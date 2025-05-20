package ru.yandex.practicum.filmorate.util.dtomapper;

import ru.yandex.practicum.filmorate.dto.FilmDtoCreate;
import ru.yandex.practicum.filmorate.dto.FilmDtoUpdate;
import ru.yandex.practicum.filmorate.model.Film;

public class FilmDtoMapper {

	private FilmDtoMapper() {
	}

	public static Film returnFilm(FilmDtoCreate dto) {
		Film film = new Film();
		film.setDescription(dto.getDescription());
		film.setDuration(dto.getDuration());
		film.setName(dto.getName());
		film.setReleaseDate(dto.getReleaseDate());
		return film;
	}

	public static Film returnFilm(Film film, FilmDtoUpdate dto) {
		if (dto.hasDescription())
			film.setDescription(dto.getDescription());
		if (dto.hasDuration())
			film.setDuration(dto.getDuration());
		if (dto.hasName())
			film.setName(dto.getName());
		if (dto.hasReleaseDate())
			film.setReleaseDate(dto.getReleaseDate());
		return film;
	}
}
