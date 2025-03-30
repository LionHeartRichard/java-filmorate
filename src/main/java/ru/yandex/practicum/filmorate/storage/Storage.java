package ru.yandex.practicum.filmorate.storage;

import java.util.Collection;

public interface Storage<R, C, U> {

	public R create(C createDto);

	public Collection<R> read();

	public R update(U updateDto);

	public R findById(Long id);
}
