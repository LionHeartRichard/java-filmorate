package ru.yandex.practicum.filmorate.model.impl;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.WebModel;

@Data
@Builder(toBuilder = true)
public class Film implements WebModel {
	Long id;
	String name;
	String description;
	LocalDate releaseDate;
	Integer duration;
}
