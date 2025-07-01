package ru.yandex.practicum.filmorate.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.util.LocalValidator;

@Slf4j
@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {
	private final GenreService service;
	private final LocalValidator validator;

	@PostMapping
	public Genre create(Genre genre) {
		validator.positiveValue(genre.getId(), "Id for genre is not positive!");
		return service.create(genre);
	}

	@GetMapping
	public List<Genre> read() {
		log.trace("GET /genres");
		return service.read();
	}

	@GetMapping("/{id}")
	public Genre findById(@PathVariable Long id) {
		log.trace("GET /genres/{id}");
		validator.positiveValue(id, "Id for genre is not positive!");
		return service.findById(id);
	}

}
