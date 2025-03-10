package ru.yandex.practicum.filmorate.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.impl.Film;
import ru.yandex.practicum.filmorate.model.impl.User;
import ru.yandex.practicum.filmorate.storage.impl.FilmStorage;
import ru.yandex.practicum.filmorate.storage.impl.UserStorage;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilmService {

	private final FilmStorage filmStorage;
	private final UserStorage userStorage;

	public Film deleteLike(final Long id, final Long userId) {
		Film film = filmStorage.findById(id);
		@NotNull(message = "Удаление лайка для фильма не возможно! Лайки отсутсвуют")
		Set<Long> likes = film.getLikes();
		if (likes.contains(userId)) {
			log.trace("лайк для фильма с id:{}, удален пользователем с id:{}", id, userId);
			likes.remove(userId);
			film.setLikes(likes);
			return filmStorage.update(film);
		}
		log.warn("Лайк для фильма с id: {} не удален так как пользователь с id: {} не ставил лайк", id, userId);
		throw new NotFoundException(
				String.format("Пользователь с идентификатором: %d - не ставил лайк для фильма!!!", userId));
	}

	public Film addLike(final Long id, final Long userId) {
		Film film = filmStorage.findById(id);
		log.trace("начало обработки в СЕРВИСЕ, метод по добавлению лайков. Получен фильм: {}", film.toString());
		userStorage.findById(userId);
		Set<Long> likes = film.getLikes();
		likes = likes == null ? new HashSet<>() : likes;
		if (!likes.contains(userId)) {
			likes.add(userId);
			film.setLikes(likes);
			filmStorage.update(film);
			log.trace("Пользователь с id: {}, поставил лайк фильму {}", userId, film.toString());
		}
		return film;
	}
}
