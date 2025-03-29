package ru.yandex.practicum.filmorate.repositories.specific.byfilm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.impl.Film;
import ru.yandex.practicum.filmorate.repositories.rowmapper.FilmRowMapper;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({FilmIdSpecification.class, FilmRowMapper.class})
public class FilmIdSpecificationAppTest {
	private final FilmIdSpecification filmSpecification;

	@Test
	void getFilmById() {
		Optional<Film> filmOpt = filmSpecification.specified(1L, Optional.empty());

		assertTrue(filmOpt.isPresent());
	}
}
