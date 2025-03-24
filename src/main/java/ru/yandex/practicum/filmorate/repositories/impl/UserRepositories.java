package ru.yandex.practicum.filmorate.repositories.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.impl.User;
import ru.yandex.practicum.filmorate.repositories.BaseOperations;
import ru.yandex.practicum.filmorate.repositories.Repositories;
import ru.yandex.practicum.filmorate.repositories.Specification;
import ru.yandex.practicum.filmorate.repositories.specific.UserEmailSpecification;
import ru.yandex.practicum.filmorate.repositories.specific.UserFindAllSpecification;
import ru.yandex.practicum.filmorate.repositories.specific.UserSpecification;

@Repository
@RequiredArgsConstructor
public class UserRepositories implements Repositories<UserDto, User> {

	private final static String TABLE_NAME = "person";

	private final UserFindAllSpecification userFindAll;
	private final UserEmailSpecification userFindByEmail;
	private final BaseOperations<UserDto> operations;

	@Override
	public Optional<Long> add(UserDto dto) {
		return operations.add(dto, TABLE_NAME);
	}

	@Override
	public Optional<Integer> remove(UserDto dto) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<Integer> update(UserDto row) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Collection<User> query() {
		return userFindAll.specified(null, new ArrayList<>());
	}

	public Optional<User> query(String email) {
		return userFindByEmail.specified(email, Optional.empty());
	}

}
