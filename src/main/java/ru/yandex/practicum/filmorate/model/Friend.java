package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Friend {
	private Long primaryKey;
	private Long userId;
	private Long friendId;
}
