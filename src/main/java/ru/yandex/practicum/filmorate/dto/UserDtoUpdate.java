package ru.yandex.practicum.filmorate.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.Login;

@Data
@Builder
public class UserDtoUpdate {
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long id;
	private String email;
	private String login;
	private String name;
	private LocalDate birthday;
}
