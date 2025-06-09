package ru.yandex.practicum.filmorate.repositories;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
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
@Import({LikeRepository.class, LikeRowMapper.class})
public class LikeRepositoryAppTest {
	private final LikeRepository rep;
	private Like like;
	private static Long filmId = 0l;
	private static Long userId = 0l;

	@BeforeEach
	void setUp() {
		like = new Like();
		filmId = filmId < 3 ? filmId + 1 : filmId - 1;
		like.setFilmId(filmId);
		userId = userId < 3 ? userId + 1 : userId - 1;
		like.setUserId(userId);
		rep.save(like);
	}

	@Test
	void findAllTest() {
		List<Like> actual = rep.findAll();
		assertTrue(!actual.isEmpty());
	}

	@Test
	void findByPrimaryKeyTest() {
		Optional<Like> actual = rep.findByPrimaryKey(like.getLikeId());
		assertTrue(actual.isPresent());
	}

	@Test
	void findLikeTest() {
		Optional<Like> actual = rep.findLike(like.getFilmId(), like.getUserId());
		assertTrue(actual.isPresent());
	}

	@Test
	void findByFilmId() {
		List<Like> actual = rep.findByFilmId(like.getFilmId());
		assertTrue(!actual.isEmpty());
	}

	@Test
	void findByUserId() {
		List<Like> actual = rep.findByUserId(like.getUserId());
		assertTrue(!actual.isEmpty());
	}

	@Test
	void deleteByIdTest() {
		assertTrue(rep.deleteById(like.getLikeId()));
	}

	@Test
	void deleteByFilmIdTest() {
		assertTrue(rep.deleteByFilmId(like.getFilmId()));
	}

	@Test
	void deleteByUserIdTest() {
		assertTrue(rep.deleteByUserId(like.getUserId()));
	}

}
