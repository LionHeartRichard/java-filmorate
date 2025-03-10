package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;
import java.util.Map;

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
import ru.yandex.practicum.filmorate.exception.NotValidParamException;
import ru.yandex.practicum.filmorate.model.impl.Film;
import ru.yandex.practicum.filmorate.storage.impl.FilmStorage;
import ru.yandex.practicum.filmorate.util.GetConstants;
import ru.yandex.practicum.filmorate.service.FilmService;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

	private final FilmStorage filmStorage;
	private final FilmService filmService;

	@PostMapping
	public Film create(@Valid @RequestBody Film film) {
		log.trace("Обработка запроса POST /films");
		return filmStorage.create(film);
	}

	@GetMapping
	public Collection<Film> findTopFilms(@RequestParam(defaultValue = GetConstants.COUNT) final int count) {
		log.trace("Обработка запроса GET /films");
		return filmStorage.findTopFilms(count);
	}

	@PutMapping
	public Film update(@Valid @RequestBody final Film newFilm) {
		log.trace("Обработка запроса PUT /films");
		return filmStorage.create(newFilm);
	}

	@GetMapping("/{id}")
	public Film findById(@PathVariable final Long id) {
		log.trace("Обработка запроса GET /films/{id}");
		if (id < 0) {
			log.warn("Идентификатор фильма имеет отрицательное значение: {}", id);
			throw new NotValidParamException(
					Map.of("Не верный идентиикатор фильма", "идентификатор - целое положительное число"),
					Map.of("Передан идентификатор фильма", id + ""));
		}
		return filmStorage.findById(id);
	}

	@PutMapping("/{id}/like/{userId}")
	public Film addLike(@PathVariable(required = false) final Long id,
			@PathVariable(required = false) final Long userId) {
		log.trace("Обработка запроса PUT /films/{id}/like/{userId}");
		validId(id, userId);
		return filmService.addLike(id, userId);
	}

	@DeleteMapping("/{id}/like/{userId}")
	public Film deleteLike(@PathVariable(required = false) final Long id,
			@PathVariable(required = false) final Long userId) {
		log.trace("Обработка запроса DELETE /films/{id}/like/{userId}");
		validId(id, userId);
		return filmService.deleteLike(id, userId);
	}

	private void validId(Long id, Long userId) {
		if (id == null || userId == null) {
			log.warn("Передан ошибочный идентификатор от клиента filmId: {}, userId: {}", id, userId);
			throw new NotValidParamException(Map.of("Идентификатор фильма", id + ""),
					Map.of("Идентификатор пользователя", userId + ""),
					Map.of("Идентификаторы", "НЕ МОГУТ иметь значение null!!!"));
		}
		if (id < 0 || userId < 0) {
			log.warn("Передан ошибочный идентификатор от клиента filmId: {}, userId: {}", id, userId);
			throw new NotValidParamException(Map.of("Идентификатор фильма", id + ""),
					Map.of("Идентификатор пользователя", userId + ""),
					Map.of("Идентификаторы", "НЕ МОГУТ иметь отрицательное значение!!!"));
		}
	}
}
