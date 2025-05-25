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
	private static final String FIND_BY_LOGIN = "SELECT * FROM person WHERE login LIKE ?";
	private static final String FIND_BY_NAME = "SELECT * FROM person WHERE name LIKE ?";
	private static final String FIND_BY_EMAIL = "SELECT * FROM person WHERE email = ?";
	private static final String FIND_BY_ID = "SELECT * FROM person WHERE person_id = ?";

	private static final String INSERT_QUERY = "INSERT INTO person(email, login, name, birthday)"
			+ "VALUES (?, ?, ?, ?) RETURNING person_id";
	private static final String UPDATE_QUERY = "UPDATE person SET email = ?, login = ?, name = ?, birthday = ? WHERE person_id = ?";

	private static final String DELETE_USER_BY_ID = "DELETE FROM person WHERE person_id = ?";
	private static final String DELETE_USER_BY_EMAIL = "DELETE FROM person WHERE email = ?";

	public UserRepository(JdbcTemplate jdbc, RowMapper<User> mapper) {
		super(jdbc, mapper);
	}

	public List<User> findAll() {
		return findMany(FIND_ALL_QUERY);
	}

	public Optional<User> findById(Long id) {
		return findOne(FIND_BY_ID, id);
	}

	public Optional<User> findByEmail(String email) {
		return findOne(FIND_BY_EMAIL, email);
	}

	public List<User> findByName(String name) {
		return findMany(FIND_BY_NAME, name);
	}

	public List<User> findByLogin(String login) {
		return findMany(FIND_BY_LOGIN, login);
	}

	public User save(User user) {
		Long id = insert(INSERT_QUERY, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
		user.setId(id);
		return user;
	}

	public User update(User user) {
		update(UPDATE_QUERY, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
		return user;
	}

	public boolean deleteUserById(Long id) {
		return delete(DELETE_USER_BY_ID, id);
	}

	public boolean deleteUserByEmail(String email) {
		return delete(DELETE_USER_BY_EMAIL, email);
	}

}
