package ru.yandex.practicum.filmorate.repositories.specific.byfilm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.repositories.rowmapper.FilmGenreRowMapper;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({FilmFindByGenreSpecification.class, FilmGenreRowMapper.class})
public class FilmFindByGenreSpecificationAppTest {

	private final FilmFindByGenreSpecification filmSpecification;

	@Test
	void getFilmsByGenreName() {
		String genre = "Action";
		Integer offset = 0;
		Object[] params = new Object[2];
		params[0] = genre;
		params[1] = offset;

		Set<Long> filmIdSet = filmSpecification.specified(params, new HashSet<>());

		assertTrue(!filmIdSet.isEmpty());
	}
}
