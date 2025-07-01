package ru.yandex.practicum.filmorate.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.dto.FilmAnsDto;
import ru.yandex.practicum.filmorate.dto.FilmDtoCreate;
import ru.yandex.practicum.filmorate.dto.FilmDtoUpdate;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.repositories.FilmGenreRepository;
import ru.yandex.practicum.filmorate.repositories.FilmRepository;
import ru.yandex.practicum.filmorate.repositories.GenreRepository;
import ru.yandex.practicum.filmorate.repositories.LikeRepository;
import ru.yandex.practicum.filmorate.repositories.MpaRepository;
import ru.yandex.practicum.filmorate.repositories.UserRepository;
import ru.yandex.practicum.filmorate.util.dtomapper.DtoMapperFilm;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

	private static final String MPA_NOT_FOUND = "Failed in FilmService! MPA not found!";
	private static final String FILM_NOT_FOUND = "Failed in FilmService! Film not found!";
	private static final String USER_NOT_FOUND = "Failed in FilmService! User not found";

	private final FilmRepository repFilm;
	private final LikeRepository repLike;
	private final GenreRepository repGenre;
	private final MpaRepository repMpa;
	private final UserRepository repUser;
	private final FilmGenreRepository repFilmGenre;

	public FilmAnsDto create(FilmDtoCreate dto) {

		Film film = DtoMapperFilm.getFilm(dto);
		repFilm.save(film);
		log.trace("Done: save film in DB");

		Mpa mpa = dto.getMpa() == null ? null
				: repMpa.findById(dto.getMpa().getId()).orElseThrow(() -> new NotFoundException(MPA_NOT_FOUND));

		List<Genre> genres = new ArrayList<>();
		List<Genre> swap = dto.getGenres();
		Set<FilmGenre> cache = new HashSet<>();
		if (swap != null) {
			swap.forEach(v -> {
				if (v.getId() != null) {
					Genre genre = repGenre.findById(v.getId())
							.orElseThrow(() -> new NotFoundException("Genre not found!"));
					FilmGenre filmGenre = new FilmGenre();
					filmGenre.setFilmId(film.getId());
					filmGenre.setGenreId(genre.getId());
					if (!cache.contains(filmGenre)) {
						cache.add(filmGenre);
						repFilmGenre.save(filmGenre);
						genres.add(genre);
					}
				}
			});
			log.trace("Done: save genres in DB");
		}
		FilmAnsDto ans = DtoMapperFilm.getAnsDtoForFilm(film, mpa, genres);
		return ans;
	}

	public List<Film> read() {
		return repFilm.findAll();
	}

	public FilmAnsDto update(FilmDtoUpdate dto) {
		Film film = repFilm.findById(dto.getId())
				.orElseThrow(() -> new NotFoundException("Failed update film! Film not found!"));
		film = DtoMapperFilm.getFilm(film, dto);
		repFilm.update(film);

		Optional<Mpa> mpaOpt = repMpa.findById(film.getMpaId());
		Mpa mpa = mpaOpt.isPresent() ? mpaOpt.get() : null;

		List<Genre> genres = new ArrayList<>();
		List<FilmGenre> filmGenres = repFilmGenre.findByFilmId(film.getId());
		for (FilmGenre v : filmGenres) {
			Optional<Genre> genreOpt = repGenre.findById(v.getGenreId());
			if (genreOpt.isPresent())
				genres.add(genreOpt.get());
		}

		FilmAnsDto ans = DtoMapperFilm.getAnsDtoForFilm(film, mpa, genres);
		return ans;
	}

	public FilmAnsDto findById(Long filmId) {
		Film film = repFilm.findById(filmId).orElseThrow(() -> new NotFoundException("Failed update film!"));

		Mpa mpa = new Mpa();
		Optional<Mpa> mpaOpt = repMpa.findById(film.getMpaId());
		mpa = mpaOpt.isPresent() ? mpaOpt.get() : mpa;

		List<FilmGenre> filmGenres = repFilmGenre.findByFilmId(film.getId());
		List<Genre> genres = new ArrayList<>();
		filmGenres.forEach(v -> {
			Optional<Genre> genreOpt = repGenre.findById(v.getGenreId());
			if (genreOpt.isPresent())
				genres.add(genreOpt.get());
		});

		FilmAnsDto ans = DtoMapperFilm.getAnsDtoForFilm(film, mpa, genres);
		return ans;
	}

	public void addLike(Long filmId, Long userId) {
		validationForLike(filmId, userId);
		Like like = new Like();
		like.setFilmId(filmId);
		like.setUserId(userId);
		repLike.save(like);
	}

	public void deleteLike(Long likeId) {
		repLike.deleteById(likeId);
	}

	public void deleteLike(Long filmId, Long userId) {
		validationForLike(filmId, userId);
		repLike.deleteLike(filmId, userId);
	}

	public List<Film> findTopFilm(Integer limit) {
		Map<Long, Integer> swap = repLike.getTopFilms(limit);
		List<Film> ans = new ArrayList<>();
		swap.forEach((k, v) -> {
			Optional<Film> filmOpt = repFilm.findById(k);
			if (filmOpt.isPresent()) {
				ans.add(filmOpt.get());
			}
		});
		return ans;
	}

	private void validationForLike(Long filmId, Long userId) {
		repFilm.findById(filmId).orElseThrow(() -> new NotFoundException(FILM_NOT_FOUND));
		repUser.findById(userId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
	}
}
