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
	private long nextId = 1L;

	@Override
	public Film create(Film film) {
		log.trace("создание фильма в ХРАНИЛИЩЕ фильмов, film: {}", film.toString());
		if (film.getId() == null) {
			film.setId(nextId++);
			films.put(film.getId(), film);
			log.trace("Фильм с id: {} добавлен в ХРАНИЛИЩЕ фильмов", film.getId());
			return film;
		}
		log.warn("Id указанн в ручную, film: {}", film.toString());
		throw new ConditionsNotMetException(String.format(
				"Фильм: %s не может быть добавлен! Идентификатор генерируется автоматически!", film.toString()));
	}

	@Override
	public Collection<Film> read() {
		log.trace("Обработка в ХРАНИЛИЩЕ. Получение списка всех фильмов");
		return films.values();
	}

//	public Collection<Film> findTopFilms(int count) {
//		log.trace("Выполнение метода read в ХРАНИЛИЩЕ фильмов");
//		List<Film> topFilms = films.values().stream().map(f -> {
//			if (f.getLikes() == null)
//				f.setLikes(new HashSet<>());
//			return f;
//		}).sorted((a, b) -> Integer.compare(b.getLikes().size(), a.getLikes().size())).limit(count).toList();
//		return topFilms;
//	}

	@Override
	public Film update(Film film) {
		log.trace("Обновление фильма в ХРАНИЛИЩЕ фильмов newFilm: {}", film.toString());
		if (films.containsKey(film.getId())) {
			films.put(film.getId(), film);
			log.trace("Фильм изменен и внесен в память, film: {}", film.toString());
			return film;
		}
		log.warn("Обновление несуществующего фильма: {}", film.toString());
		throw new NotFoundException(String.format("Фильм с id: %d не найден!", film.getId()));
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

}
