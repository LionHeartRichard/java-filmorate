package ru.yandex.practicum.filmorate.model.impl;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Friend {
	private Long primaryKey;
	private Long userId;
	private Long friendId;
}
