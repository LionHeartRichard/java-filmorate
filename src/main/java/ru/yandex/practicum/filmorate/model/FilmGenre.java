package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class FilmGenre {
	private Long filmId;
	private Long genreId;
}
