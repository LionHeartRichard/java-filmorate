package ru.yandex.practicum.filmorate.model.impl;

import java.time.LocalDate;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.WebModel;
import ru.yandex.practicum.filmorate.validation.ThisAfter1895;
import ru.yandex.practicum.filmorate.validation.NotNegativeValue;

@Validated
@Data
@Builder(toBuilder = true)
public class Film implements WebModel {

	private Long id;
	@NotBlank(message = "Наименование не может быть пустым!")
	private String name;
	@Size(min = 0, max = 200, message = "Описание не может быть больше 200 символов")
	private String description;
	@ThisAfter1895
	private LocalDate releaseDate;
	@NotNegativeValue
	private Integer duration;
}
