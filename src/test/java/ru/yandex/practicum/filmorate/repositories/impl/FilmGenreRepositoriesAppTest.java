package ru.yandex.practicum.filmorate.repositories.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.impl.FilmGenre;
import ru.yandex.practicum.filmorate.repositories.rowmapper.FilmGenreRowMapper;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({FilmGenreRepositories.class, FilmGenreRowMapper.class})
public class FilmGenreRepositoriesAppTest {

	private final FilmGenreRepositories rep;

	private FilmGenre filmGenre;

	private static int count = 0;

	@BeforeEach
	void setUp() {
		++count;
		filmGenre = FilmGenre.builder().filmId(1L).name("fantasy" + count).build();

		Optional<Long> actual = rep.add(filmGenre);

		assertTrue(actual.isPresent());
	}

	void addWhenFilmGenreValidThenReturnLong() {
		filmGenre.setName("fantasy-add");

		Optional<Long> actual = rep.add(filmGenre);

		assertTrue(actual.isPresent());
	}

	void updateWhenFilmGenreValidThenReturnInteger() {
		filmGenre.setName("fantasy-update");

		Optional<Integer> actual = rep.update(filmGenre);

		assertTrue(actual.isPresent());
	}

	void removeByFilmIdAndGenreName() {
		Optional<Integer> actual = rep.remove(1L, "fantasy1");

		assertTrue(actual.isPresent());
		assertEquals(1, actual.get());
	}
}
