package ru.yandex.practicum.filmorate.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.ThisAfter1895;

@Data
@Builder
public class FilmDto {
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long id;
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
