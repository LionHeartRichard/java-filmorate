package ru.yandex.practicum.filmorate.dto;

import java.time.LocalDate;

import lombok.Data;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.util.GetConstants;

@Data
public class FilmDtoUpdate {

	// @JsonProperty(access = JsonProperty.Access.READ_ONLY) ?!!!!!!!!!!!!!!
	private final Long id;
	private final String name;
	private final String description;
	private final LocalDate releaseDate;
	private final Integer duration;
	private final Mpa mpa;

	public boolean hasName() {
		return !(name == null || name.isBlank() || name.length() > 100);
	}

	public boolean hasDescription() {
		return !(description == null || description.isBlank() || description.length() > 250);
	}

	public boolean hasReleaseDate() {
		return !(releaseDate == null || releaseDate.isBefore(GetConstants.THRESHOLD_DATE));
	}

	public boolean hasDuration() {
		return !(duration == null || duration < 1);
	}

	public boolean hasMpaId() {
		return !(mpa == null || mpa.getId() == null);
	}
}
