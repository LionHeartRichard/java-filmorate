package ru.yandex.practicum.filmorate.util;

import java.util.Collection;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

@Slf4j
@Component
public class LocalValidator {

	public void collectionNotNull(Collection<?> collection, String message) {
		if (collection == null) {
			log.warn("Не пройдена валидация коллекции. {}", message);
			throw new NotFoundException(message);
		}
	}

	public void positiveValue(Long value, String message) {
		if (value == null || value < 0) {
			log.warn("Не пройдена валидация. {}", message);
			throw new NotFoundException(message);
		}
	}

	public void positiveValue(Integer value, String message) {
		if (value == null || value < 0) {
			log.warn("Не пройдена валидация. {}", message);
			throw new NotFoundException(message);
		}
	}

	public void positiveValue(Double value, String message) {
		if (value == null || value < 0) {
			log.warn("Не пройдена валидация. {}", message);
			throw new NotFoundException(message);
		}
	}
}
