package ru.yandex.practicum.filmorate.repositories.specific;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.repositories.Specification;
import ru.yandex.practicum.filmorate.repositories.rowmapper.FilmRowMapper;
import ru.yandex.practicum.filmorate.model.impl.Film;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FilmNameSpecification implements Specification<String, List<Film>> {

	private static final String QUERY = "SELECT * FROM film WHERE name = ?";
	private final JdbcTemplate jdbc;
	private final FilmRowMapper rowMapper;

	@Override
	public List<Film> specified(String name, List<Film> ans) {
		log.trace("Query search film by name: {}", name);
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, name);
			}
		};
		ans = jdbc.query(QUERY, pss, rowMapper);
		log.trace("SQL: {}", QUERY);
		return ans;
	}

}
