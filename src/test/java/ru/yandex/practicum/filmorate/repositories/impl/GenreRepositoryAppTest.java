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
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repositories.GenreRepository;
import ru.yandex.practicum.filmorate.repositories.rowmapper.GenreRowMapper;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({GenreRepository.class, GenreRowMapper.class})
public class GenreRepositoryAppTest {
	private final GenreRepository rep;

	private Genre genre;

	private static int count = 4;

	@BeforeEach
	void setUp() {
		while (count++ <= 7) {
			genre = new Genre(null, "new-GENRE" + count);
			Genre actual = rep.save(genre);
			assertTrue(actual.getGenreId() > 0L);
		}
	}

	@Test
	void findAllTest() {
		List<Genre> actual = rep.findAll();
		assertTrue(!actual.isEmpty());
	}

	@Test
	void findByIdTest() {
		Long id = 1L;
		Optional<Genre> actual = rep.findById(id);
		assertTrue(actual.isPresent());
	}

	@Test
	void findByNameTest() {
		String name = "Thr%";
		List<Genre> actual = rep.findByName(name);
		assertTrue(!actual.isEmpty());
	}

	@Test
	void findByFullNameTest() {
		String fullName = "Action";
		List<Genre> actual = rep.findByFullName(fullName);
		assertTrue(!actual.isEmpty());
	}

	@Test
	void update() {
		Genre genre = new Genre();
		genre.setGenreId(3L);
		genre.setName("Fantasy-Update");
		Genre actual = rep.update(genre);
		assertTrue(actual != null);
	}
}
