package ru.yandex.practicum.filmorate.model.impl;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.WebModel;

@Data
@Builder(toBuilder = true)
public class Film implements WebModel {
	private Long id;
	private String name;
	private String description;
	private LocalDate releaseDate;
	private Integer duration;
}
