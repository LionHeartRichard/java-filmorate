package ru.yandex.practicum.filmorate.controller.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.controller.Controller;
import ru.yandex.practicum.filmorate.exception.CustomException;
import ru.yandex.practicum.filmorate.model.impl.Film;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController implements Controller<Film> {

	private final Map<Long, Film> films = new HashMap<>();

	@Override
	@PostMapping
	public Film doPost(@Valid @RequestBody Film film) {
		log.trace("Валидация пройдена. Начало обработки POST зпроса /films, обект фильм: " + film.toString());
		if (film.getId() == null) {
			film.setId(nextId());
			films.put(film.getId(), film);
			log.trace("Фильм с id: " + film.getId() + " добавлен");
			return film;
		}
		log.warn("Попытка внести информацию о фильме с id указанным в ручную, object film: " + film.toString());
		throw new CustomException(
				"Фильм с id: " + film.getId() + " не может быть добавлен! Идентификатор генерируется автоматически!");
	}

	@Override
	@GetMapping
	public Collection<Film> doGet() {
		return films.values();
	}

	@Override
	@PutMapping
	public Film doPut(@Valid @RequestBody Film newFilm) {
		log.trace("Валидация пройдена. Начало обработки PUT зпроса /films, обект: " + newFilm.toString());
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
			log.trace("Фильм изменен и внесен в память, обект: " + film.toString());
			return film;
		}
		log.warn("Попытка внести изменения в информацию о фильме с несуществующим id: " + newFilm.getId());
		throw new CustomException("Фильм с id: " + newFilm.getId() + " не найден!");
	}

	private Long nextId() {
		Long id = films.keySet().stream().mapToLong(key -> key).max().orElse(0);
		return ++id;
	}
}
