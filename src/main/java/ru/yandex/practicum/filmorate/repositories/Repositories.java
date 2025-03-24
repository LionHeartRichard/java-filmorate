package ru.yandex.practicum.filmorate.repositories;

import java.util.Collection;
import java.util.Optional;

public interface Repositories<T> {
	Optional<Long> add(T row);

	Optional<Integer> remove(T row);

	Optional<Integer> update(T row);

	Collection<T> query();
}
