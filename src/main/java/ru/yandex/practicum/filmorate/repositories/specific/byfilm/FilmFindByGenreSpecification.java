package ru.yandex.practicum.filmorate.repositories.specific.byfilm;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.impl.FilmGenre;
import ru.yandex.practicum.filmorate.repositories.Specification;
import ru.yandex.practicum.filmorate.repositories.rowmapper.FilmGenreRowMapper;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FilmFindByGenreSpecification implements Specification<Object[], Set<Long>> {

	private final JdbcTemplate jdbc;
	private final FilmGenreRowMapper rowMapper;

	private static final Integer LIMIT = 200;
	private static final String QUERY = "SELECT * FROM film_genre WHERE name = ? LIMIT ? OFFSET ?";

	@Override
	public Set<Long> specified(Object[] params, Set<Long> ans) {
		String genre = String.valueOf(params[0]);
		Integer offset = (Integer) params[1];
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, genre);
				ps.setInt(2, LIMIT);
				ps.setInt(3, offset);
			}
		};
		ans = jdbc.queryForStream(QUERY, pss, rowMapper).map(FilmGenre::getFilmId).collect(Collectors.toSet());
		return ans;
	}

}
