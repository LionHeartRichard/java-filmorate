package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;

public interface Controller<T> {
	T create(T t);

	Collection<T> doGet();

	T update(T t);

}
