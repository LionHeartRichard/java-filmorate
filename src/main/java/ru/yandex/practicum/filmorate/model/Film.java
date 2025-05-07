package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Film {
	private Long id;
	private String name;
	private String description;
	private LocalDate releaseDate;
	private Integer duration;
}
