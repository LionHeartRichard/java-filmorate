package ru.yandex.practicum.filmorate.controller;

import java.util.ArrayList;
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
import ru.yandex.practicum.filmorate.storage.impl.FilmStorage;
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
	private final FilmStorage filmStorage;
	private final FilmService filmService;

	@PostMapping
	public FilmDto.Response.Private create(@Valid @RequestBody FilmDto.Request.Create filmDto) {
		log.trace("Обработка запроса POST /films");
		return filmStorage.create(filmDto);
	}

	@GetMapping
	public Collection<FilmDto.Response.Private> read() {
		log.trace("Обработка запроса GET /films");
		return filmStorage.read();
	}

	@GetMapping("/popular")
	public Collection<FilmDto.Response.Private> findTopFilms(
			@RequestParam(defaultValue = GetConstants.COUNT) Integer count) {
		log.trace("Обработка запроса GET /films/popular");
		return new ArrayList<>();
//		return filmStorage.findTopFilms(count);
	}

	@PutMapping
	public FilmDto.Response.Private update(@Valid @RequestBody final FilmDto.Request.Update filmDto) {
		log.trace("Обработка запроса PUT /films");
		return filmStorage.update(filmDto);
	}

	@GetMapping("/{id}")
	public FilmDto.Response.Private findById(@PathVariable final Long id) {
		log.trace("Обработка запроса GET /films/{id}");
		validator.positiveValue(id,
				String.format("В запросе на поиск фильма по id, передано отрицательное значение: %d", id));
		return filmStorage.findById(id);
	}

//	@PutMapping("/{id}/like/{user_id}")
//	public Film addLike(@PathVariable final Long id, @PathVariable("user_id") final Long userId) {
//		log.trace("Обработка запроса PUT /films/{id}/like/{user_id}");
//		validator.positiveValue(id, String
//				.format("В запросе на добавление лайка к фильму, передано отрицательное значение id-фильма: %d", id));
//		validator.positiveValue(userId, String.format(
//				"В запросе на добавление лайка к фильму, передано отрицательное значение id-пользователя: %d", userId));
//		return filmService.addLike(id, userId);
//	}
//
//	@DeleteMapping("/{id}/like/{user_id}")
//	public Film deleteLike(@PathVariable final Long id, @PathVariable("user_id") final Long userId) {
//		log.trace("Обработка запроса DELETE /films/{id}/like/{user_id}");
//		validator.positiveValue(id, String
//				.format("В запросе на удаление лайка к фильму, передано отрицательное значение id-фильма: %d", id));
//		validator.positiveValue(userId, String.format(
//				"В запросе на удаление лайка к фильму, передано отрицательное значение id-пользователя: %d", userId));
//		return filmService.deleteLike(id, userId);
//	}
}
