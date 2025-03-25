package ru.yandex.practicum.filmorate.repositories.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.impl.User;
import ru.yandex.practicum.filmorate.repositories.BaseOperations;
import ru.yandex.practicum.filmorate.repositories.Repositories;
import ru.yandex.practicum.filmorate.repositories.specific.UserEmailSpecification;
import ru.yandex.practicum.filmorate.repositories.specific.UserFindAllSpecification;
import ru.yandex.practicum.filmorate.repositories.specific.UserIdSpecification;

@Repository
@RequiredArgsConstructor
public class UserRepositories implements Repositories<User> {

	private static final String UPDATE_USER = "UPDATE person SET email = ?, login = ?, name = ?, birthday = ?  WHERE person_id = ?";
	private static final String TABLE_NAME = "person";
	private static final String ID = "person_id";

	private final UserFindAllSpecification userFindAll;
	private final UserEmailSpecification userFindByEmail;
	private final UserIdSpecification userFindByid;
	private final BaseOperations<User> operations;

	@Override
	public Optional<Long> add(User user) {
		return operations.add(user, TABLE_NAME);
	}

	@Override
	public Optional<Integer> remove(Long id) {
		return operations.remove(id, TABLE_NAME, ID);
	}

	@Override
	public Optional<Integer> update(User user) {
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, user.getEmail());
				ps.setString(2, user.getLogin());
				ps.setString(3, user.getName());
				ps.setDate(4, Date.valueOf(user.getBirthday()));
				ps.setLong(5, user.getId());
			}

		};
		return operations.update(UPDATE_USER, pss);
	}

	@Override
	public Collection<User> query() {
		return userFindAll.specified(0, new ArrayList<>());
	}

	public Optional<User> query(String email) {
		return userFindByEmail.specified(email, Optional.empty());
	}

	public Optional<User> query(Long id) {
		return userFindByid.specified(id, Optional.empty());
	}

}
