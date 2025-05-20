package ru.yandex.practicum.filmorate.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.User;

@Repository
public class UserRepository extends BaseRepository<User> {

	private static final String FIND_ALL_QUERY = "SELECT * FROM person";
	private static final String FIND_BY_EMAIL_QUERY = "SELECT * FROM person WHERE email = ?";
	private static final String FIND_BY_ID_QUERY = "SELECT * FROM person WHERE person_id = ?";
	private static final String INSERT_QUERY = "INSERT INTO person(email, login, name, birthday)"
			+ "VALUES (?, ?, ?, ?) returning person_id";
	private static final String UPDATE_QUERY = "UPDATE person SET email = ?, login = ?, name = ?, birthday = ? WHERE person_id = ?";
	private static final String DELETE_USER_BY_ID = "DELETE FROM person WHERE person_id = ?";

	public UserRepository(JdbcTemplate jdbc, RowMapper<User> mapper) {
		super(jdbc, mapper);
	}

	public List<User> findByAll() {
		return findMany(FIND_ALL_QUERY);
	}

	public Optional<User> findById(Long id) {
		return findOne(FIND_BY_ID_QUERY, id);
	}

	public Optional<User> findByEmail(String email) {
		return findOne(FIND_BY_EMAIL_QUERY, email);
	}

	public User save(User user) {
		Long id = insert(INSERT_QUERY, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
		user.setId(id);
		return user;
	}

	public User update(User user) {
		update(UPDATE_QUERY, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
		return user;
	}

	public boolean deleteUserById(Long id) {
		return delete(DELETE_USER_BY_ID, id);
	}

}
