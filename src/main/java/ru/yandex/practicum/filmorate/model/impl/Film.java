package ru.yandex.practicum.filmorate.model.impl;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.WebModel;
import ru.yandex.practicum.filmorate.validation.ThisAfter1895;

@Validated
@Data
@Builder(toBuilder = true)
public class Film implements WebModel {

	private Long id;
	@NotBlank(message = "Наименование не может быть пустым!")
	private String name;
	@Size(min = 0, max = 200, message = "Описание не может быть больше 200 символов")
	private String description;
	@ThisAfter1895(message = "Дата релиза фильма не может быть раньше чем выпущен первый фильм (1895-12-28)!!!")
	private LocalDate releaseDate;
	@Min(value = 1, message = "Продолжительность фильма не может быть отрицательной или равной 0!!!")
	private Integer duration;
	private Set<Long> likes;

	public Film(Long id, @NotBlank(message = "Наименование не может быть пустым!") String name,
			@Size(min = 0, max = 200, message = "Описание не может быть больше 200 символов") String description,
			LocalDate releaseDate,
			@Min(value = 1, message = "Продолжительность фильма не может быть отрицательной или равной 0!!!") Integer duration,
			Set<Long> likes) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.releaseDate = releaseDate;
		this.duration = duration;
		this.likes = likes == null ? new HashSet<>() : likes;
	}
}
