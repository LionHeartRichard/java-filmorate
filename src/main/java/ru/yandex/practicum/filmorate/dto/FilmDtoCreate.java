package ru.yandex.practicum.filmorate.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.validation.ThisAfter1895;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Validated
@Data
public class FilmDtoCreate {
	@NotBlank
	@Size(max = 50)
	String name;
	@Size(min = 0, max = 200)
	String description;
	@ThisAfter1895
	LocalDate releaseDate;
	@Min(value = 1)
	Integer duration;
	Mpa mpa;
	List<Genre> genres;
}
