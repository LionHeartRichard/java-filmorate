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
import ru.yandex.practicum.filmorate.controller.Controller;
import ru.yandex.practicum.filmorate.exception.CustomException;
import ru.yandex.practicum.filmorate.model.impl.Film;

@RestController
@RequestMapping("/films")
public class FilmController implements Controller<Film> {

	private final Map<Long, Film> films = new HashMap<>();

	@Override
	@PostMapping
	public Film doPost(@Valid @RequestBody Film film) {
		if (!films.containsKey(film.getId())) {
			films.put(film.getId(), film);
			return film;
		}
		throw new CustomException("Фильм с id: " + film.getId() + " уже добавлен!");
	}

	@Override
	@GetMapping
	public Collection<Film> doGet() {
		return films.values();
	}

	@Override
	@PutMapping
	public Film doPut(@Valid @RequestBody Film newFilm) {
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
			return film;
		}
		throw new CustomException("Фильм с id: " + newFilm.getId() + " не найден!");
	}
}
