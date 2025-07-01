package ru.yandex.practicum.filmorate.repositories;

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
import ru.yandex.practicum.filmorate.repositories.rowmapper.GenreRowMapper;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({GenreRepository.class, GenreRowMapper.class})
public class GenreRepositoryAppTest {
	private final GenreRepository rep;

	private Genre genre;
	private static int postfix = 4;

	@BeforeEach
	void setUp() {
		genre = new Genre();
		genre.setName("new-GENRE_naeme:" + postfix);
		++postfix;
		rep.save(genre);
	}

	@Test
	void findAllTest() {
		List<Genre> actual = rep.findAll();
		assertTrue(!actual.isEmpty());
	}

	@Test
	void findByIdTest() {
		Optional<Genre> actual = rep.findById(genre.getId());
		assertTrue(actual.isPresent());
	}

	@Test
	void findByNameTest() {
		String name = "Ко%";
		List<Genre> actual = rep.findByName(name);
		assertTrue(!actual.isEmpty());
	}

	@Test
	void findByFullNameTest() {
		List<Genre> actual = rep.findByFullName(genre.getName());
		assertTrue(!actual.isEmpty());
	}

	@Test
	void updateTest() {
		genre.setName("Fantasy-Update");
		rep.update(genre);
	}
}
