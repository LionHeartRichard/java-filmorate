package ru.yandex.practicum.filmorate.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

@Data
public class FilmAnsDto {
	private final Long id;
	private final String name;
	private final String description;
	private final LocalDate releaseDate;
	private final Integer duration;
	private final Mpa mpa;
	private final List<Genre> genres;
}
