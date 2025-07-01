package ru.yandex.practicum.filmorate.util.dtomapper;

import java.util.List;

import ru.yandex.practicum.filmorate.dto.FilmAnsDto;
import ru.yandex.practicum.filmorate.dto.FilmDtoCreate;
import ru.yandex.practicum.filmorate.dto.FilmDtoUpdate;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

public class DtoMapperFilm {

	private DtoMapperFilm() {
	}

	public static Film getFilm(FilmDtoCreate dto) {
		Film film = new Film();
		film.setDescription(dto.getDescription());
		film.setDuration(dto.getDuration());
		film.setName(dto.getName());
		film.setReleaseDate(dto.getReleaseDate());
		if (dto.getMpa() != null) {
			film.setMpaId(dto.getMpa().getId());
		}
		return film;
	}

	public static Film getFilm(FilmAnsDto dto) {
		Film film = new Film();
		film.setDescription(dto.getDescription());
		film.setDuration(dto.getDuration());
		film.setName(dto.getName());
		film.setReleaseDate(dto.getReleaseDate());
		if (dto.getMpa() != null) {
			film.setMpaId(dto.getMpa().getId());
		}
		return film;
	}

	public static Film getFilm(Film film, FilmDtoUpdate dto) {
		if (dto.hasDescription())
			film.setDescription(dto.getDescription());
		if (dto.hasDuration())
			film.setDuration(dto.getDuration());
		if (dto.hasName())
			film.setName(dto.getName());
		if (dto.hasReleaseDate())
			film.setReleaseDate(dto.getReleaseDate());
		if (dto.hasMpaId()) {
			film.setMpaId(dto.getMpa().getId());
		}
		return film;
	}

	public static FilmAnsDto getAnsDtoForFilm(Film film, Mpa mpa, List<Genre> genres) {
		return new FilmAnsDto(film.getId(), film.getName(), film.getDescription(), film.getReleaseDate(),
				film.getDuration(), mpa, genres);
	}
}
