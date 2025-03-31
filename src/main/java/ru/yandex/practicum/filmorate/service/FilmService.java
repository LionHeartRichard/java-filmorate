package ru.yandex.practicum.filmorate.service;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.FilmDto.Response.Private;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.impl.Film;
import ru.yandex.practicum.filmorate.model.impl.Like;
import ru.yandex.practicum.filmorate.repositories.impl.FilmRepositories;
import ru.yandex.practicum.filmorate.repositories.impl.LikeRepositories;
import ru.yandex.practicum.filmorate.util.dtomapper.FilmDtoMapper;

@Service
public class FilmService {

	private final FilmRepositories filmRepo;
	private final LikeRepositories likeRepo;

	private final Map<Long, Film> cache;

	@Autowired
	public FilmService(FilmRepositories filmRepo, LikeRepositories likeRepo) {
		this.filmRepo = filmRepo;
		this.likeRepo = likeRepo;
		cache = this.filmRepo.getStream(0).collect(Collectors.toMap(Film::getId, f -> f));
	}

	public Long create(FilmDto.Request.Create filmDto) {
		Film film = FilmDtoMapper.returnFilm(filmDto);
		Long id = filmRepo.add(film)
				.orElseThrow(() -> new InternalServerException("Error - when adding a film to the database!"));
		film.setId(id);
		cache.put(id, film);
		return id;
	}

	public Collection<Private> read() {
		return cache.values().stream().map(FilmDtoMapper::returnPrivate).toList();
	}

	public Collection<Long> findTopFilms(Integer count) {
		return likeRepo.getTopFilms(count).keySet();
	}

	public Private update(FilmDto.Request.Update filmDto) {
		Long id = filmDto.getId();
		if (cache.containsKey(id)) {
			Film film = FilmDtoMapper.returnFilm(filmDto);
			filmRepo.update(film).orElseThrow(() -> new InternalServerException("Failed! Update film in data base!"));
			cache.put(id, film);
			return FilmDtoMapper.returnPrivate(film);
		}
		throw new NotFoundException("Failed update film! Film not found!");
	}

	public Private findById(Long id) {
		Film film = cache.get(id);
		if (film == null)
			throw new NotFoundException("Film not found!");
		return FilmDtoMapper.returnPrivate(film);
	}

	public void addLike(Long id, Long userId) {
		Like like = Like.builder().filmId(id).userId(userId).build();
		likeRepo.add(like);
	}

	public void deleteLike(Long id, Long userId) {
		if (cache.containsKey(id))
			likeRepo.remove(id, userId);
	}
}
