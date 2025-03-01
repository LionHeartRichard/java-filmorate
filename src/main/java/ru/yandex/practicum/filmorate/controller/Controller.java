package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;

public interface Controller<T> {
	T doPost(T t);

	Collection<T> doGet();

	T doPut(T t);

}
