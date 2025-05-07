package ru.yandex.practicum.filmorate.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ru.yandex.practicum.filmorate.validation.ThisAfter1895;

public class FilmDtoCreate {
	@NotBlank
	@Size(max = 50)
	private String name;
	@Size(min = 0, max = 200)
	private String description;
	@ThisAfter1895
	private LocalDate releaseDate;
	@Min(value = 1)
	private Integer duration;
}
