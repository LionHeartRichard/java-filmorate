package ru.yandex.practicum.filmorate.repositories.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.repositories.rowmapper.LikeRowMapper;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({LikeRepositories.class, LikeRowMapper.class})
public class LikeRepositoriesAppTest {
	private final LikeRepositories rep;

	private Like like;

	@BeforeEach
	void setUp() {
		like = Like.builder().filmId(1L).userId(1L).build();
	}

	@Test
	void addLike() {
		Optional<Long> actual = rep.add(like);

		assertTrue(actual.isPresent());
	}

	@Test
	void removeLike() {
		like.setFilmId(1L);
		like.setUserId(3L);
		Optional<Long> addRow = rep.add(like);
		assertTrue(addRow.isPresent());

		Optional<Integer> actual = rep.remove(1L, 3L);

		assertTrue(actual.isPresent());
	}

	@Test
	void countLikesTopFilm() {
		like.setFilmId(3L);
		like.setUserId(1L);
		rep.add(like);
		like.setUserId(3L);
		rep.add(like);

		Map<Long, Integer> actual = rep.getTopFilms(10);

		assertTrue(actual.size() >= 1);
	}
}
