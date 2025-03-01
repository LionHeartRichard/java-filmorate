package ru.yandex.practicum.filmorate.model.impl;

import java.time.Duration;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.WebModel;
import ru.yandex.practicum.filmorate.validation.ValidOfLocalDate1895;
import ru.yandex.practicum.filmorate.validation.ValidPositiveDuration;

@Data
@Builder(toBuilder = true)
public class Film implements WebModel{
	@NotNull(message = "Идентификатор не может быть Null!")
	private Long id;
	@NotBlank(message = "Наименование не может быть пустым!")
	private String name;
	@Max(200)
	private String description;
	@ValidOfLocalDate1895
	private LocalDate releaseDate;
	@ValidPositiveDuration
	private Duration duration;
}
