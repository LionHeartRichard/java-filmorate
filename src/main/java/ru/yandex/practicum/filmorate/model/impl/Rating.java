package ru.yandex.practicum.filmorate.model.impl;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Rating {
	private String name;
}
