package ru.yandex.practicum.filmorate.exception;

import java.util.Map;

public class NotValidParamException extends RuntimeException {
	private final StringBuilder message;

	@SafeVarargs
	public NotValidParamException(Map<String, String>... params) {
		message = new StringBuilder("Не валидные параметры:\n");
		for (Map<String, String> param : params) {
			param.forEach((k, v) -> message.append(k).append(": ").append(v).append(";\n"));
		}
	}

	@Override
	public String getMessage() {
		return message.toString();
	}
}
