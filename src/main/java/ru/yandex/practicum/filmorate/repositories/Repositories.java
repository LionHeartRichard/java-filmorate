package ru.yandex.practicum.filmorate.repositories;

import java.util.Collection;
import java.util.Optional;

public interface Repositories<T> {
	Optional<Long> add(Object dto);

	Optional<Integer> remove(Object dto);

	Optional<Integer> update(Object dto);

	Collection<T> query();
}
