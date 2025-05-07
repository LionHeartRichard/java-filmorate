package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Mpa {
	private Long mpaId;
	private Long filmId;
	private String name;
}
