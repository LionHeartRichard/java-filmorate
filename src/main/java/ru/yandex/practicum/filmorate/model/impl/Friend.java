package ru.yandex.practicum.filmorate.model.impl;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.WebModel;

@Data
@Builder(toBuilder = true)
public class Friend implements WebModel {
	private Long userId;
	private Long friendId;
	private String statusName;
}
