package ru.yandex.practicum.filmorate.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.dto.FilmAnsDto;
import ru.yandex.practicum.filmorate.dto.FilmDtoCreate;
import ru.yandex.practicum.filmorate.dto.FilmDtoUpdate;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.repositories.*;
import ru.yandex.practicum.filmorate.util.dtomapper.DtoMapperFilm;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

	private static final String MPA_NOT_FOUND = "Failed in FilmService! MPA not found!";
	private static final String FILM_NOT_FOUND = "Failed in FilmService! Film not found!";
	private static final String USER_NOT_FOUND = "Failed in FilmService! User not found";

	FilmRepository repFilm;
	LikeRepository repLike;
	GenreRepository repGenre;
	MpaRepository repMpa;
	UserRepository repUser;
	FilmGenreRepository repFilmGenre;
	DirectorRepository repDirector;
	FilmAnsDtoRepository repFilmAnsDto;

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

		List<Director> directors = dto.getDirectors();
		repFilm.saveFilmDirectors(film, directors);
		directors = repDirector.findByFilmId(film.getId());

		FilmAnsDto ans = DtoMapperFilm.getAnsDtoForFilm(film, mpa, genres, directors);
		return ans;
	}

	public List<FilmAnsDto> read() {
		return repFilmAnsDto.getFilms();
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

		List<Director> directors = dto.getDirectors();
		repFilm.saveFilmDirectors(film, directors);
		directors = repDirector.findByFilmId(film.getId());

		FilmAnsDto ans = DtoMapperFilm.getAnsDtoForFilm(film, mpa, genres, directors);
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

		List<Director> directors = repDirector.findByFilmId(film.getId());

		FilmAnsDto ans = DtoMapperFilm.getAnsDtoForFilm(film, mpa, genres, directors);
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

	public List<Film> findTopFilm(Integer limit, Long genreId, Integer year) {
		Map<Long, Integer> swap = repLike.getTopFilms(limit, genreId, year);
		List<Film> ans = new ArrayList<>();
		swap.forEach((k, v) -> {
			Optional<Film> filmOpt = repFilm.findById(k);
			if (filmOpt.isPresent())
				ans.add(filmOpt.get());
		});
		return ans;
	}

	public List<FilmAnsDto> findByDirector(Long directorId, String sortBy) {
		repDirector.findById(directorId).orElseThrow(() -> new NotFoundException("Director not found"));
		return repFilmAnsDto.getFilmsByDirectorId(directorId, sortBy);
	}

	public List<FilmAnsDto> searchFilms(String subString, String by) {
		return repFilmAnsDto.searchFilms(subString, by);
	}

	private void validationForLike(Long filmId, Long userId) {
		repFilm.findById(filmId).orElseThrow(() -> new NotFoundException(FILM_NOT_FOUND));
		repUser.findById(userId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
	}

	public List<FilmAnsDto> findCommonFilms(Long userId, Long friendId) {
		repUser.findById(userId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
		repUser.findById(friendId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
		return repFilm.findCommonFilms(userId, friendId)
				.stream()
				.map(this::findById)
				.toList();
	}
}
