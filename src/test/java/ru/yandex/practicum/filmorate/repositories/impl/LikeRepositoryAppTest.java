package ru.yandex.practicum.filmorate.repositories.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
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
import ru.yandex.practicum.filmorate.repositories.LikeRepository;
import ru.yandex.practicum.filmorate.repositories.rowmapper.LikeRowMapper;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({LikeRepository.class, LikeRowMapper.class})
public class LikeRepositoryAppTest {
	private final LikeRepository rep;

	private Like like;

	@BeforeEach
	void setUp() {
		for (Long id = 1L; id <= 3; ++id) {
			like = new Like();
			like.setFilmId(1L);
			like.setUserId(id);
			Like actual = rep.save(like);
			assertTrue(actual != null);
			assertTrue(actual.getLikeId().equals(id));
		}

		for (Long id = 1L; id <= 3; ++id) {
			like = new Like();
			like.setFilmId(2L);
			like.setUserId(id);
			Like actual = rep.save(like);
			assertTrue(actual != null);
			assertTrue(actual.getLikeId().equals(id));
		}

		for (Long id = 1L; id <= 3; ++id) {
			like = new Like();
			like.setFilmId(3L);
			like.setUserId(id);
			Like actual = rep.save(like);
			assertTrue(actual != null);
			assertTrue(actual.getLikeId().equals(id));
		}
	}

	@Test
	void findAllTest() {
		List<Like> actual = rep.findAll();
		assertTrue(!actual.isEmpty());
	}

	@Test
	void findByPrimaryKeyTest() {
		Long primaryKey = 1L;
		Optional<Like> actual = rep.findByPrimaryKey(primaryKey);
		assertTrue(actual.isPresent());
	}

	@Test
	void findLikeTest() {
		Long filmId = 1L;
		Long userId = 1L;
		Optional<Like> actual = rep.findLike(filmId, userId);
		assertTrue(!actual.isPresent());
	}

	@Test
	void findByFilmId() {
		Long filmId = 1L;
		List<Like> actual = rep.findByFilmId(filmId);
		assertTrue(!actual.isEmpty());
	}

	@Test
	void findByUserId() {
		Long userId = 1L;
		List<Like> actual = rep.findByUserId(userId);
		assertTrue(!actual.isEmpty());
	}

	@Test
	void deleteByIdTest() {
		Long likeId = 1L;
		assertTrue(rep.deleteById(likeId));
	}

	@Test
	void deleteByFilmIdTest() {
		Long filmId = 2L;
		assertTrue(rep.deleteByFilmId(filmId));
	}

	@Test
	void deleteByUserIdTest() {
		Long userId = 3L;
		assertTrue(rep.deleteByUserId(userId));
	}

}
