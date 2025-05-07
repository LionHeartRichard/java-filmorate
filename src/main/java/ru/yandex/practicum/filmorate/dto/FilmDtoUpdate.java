package ru.yandex.practicum.filmorate.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilmDtoUpdate {
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long id;
	private String name;
	private String description;
	private LocalDate releaseDate;
	private Integer duration;
}
