package ru.yandex.practicum.filmorate.controller;

import java.util.List;

import jakarta.validation.constraints.Pattern;
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
import ru.yandex.practicum.filmorate.util.GetConstants;
import ru.yandex.practicum.filmorate.util.LocalValidator;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.dto.FilmAnsDto;
import ru.yandex.practicum.filmorate.dto.FilmDtoCreate;
import ru.yandex.practicum.filmorate.dto.FilmDtoUpdate;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

	private static final String NEGATIVE_FILM_ID = "Film ID cannot be negative!";
	private static final String NEGATIVE_USER_ID = "User ID cannot be negative!";

	private final LocalValidator validator;
	private final FilmService filmService;

	@PostMapping
	public FilmAnsDto create(@Valid @RequestBody FilmDtoCreate dto) {
		log.trace("POST /films");
		return filmService.create(dto);
	}

	@GetMapping
	public List<FilmAnsDto> read() {
		log.trace("GET /films");
		return filmService.read();
	}

	@PutMapping
	public FilmAnsDto update(@Valid @RequestBody final FilmDtoUpdate dto) {
		log.trace("PUT /films");
		return filmService.update(dto);
	}

	@GetMapping("/{id}")
	public FilmAnsDto findById(@PathVariable final Long id) {
		log.trace("GET /films/{id}");
		validator.positiveValue(id, NEGATIVE_FILM_ID);
		return filmService.findById(id);
	}

	@PutMapping("/{id}/like/{user_id}")
	public void addLike(@PathVariable final Long id, @PathVariable("user_id") final Long userId) {
		log.trace("PUT /films/{id}/like/{user_id}");
		validator.positiveValue(id, NEGATIVE_FILM_ID);
		validator.positiveValue(userId, NEGATIVE_USER_ID);
		filmService.addLike(id, userId);
	}

	@DeleteMapping("/{id}/like/{user_id}")
	public void deleteLike(@PathVariable final Long id, @PathVariable("user_id") final Long userId) {
		log.trace("DELETE /films/{id}/like/{user_id}");
		validator.positiveValue(id, NEGATIVE_FILM_ID);
		validator.positiveValue(userId, NEGATIVE_USER_ID);
		filmService.deleteLike(id, userId);
	}

	@GetMapping("/popular")
	public List<FilmAnsDto> findTopFilms(
			@RequestParam(defaultValue = GetConstants.COUNT) Integer count,
			@RequestParam(required = false) Long genreId, @RequestParam(required = false) Integer year) {
		log.trace("GET /films/popular");
		return filmService.findTopFilm(count, genreId, year);
	}

	@GetMapping("/director/{directorId}")
	public List<FilmAnsDto> findByDirector(@PathVariable("directorId") Long directorId,
			@RequestParam(value = "sortBy", required = false) String sortBy) {
		log.trace("GET /director/{directorId}");
		return filmService.findByDirector(directorId, sortBy);
	}

	@GetMapping("/search")
	public List<FilmAnsDto> searchFilms(@RequestParam(required = false) String query,
			@RequestParam(required = false) @Pattern(regexp = "title|director|title,director|director,title") String by) {
		log.trace("GET /films/search");
		return filmService.searchFilms(query, by);
	}

	@GetMapping("/common")
	public List<FilmAnsDto> findCommonFilms(@RequestParam Long userId, @RequestParam Long friendId) {
		log.trace("GET /films/common");
		validator.positiveValue(userId, String.format("ID cannot be negative, userId: %d", userId));
		validator.positiveValue(friendId, String.format("ID cannot be negative, filmId: %d", friendId));
		return filmService.findCommonFilms(userId, friendId);
	}

	@DeleteMapping("/{id}")
	public void deleteFilm(@PathVariable final Long id) {
		log.trace("DELETE /films/{id}");
		validator.positiveValue(id, NEGATIVE_FILM_ID);
		filmService.deleteFilm(id);
	}
}
