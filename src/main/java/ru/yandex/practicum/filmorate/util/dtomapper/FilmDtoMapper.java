package ru.yandex.practicum.filmorate.util.dtomapper;

import ru.yandex.practicum.filmorate.dto.FilmDto.Request.Create;
import ru.yandex.practicum.filmorate.dto.FilmDto.Request.Update;
import ru.yandex.practicum.filmorate.dto.FilmDto.Response.Public;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.dto.FilmDto.Response.Private;

public class FilmDtoMapper {
	private FilmDtoMapper() {
	}

	public static Film returnFilm(Create dto) {
		Film film = Film.builder().id(null).name(dto.getName()).description(dto.getDescription())
				.releaseDate(dto.getReleaseDate()).duration(dto.getDuration()).build();
		return film;
	}

	public static Film returnFilm(Update dto) {
		Film film = Film.builder().id(dto.getId()).name(dto.getName()).description(dto.getDescription())
				.releaseDate(dto.getReleaseDate()).duration(dto.getDuration()).build();
		return film;
	}

	public static Public returnPublic(Film film) {
		Public dto = new Public(film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration());
		return dto;
	}

	public static Private returnPrivate(Film film) {
		Private dto = new Private(film.getId(), film.getName(), film.getDescription(), film.getReleaseDate(),
				film.getDuration());
		return dto;
	}

}
