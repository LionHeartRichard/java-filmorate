package ru.yandex.practicum.filmorate.repositories.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repositories.rowmapper.FilmRowMapper;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({FilmRepositories.class, FilmRowMapper.class})
public class FilmRepositoriesAppTest {
	private final FilmRepositories rep;

	private Film film;

	@BeforeEach
	void setUp() {
		film = Film.builder()
				.id(null)
				.name("name Set-Up")
				.description("description set up")
				.releaseDate(LocalDate.now())
				.duration(120)
				.build();
	}

	@Test
	void addWhenValidDataThenReturnFilmId() {
		Optional<Long> actual = rep.add(film);

		assertTrue(actual.isPresent());
	}

	@Test
	void updateWhenValidDataThenReturnNumbersRowsUpdate() {
		film.setId(1L);

		Optional<Integer> actual = rep.update(film);

		assertTrue(actual.isPresent());
		assertEquals(1, actual.get());
	}

	@Test
	void removeWhenValidDataThenReturnNumbersRowsRemove() {
		Film filmRemove = Film.builder()
				.id(null)
				.name("RemoveName")
				.description("Remove desc")
				.releaseDate(LocalDate.now())
				.duration(50)
				.build();
		filmRemove.setId(rep.add(filmRemove).get());

		Optional<Integer> actual = rep.remove(filmRemove.getId());

		assertTrue(actual.isPresent());
		assertEquals(1, actual.get());
	}
}
