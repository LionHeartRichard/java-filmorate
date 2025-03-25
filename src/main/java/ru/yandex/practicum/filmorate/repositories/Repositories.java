package ru.yandex.practicum.filmorate.repositories;

import java.util.Collection;
import java.util.Optional;

public interface Repositories<T> {
	Optional<Long> add(T t);

	Optional<Integer> remove(Long id);

	Optional<Integer> update(T t);

	Collection<T> query();
}
