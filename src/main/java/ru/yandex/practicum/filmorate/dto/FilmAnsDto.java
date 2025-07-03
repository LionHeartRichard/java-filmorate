package ru.yandex.practicum.filmorate.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Data
public class FilmAnsDto {
	Long id;
	String name;
	String description;
	LocalDate releaseDate;
	Integer duration;
	Mpa mpa;
	List<Genre> genres;
}
