package ru.yandex.practicum.filmorate.repositories.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.impl.User;
import ru.yandex.practicum.filmorate.repositories.BaseRepo;
import ru.yandex.practicum.filmorate.repositories.Repositories;
import ru.yandex.practicum.filmorate.repositories.rowmapper.UserRowMapper;

@Slf4j
@Repository
public class UserRepositories extends BaseRepo<User> implements Repositories<User> {

	@Autowired
	public UserRepositories(JdbcTemplate jdbc, UserRowMapper rowMapper) {
		super(jdbc, rowMapper, "person", "person_id");
	}

	public Optional<Integer> remove(Long id) {
		log.trace("remove user, id: {}", id);
		return super.remove(id);
	}

	@Override
	public Optional<Long> add(User user) {
		log.trace("add user: {}", Optional.ofNullable(user).map(User::toString).orElse("null"));
		String queryInsert = "INSERT INTO person (email,login,name,birthday) VALUES (?,?,?,?)";
		Object[] fileds = {user.getEmail(), user.getLogin(), user.getName(), user.getBirthday()};
		return super.addByGeneratedKey(queryInsert, fileds);
	}

	@Override
	public Optional<Integer> update(User user) {
		log.trace("user update: {}", Optional.ofNullable(user).map(User::toString).orElse("null"));
		String updateUser = "UPDATE person SET email = ?, login = ?, name = ?, birthday = ?  WHERE person_id = ?";
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
		return super.update(updateUser, pss);
	}

	@Override
	public Stream<User> getStream(Integer offset) {
		return super.getStreamByTable(offset);
	}
}
