package ru.yandex.practicum.filmorate.repositories.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.repositories.rowmapper.FilmGenreRowMapper;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({FilmGenreRepositories.class, FilmGenreRowMapper.class})
public class FilmGenreRepositoriesAppTest {

	private final FilmGenreRepositories rep;

	private FilmGenre filmGenre;

	@BeforeEach
	void setUp() {
		for (Long id = 1L; id <= 3; ++id) {
			filmGenre = FilmGenre.builder().filmId(id).genreId(id).build();
			Optional<Long> actual = rep.add(filmGenre);
			assertTrue(actual.isPresent());
		}
	}

	@Test
	void removeByFilmIdAndGenreId() {
		Optional<Integer> actual = rep.remove(3L, 3L);

		assertTrue(actual.isPresent());
	}
}
