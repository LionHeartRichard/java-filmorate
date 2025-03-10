package ru.yandex.practicum.filmorate.storage;

import java.util.Collection;

public interface Storage<T> {

	public T create(T t);

	public Collection<T> read();

	public T update(T t);

	// public T delete(Long id);

	public T findById(Long id);
}
