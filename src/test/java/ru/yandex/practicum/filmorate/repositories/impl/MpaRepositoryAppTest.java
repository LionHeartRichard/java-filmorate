package ru.yandex.practicum.filmorate.repositories.impl;

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
import ru.yandex.practicum.filmorate.repositories.MpaRepository;
import ru.yandex.practicum.filmorate.repositories.rowmapper.MpaRowMapper;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({MpaRepository.class, MpaRowMapper.class})
public class MpaRepositoryAppTest {
	private final MpaRepository rep;

	private Mpa mpa;

	private static Long count = 1L;

	@BeforeEach
	void setUp() {
		while (count++ <= 3L) {
			mpa = new Mpa();
			mpa.setFilmId(count);
			mpa.setName("new_MPA");
			Mpa actual = rep.save(mpa);
			assertTrue(actual != null);
			assertTrue(actual.getId() > 0L);
		}
	}

	@Test
	void findAllTest() {
		List<Mpa> actual = rep.findAll();
		assertTrue(!actual.isEmpty());
	}

	@Test
	void findByIdTest() {
		Long id = 1L;
		Optional<Mpa> actual = rep.findById(id);
		assertTrue(actual.isPresent());
	}

	@Test
	void findByNameTest() {
		String name = "%Mp%";
		List<Mpa> actual = rep.findByName(name);
		assertTrue(!actual.isEmpty());
	}

	@Test
	void findByFullNameTest() {
		String fullName = "new_MPA";
		List<Mpa> actual = rep.findByFullName(fullName);
		assertTrue(!actual.isEmpty());
	}

	@Test
	void findByFilmIdTest() {
		Long filmId = 1L;
		Optional<Mpa> actual = rep.findByFilmId(filmId);
		assertTrue(actual.isPresent());
	}

	public Mpa update(Mpa mpa) {
		update(UPDATE_QUERY, mpa.getName(), mpa.getId());
		return mpa;
	}

	public boolean deleteById(Long mpaId) {
		return delete(DELETE_MPA_BY_ID, mpaId);
	}

	public boolean deleteByFilmId(Long filmId) {
		return delete(DELETE_MPA_BY_FILM_ID, filmId);
	}
}
