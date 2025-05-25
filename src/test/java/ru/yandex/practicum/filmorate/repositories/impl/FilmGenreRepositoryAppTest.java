package ru.yandex.practicum.filmorate.repositories.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.repositories.FilmGenreRepository;
import ru.yandex.practicum.filmorate.repositories.rowmapper.FilmGenreRowMapper;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({FilmGenreRepository.class, FilmGenreRowMapper.class})
public class FilmGenreRepositoryAppTest {

	private final FilmGenreRepository rep;

	private FilmGenre filmGenre;

	@BeforeEach
	void setUp() {
		for (Long id = 1L; id <= 3; ++id) {
			filmGenre = new FilmGenre();
			filmGenre.setFilmId(id);
			filmGenre.setGenreId(id);
			FilmGenre actual = rep.save(filmGenre);
			assertTrue(actual != null);
			assertTrue(actual.getPrimaryKey().equals(id));
		}
	}

	@Test
	void findAllTest() {
		List<FilmGenre> actual = rep.findAll();
		assertTrue(actual.isEmpty());
	}

	@Test
	void findByPrimaryKeyTest() {
		Long primaryKey = 1L;
		Optional<FilmGenre> filmGenreOpt = rep.findByPrimaryKey(primaryKey);
		assertTrue(filmGenreOpt.isPresent());
	}

	@Test
	void findByFilmIdTest() {
		Long filmId = 1L;
		List<FilmGenre> actual = rep.findByFilmId(filmId);
		assertFalse(actual.isEmpty());
	}

	@Test
	void findByGenreIdTest() {
		Long genreId = 1L;
		List<FilmGenre> actual = rep.findByGenreId(genreId);
		assertFalse(actual.isEmpty());
	}

	@Test
	void deleteByPrimaryKeyTest() {
		Long primaryKey = 1L;
		assertTrue(rep.deleteByPrimaryKey(primaryKey));
	}

	@Test
	void deleteByFilmIdAndGenreIdTest() {
		boolean hasDelete = rep.deleteByFilmIdAndGenreId(3L, 3L);
		assertTrue(hasDelete);
	}
}
