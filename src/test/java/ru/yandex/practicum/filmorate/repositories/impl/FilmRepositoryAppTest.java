package ru.yandex.practicum.filmorate.repositories.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repositories.FilmRepository;
import ru.yandex.practicum.filmorate.repositories.rowmapper.FilmRowMapper;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({FilmRepository.class, FilmRowMapper.class})
public class FilmRepositoryAppTest {
	private final FilmRepository rep;

	private Film film;

	@BeforeEach
	void setUp() {
		film = new Film();
		film.setId(null);
		film.setName("name Set-Up");
		film.setDescription("description set up");
		film.setReleaseDate(LocalDate.now());
		film.setDuration(120);
	}

	@Test
	void findAllTest() {
		List<Film> actual = rep.findAll();
		assertTrue(!actual.isEmpty());
	}

	@Test
	void findById() {
		Long id = 1L;
		Optional<Film> actual = rep.findById(id);
		assertTrue(actual.isPresent());
	}

	@Test
	void findByNameTest() {
		String name = "sta%";
		List<Film> actual = rep.findByName(name);
		assertTrue(!actual.isEmpty());
	}

	@Test
	void findByFullNameTest() {
		String name = "D hunter vampire";
		List<Film> actual = rep.findByFullName(name);
		assertTrue(!actual.isEmpty());
	}

	@Test
	void updateTest() {
		Film film = new Film(2L, "upName", "upDescription", LocalDate.of(2022, 2, 22), 22);
		Film actual = rep.update(film);
		assertTrue(actual != null);
	}

}
