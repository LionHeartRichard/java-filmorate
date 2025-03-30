package ru.yandex.practicum.filmorate.storage.impl;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.impl.Film;
import ru.yandex.practicum.filmorate.repositories.impl.FilmRepositories;
import ru.yandex.practicum.filmorate.storage.Storage;
import ru.yandex.practicum.filmorate.util.dtomapper.FilmDtoMapper;
import ru.yandex.practicum.filmorate.dto.FilmDto.Request.Create;
import ru.yandex.practicum.filmorate.dto.FilmDto.Request.Update;
import ru.yandex.practicum.filmorate.dto.FilmDto.Response.Private;

@Slf4j
@Component
public class FilmStorage implements Storage<Private, Create, Update> {

	private final FilmRepositories filmRepositories;
	private final Map<Long, Film> films;

	@Autowired
	public FilmStorage(FilmRepositories filmRepositories) {
		this.filmRepositories = filmRepositories;
		films = this.filmRepositories.query(0).stream().collect(Collectors.toMap(Film::getId, Function.identity()));
	}

	@Override
	public Private create(Create filmDto) {
		Film film = FilmDtoMapper.returnFilm(filmDto);
		log.trace("создание фильма в ХРАНИЛИЩЕ фильмов, film: {}", film.toString());
		Long id = filmRepositories.add(film)
				.orElseThrow(() -> new InternalServerException("Faild! Film not added to database!!!"));
		film.setId(id);
		films.put(film.getId(), film);
		log.trace("Фильм с id: {} добавлен в ХРАНИЛИЩЕ фильмов", film.getId());
		return FilmDtoMapper.returnPrivate(film);

	}

	@Override
	public Collection<Private> read() {
		log.trace("Обработка в ХРАНИЛИЩЕ. Получение списка всех фильмов");
		return films.values().stream().map(FilmDtoMapper::returnPrivate).toList();
	}

//	public Collection<Film> findTopFilms(int count) {
//		log.trace("Выполнение метода read в ХРАНИЛИЩЕ фильмов");
//		List<Film> topFilms = films.values().stream().map(f -> {
//			if (f.getLikes() == null)
//				f.setLikes(new HashSet<>());
//			return f;
//		}).sorted((a, b) -> Integer.compare(b.getLikes().size(), a.getLikes().size())).limit(count).toList();
//		return topFilms;
//	}

	@Override
	public Private update(Update filmDto) {
		Film film = FilmDtoMapper.returnFilm(filmDto);
		log.trace("Обновление фильма в ХРАНИЛИЩЕ фильмов newFilm: {}", film.toString());
		if (films.containsKey(film.getId())) {
			filmRepositories.update(film)
					.orElseThrow(() -> new InternalServerException("Failed! Film not updated to database!!!"));
			films.put(film.getId(), film);
			log.trace("Фильм изменен и внесен в память, film: {}", film.toString());
			return FilmDtoMapper.returnPrivate(film);
		}
		filmRepositories.query(film.getId())
				.orElseThrow(() -> new NotFoundException("Failed! Film not found in database!!!"));
		filmRepositories.update(film)
				.orElseThrow(() -> new InternalServerException("Failed! Film not updated to database!!!"));
		log.trace("Фильм изменен и внесен в память, film: {}", film.toString());
		return FilmDtoMapper.returnPrivate(film);
	}

	@Override
	public Private findById(Long id) {
		log.trace("Поиск в ХРАНИЛИЩЕ фильмов по id: {}", id);
		Film film;
		if (films.containsKey(id)) {
			film = films.get(id);
			log.trace("Возвращаем фильм {} из ХРАНИЛИЩА", film.toString());
			return FilmDtoMapper.returnPrivate(film);
		}
		film = filmRepositories.query(id)
				.orElseThrow(() -> new NotFoundException("Failed! Film not found in database!!!"));
		return FilmDtoMapper.returnPrivate(film);
	}

}
