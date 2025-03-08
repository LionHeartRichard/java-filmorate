package ru.yandex.practicum.filmorate.storage.impl;

import java.util.Collection;

import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.model.impl.User;
import ru.yandex.practicum.filmorate.storage.Storage;

@Component
public class UserStorage implements Storage<User> {

	@Override
	public User create(User t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<User> read() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User update(User t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User delete(User t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<User> findByParam(String... args) {
		// TODO Auto-generated method stub
		return null;
	}

}
