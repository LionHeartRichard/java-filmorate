package ru.yandex.practicum.filmorate.repositories.specific.bylikefilm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.repositories.rowmapper.FilmUserRowMapper;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({GetUsersIdForFilmLikeSpecification.class, FilmUserRowMapper.class})
public class GetUsersIdForFilmLikeSpecificationAppTest {
	private final GetUsersIdForFilmLikeSpecification likeForFilm;

	@Test
	void getLikeForFilm() {
		Long filmId = 1L;
		Integer offset = 0;
		Long[] params = {filmId, offset.longValue()};

		Set<Long> likes = likeForFilm.specified(params, new HashSet<>());

		assertTrue(!likes.isEmpty());
	}
}
