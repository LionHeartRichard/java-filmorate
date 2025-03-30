package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.impl.Film;
import ru.yandex.practicum.filmorate.storage.impl.FilmStorage;
import ru.yandex.practicum.filmorate.storage.impl.UserStorage;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilmService {

	private final FilmStorage filmStorage;
	private final UserStorage userStorage;
//
//	public Film deleteLike(final Long id, final Long userId) {
//		Film film = filmStorage.findById(id);
//
////		if (film.getLikes().contains(userId)) {
////			film.getLikes().remove(userId);
////			log.trace("лайк для фильма с id: {} удален, пользователем с id: {}", id, userId);
////			return filmStorage.update(film);
////		}
//		log.warn("Лайк для фильма с id: {} не удален так как пользователь с id: {} не ставил лайк", id, userId);
//		throw new NotFoundException(String
//				.format("Лайк для фильма с id: %d не удален так как пользователь с id: %d не ставил лайк", id, userId));
//	}
//
//	public Film addLike(final Long id, final Long userId) {
//		log.trace("Начало. Метод по добавлению лайков. Получены: id: {}, userId", id, userId);
//		Film film = filmStorage.findById(id);
//		log.trace("Обработка. Метод по добавлению лайков. Получен фильм: {}", film.toString());
//		userStorage.findById(userId);
////		if (!film.getLikes().contains(userId)) {
////			film.getLikes().add(userId);
////			filmStorage.update(film);
////			log.trace("Пользователь с id: {}, поставил лайк фильму {}", userId, film.toString());
////		}
//		return film;
//	}
}
