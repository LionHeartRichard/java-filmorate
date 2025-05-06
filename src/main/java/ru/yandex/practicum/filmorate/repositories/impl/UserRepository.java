package ru.yandex.practicum.filmorate.repositories.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.impl.User;
import ru.yandex.practicum.filmorate.repositories.BaseRepository;

@Repository
public class UserRepository extends BaseRepository<User>{

	public UserRepository(JdbcTemplate jdbc, RowMapper<User> mapper) {
		super(jdbc, mapper);
	}

}
