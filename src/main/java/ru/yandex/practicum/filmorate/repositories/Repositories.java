package ru.yandex.practicum.filmorate.repositories;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public interface Repositories<T> {
	Optional<Long> add(T t);

	Optional<Integer> update(T t);

	Collection<T> getTable(Integer offset);

	Stream<T> getStream(Integer offset);

	Optional<T> getById(Long id);
}
