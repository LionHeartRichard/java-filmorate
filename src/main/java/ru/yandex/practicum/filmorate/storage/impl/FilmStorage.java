package ru.yandex.practicum.filmorate.storage.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.CustomException;
import ru.yandex.practicum.filmorate.model.impl.Film;
import ru.yandex.practicum.filmorate.storage.Storage;

@Slf4j
@Component
public class FilmStorage implements Storage<Film> {

	private final Map<Long, Film> films = new HashMap<>();

	@Override
	public Film create(Film film) {
		log.trace("Валидация пройдена. Начало обработки POST зпроса /films, обект: {}", film.toString());
		if (film.getId() == null) {
			film.setId(nextId());
			films.put(film.getId(), film);
			log.trace("Фильм с id: {} добавлен", film.getId());
			return film;
		}
		log.warn("Попытка внести информацию о фильме с id указанным в ручную, object film: {}", film.toString());
		throw new CustomException(String.format(
				"Фильм с id: %d не может быть добавлен! Идентификатор генерируется автоматически!", film.getId()));
	}

	@Override
	public Collection<Film> read() {
		return films.values();
	}

	@Override
	public Film update(Film newFilm) {
		log.trace("Валидация пройдена. Начало обработки PUT зпроса /films, обект: {}", newFilm.toString());
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
			films.put(film.getId(), film);
			log.trace("Фильм изменен и внесен в память, обект: {}", film.toString());
			return film;
		}
		log.warn("Попытка внести изменения в информацию о фильме с несуществующим id: {}", newFilm.getId());
		throw new CustomException(String.format("Фильм с id: %d не найден!", newFilm.getId()));
	}

	@Override
	public Film delete(Film t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Film findById(Long id) {
		return films.get(id);
	}

	@Override
	public Collection<Film> findByParam(String... args) {
		// TODO Auto-generated method stub
		return null;
	}

	private Long nextId() {
		Long id = films.keySet().stream().mapToLong(k -> k).max().orElse(0);
		return ++id;
	}

}
