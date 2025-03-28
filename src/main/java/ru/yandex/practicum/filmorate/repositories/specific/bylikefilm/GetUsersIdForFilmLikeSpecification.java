package ru.yandex.practicum.filmorate.repositories.specific.bylikefilm;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.impl.FilmUser;
import ru.yandex.practicum.filmorate.repositories.Specification;
import ru.yandex.practicum.filmorate.repositories.rowmapper.FilmUserRowMapper;

@Slf4j
@Repository
@RequiredArgsConstructor
public class GetUsersIdForFilmLikeSpecification implements Specification<Long[], Set<Long>> {

	private final JdbcTemplate jdbc;
	private final FilmUserRowMapper rowMapper;

	private static final Integer LIMIT = 200;
	private static final String QUERY = "SELECT * FROM film_person WHERE film_id = ? LIMIT ? OFFSET ?";

	@Override
	public Set<Long> specified(Long[] params, Set<Long> ans) {
		Long filmId = params[0];
		Integer offset = params[1].intValue();
		log.trace("filmId: {}, offset: {}", filmId, offset);
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setLong(1, filmId);
				ps.setInt(2, LIMIT);
				ps.setInt(3, offset);
			}
		};
		ans = jdbc.queryForStream(QUERY, pss, rowMapper).map(FilmUser::getUserId).collect(Collectors.toSet());
		return ans;
	}

}
