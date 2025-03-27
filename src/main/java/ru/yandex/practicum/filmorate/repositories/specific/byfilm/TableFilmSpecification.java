package ru.yandex.practicum.filmorate.repositories.specific.byfilm;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.impl.Film;
import ru.yandex.practicum.filmorate.repositories.Specification;
import ru.yandex.practicum.filmorate.repositories.rowmapper.FilmRowMapper;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TableFilmSpecification implements Specification<Integer, List<Film>> {

	private final JdbcTemplate jdbc;
	private final FilmRowMapper rowMapper;

	private static final String QUERY = "SELECT * FROM film LIMIT ? OFFSET ?";
	private static final Integer PAGE_LIMIT = 100;

	@Override
	public List<Film> specified(Integer offset, List<Film> ans) {
		log.trace("film all, OFFSET: {}", offset);
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, PAGE_LIMIT);
				ps.setInt(2, offset);
			}
		};
		log.trace("film all, SQL: {}", QUERY);
		ans = jdbc.query(QUERY, pss, rowMapper);
		return ans;
	}

}
