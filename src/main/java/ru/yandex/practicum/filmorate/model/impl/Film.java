package ru.yandex.practicum.filmorate.model.impl;

import java.time.LocalDate;
import lombok.Value;
import ru.yandex.practicum.filmorate.model.WebModel;

@Value
public class Film implements WebModel {
	Long id;
	String name;
	String description;
	LocalDate releaseDate;
	Integer duration;
}
