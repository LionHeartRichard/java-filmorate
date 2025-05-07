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
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repositories.rowmapper.GenreRowMapper;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({GenreRepositories.class, GenreRowMapper.class})
public class GenreRepositoriesAppTest {
	private final GenreRepositories rep;

	private Genre genre;

	private static int count = 4;

	@BeforeEach
	void setUp() {
		while (count++ <= 7) {
			genre = Genre.builder().name("new-GENRE" + count).build();
			Optional<Long> actual = rep.add(genre);
			assertTrue(actual.isPresent());
		}
	}

	@Test
	void removeGenreWhenValidDataThrenReturnNumbersRows() {
		Optional<Integer> actual = rep.remove(5L);

		assertTrue(actual.isPresent());
	}
}
