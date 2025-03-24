package ru.yandex.practicum.filmorate.repositories;

public interface Specification<T, A> {
	A specified(T param, A ans);
}
