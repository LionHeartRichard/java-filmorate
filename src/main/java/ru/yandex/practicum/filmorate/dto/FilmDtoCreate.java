package ru.yandex.practicum.filmorate.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.ThisAfter1895;

@Data
public class FilmDtoCreate {
	@NotBlank
	@Size(max = 50)
	private final String name;
	@Size(min = 0, max = 200)
	private final String description;
	@ThisAfter1895
	private final LocalDate releaseDate;
	@Min(value = 1)
	private final Integer duration;
}
