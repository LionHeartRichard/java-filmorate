package ru.yandex.practicum.filmorate.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.util.GetConstants;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Data
public class FilmDtoUpdate {

	// @JsonProperty(access = JsonProperty.Access.READ_ONLY) ?!!!!!!!!!!!!!!
	Long id;
	String name;
	String description;
	LocalDate releaseDate;
	Integer duration;
	Mpa mpa;
	List<Director> directors;

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
