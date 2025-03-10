package ru.yandex.practicum.filmorate.storage.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.impl.Film;
import ru.yandex.practicum.filmorate.storage.Storage;

@Slf4j
@Component
public class FilmStorage implements Storage<Film> {

	private final Map<Long, Film> films = new HashMap<>();

	@Override
	public Film create(Film film) {
		log.trace("создание фильма в ХРАНИЛИЩЕ фильмов, film: {}", film.toString());
		if (film.getId() == null) {
			film.setId(nextId());
			films.put(film.getId(), film);
			log.trace("Фильм с id: {} добавлен в ХРАНИЛИЩЕ фильмов", film.getId());
			return film;
		}
		log.warn("Попытка внести информацию о фильме с id указанным в ручную, film: {}", film.toString());
		throw new ConditionsNotMetException(String.format(
				"Фильм с id: %d не может быть добавлен! Идентификатор генерируется автоматически!", film.getId()));
	}

	public Collection<Film> findTopFilms(final int count) {
		log.trace("Выполнение метода read в ХРАНИЛИЩЕ фильмов");
		return films.values().stream().sorted((a, b) -> Integer.compare(b.getLikes().size(), a.getLikes().size()))
				.limit(count).toList();
	}

	@Override
	public Film update(Film newFilm) {
		log.trace("Обновление фильма в ХРАНИЛИЩЕ фильмов newFilm: {}", newFilm.toString());
		if (films.containsKey(newFilm.getId())) {
			Film film = films.get(newFilm.getId());
			if (newFilm.getName() != null)
				film.setName(newFilm.getName());
			if (newFilm.getDescription() != null)
				film.setDescription(newFilm.getDescription());
			if (newFilm.getDuration() != null)
				film.setDuration(newFilm.getDuration());
			if (newFilm.getReleaseDate() != null)
				film.setReleaseDate(newFilm.getReleaseDate());
			if (newFilm.getLikes() != null)
				film.setLikes(newFilm.getLikes());
			films.put(film.getId(), film);
			log.trace("Фильм изменен и внесен в память, film: {}", film.toString());
			return film;
		}
		log.warn(
				"Попытка внести изменения в информацию о фильме с несуществующим id: {}. Обновление несуществующего фильма: {}",
				newFilm.getId(), newFilm.toString());
		throw new NotFoundException(String.format("Фильм с id: %d не найден!", newFilm.getId()));
	}

	@Override
	public Film findById(Long id) {
		log.trace("Поиск в ХРАНИЛИЩЕ фильмов по id: {}", id);
		if (films.containsKey(id)) {
			Film film = films.get(id);
			log.trace("Возвращаем фильм {} из ХРАНИЛИЩА", film.toString());
			return film;
		}
		log.warn("Фильм с id: {} не найден", id);
		throw new NotFoundException(String.format("Фильм с id: %d - не найден!!!", id));
	}

	private Long nextId() {
		Long id = films.keySet().stream().mapToLong(k -> k).max().orElse(0);
		return ++id;
	}

}
