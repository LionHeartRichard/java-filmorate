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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.controller.Controller;
import ru.yandex.practicum.filmorate.exception.CustomException;
import ru.yandex.practicum.filmorate.model.impl.Film;
import ru.yandex.practicum.filmorate.storage.impl.FilmStorage;
import ru.yandex.practicum.filmorate.service.FilmService;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController implements Controller<Film> {

	private final FilmStorage filmStorage;
	private final FilmService filmService;

	@Override
	@PostMapping
	public Film create(@Valid @RequestBody Film film) {
		return filmStorage.create(film);
	}

	@Override
	@GetMapping
	public Collection<Film> doGet() {
		if()
	}

	@Override
	@PutMapping
	public Film update(@Valid @RequestBody Film newFilm) {
		return filmStorage.create(newFilm);
	}
}
