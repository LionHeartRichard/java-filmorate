package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Genre {
	private Long genreId;
	private String name;
}
