package ru.yandex.practicum.filmorate.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.FilmDto.Response.Private;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.repositories.impl.FilmRepositories;
import ru.yandex.practicum.filmorate.repositories.impl.LikeRepositories;
import ru.yandex.practicum.filmorate.util.dtomapper.FilmDtoMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

	private final FilmRepositories filmRepo;
	private final LikeRepositories likeRepo;

	public Private create(FilmDto.Request.Create filmDto) {
		Film film = FilmDtoMapper.returnFilm(filmDto);
		log.trace("Add film: {}", film.toString());
		Long id = filmRepo.add(film)
				.orElseThrow(() -> new InternalServerException("Error - when adding a film to the database!"));
		film.setId(id);
		return FilmDtoMapper.returnPrivate(film);
	}

	public Collection<Private> read() {
		return filmRepo.getStream().map(FilmDtoMapper::returnPrivate).toList();
	}

	public Collection<Long> findTopFilms(Integer count) {
		return likeRepo.getTopFilms(count).keySet();
	}

	public Private update(FilmDto.Request.Update filmDto) {
		Long id = filmDto.getId();
		Film film = filmRepo.getById(id)
				.orElseThrow(() -> new NotFoundException("Failed update film! Film not found!"));
		Film upFilm = FilmDtoMapper.returnFilm(filmDto);
		filmRepo.update(upFilm).orElseThrow(() -> new InternalServerException("Failed! Update film in data base!"));
		return FilmDtoMapper.returnPrivate(film);

	}

	public Private findById(Long id) {
		Film film = filmRepo.getById(id)
				.orElseThrow(() -> new NotFoundException("Failed update film! Film not found!"));
		return FilmDtoMapper.returnPrivate(film);
	}

	public void addLike(Long id, Long userId) {
		Like like = Like.builder().filmId(id).userId(userId).build();
		likeRepo.add(like);
	}

	public void deleteLike(Long id, Long userId) {
		likeRepo.remove(id, userId);
	}
}
