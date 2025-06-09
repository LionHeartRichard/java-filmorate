package ru.yandex.practicum.filmorate.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.repositories.rowmapper.MpaRowMapper;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({MpaRepository.class, MpaRowMapper.class})
public class MpaRepositoryAppTest {

	private final MpaRepository rep;
	private Mpa mpa;
	private static Long filmId = 0l;

	@BeforeEach
	void setUp() {
		mpa = new Mpa();
		filmId = filmId < 3 ? filmId + 1 : filmId - 1;
		mpa.setFilmId(filmId);
		mpa.setName("new_MPA");
		rep.save(mpa);
	}

	@Test
	void findAllTest() {
		List<Mpa> actual = rep.findAll();
		assertTrue(!actual.isEmpty());
	}

	@Test
	void findByIdTest() {
		Optional<Mpa> actual = rep.findById(mpa.getId());
		assertTrue(actual.isPresent());
	}

	@Test
	void findByNameTest() {
		String name = "%MP%";
		List<Mpa> actual = rep.findByName(name);
		assertTrue(!actual.isEmpty());
	}

	@Test
	void findByFullNameTest() {
		List<Mpa> actual = rep.findByFullName(mpa.getName());
		assertTrue(!actual.isEmpty());
	}

	@Test
	void findByFilmIdTest() {
		Optional<Mpa> actualOpt = rep.findByFilmId(mpa.getFilmId());
		assertTrue(actualOpt.isPresent());
	}

	@Test
	void updateTest() {
		String expected = "UPDATE_MPA_Name";
		mpa.setName(expected);
		rep.update(mpa);
		assertEquals(expected, mpa.getName());
	}
}
