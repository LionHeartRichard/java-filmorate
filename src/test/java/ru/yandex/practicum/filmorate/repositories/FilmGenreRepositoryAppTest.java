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
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.repositories.rowmapper.FilmGenreRowMapper;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({FilmGenreRepository.class, FilmGenreRowMapper.class})
public class FilmGenreRepositoryAppTest {

	private final FilmGenreRepository rep;
	private FilmGenre filmGenre;
	private static Long filmId = 0L;
	private static Long genreId = 4L;

	@BeforeEach
	void setUp() {
		filmId = filmId < 3 ? filmId + 1 : filmId - 1;
		filmGenre = new FilmGenre();
		genreId = genreId < 3 ? genreId + 1 : genreId - 1;
		filmGenre.setFilmId(filmId);
		filmGenre.setGenreId(filmId);
		rep.save(filmGenre);
	}

}
