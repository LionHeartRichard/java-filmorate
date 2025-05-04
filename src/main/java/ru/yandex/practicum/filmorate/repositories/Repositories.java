package ru.yandex.practicum.filmorate.repositories;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public interface Repositories<T> {
	Optional<Long> add(T t);

	Optional<Integer> update(T t);

	Collection<T> getTable();

	Stream<T> getStream();

	Optional<T> getById(Long id);
}
