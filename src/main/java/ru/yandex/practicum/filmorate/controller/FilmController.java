package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.util.LocalValidator;
import ru.yandex.practicum.filmorate.util.GetConstants;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.dto.FilmDto;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

	private final LocalValidator validator;
	private final FilmService filmService;

	@PostMapping
	public Long create(@Valid @RequestBody FilmDto.Request.Create filmDto) {
		log.trace("POST /films");
		return filmService.create(filmDto);
	}

	@GetMapping
	public Collection<FilmDto.Response.Private> read() {
		log.trace("GET /films");
		return filmService.read();
	}

	@GetMapping("/popular")
	public Collection<Long> findTopFilms(@RequestParam(defaultValue = GetConstants.COUNT) Integer count) {
		log.trace("GET /films/popular");
		return filmService.findTopFilms(count);
	}

	@PutMapping
	public FilmDto.Response.Private update(@Valid @RequestBody final FilmDto.Request.Update filmDto) {
		log.trace("PUT /films");
		return filmService.update(filmDto);
	}

	@GetMapping("/{id}")
	public FilmDto.Response.Private findById(@PathVariable final Long id) {
		log.trace("GET /films/{id}");
		validator.positiveValue(id, String.format("ID cannot be negative, filmId: %d", id));
		return filmService.findById(id);
	}

	@PutMapping("/{id}/like/{user_id}")
	public void addLike(@PathVariable final Long id, @PathVariable("user_id") final Long userId) {
		log.trace("PUT /films/{id}/like/{user_id}");
		validator.positiveValue(id, String.format("ID cannot be negative, filmId: %d", id));
		validator.positiveValue(userId, String.format("ID cannot be negative, userId: %d", userId));
		filmService.addLike(id, userId);
	}

	@DeleteMapping("/{id}/like/{user_id}")
	public void deleteLike(@PathVariable final Long id, @PathVariable("user_id") final Long userId) {
		log.trace("DELETE /films/{id}/like/{user_id}");
		validator.positiveValue(id, String.format("ID cannot be negative, filmId: %d", id));
		validator.positiveValue(userId, String.format("ID cannot be negative, userId: %d", userId));
		filmService.deleteLike(id, userId);
	}
}
