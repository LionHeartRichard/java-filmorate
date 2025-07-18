package ru.yandex.practicum.filmorate.repositories;

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
import ru.yandex.practicum.filmorate.repositories.rowmapper.FilmRowMapper;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({FilmRepository.class, FilmRowMapper.class})
public class FilmRepositoryAppTest {
	private final FilmRepository rep;

	private Film film;
	private static int postfix = 0;

	@BeforeEach
	void setUp() {
		++postfix;
		film = new Film();
		film.setId(null);
		film.setName("name Set-Up" + postfix);
		film.setDescription("description set up" + postfix);
		film.setReleaseDate(LocalDate.now());
		film.setDuration(120);
		film.setMpaId(1L);
		rep.save(film);
	}

	@Test
	void findAllTest() {
		List<Film> actual = rep.findAll();
		assertTrue(!actual.isEmpty());
	}

	@Test
	void findById() {
		Optional<Film> actual = rep.findById(film.getId());
		assertTrue(actual.isPresent());
	}

	@Test
	void findByFullNameTest() {
		List<Film> actual = rep.findByFullName(film.getName());
		assertTrue(!actual.isEmpty());
	}

	@Test
	void deleteFilmByIdTest() {
		assertTrue(rep.deleteFilmById(film.getId()));
	}

}
