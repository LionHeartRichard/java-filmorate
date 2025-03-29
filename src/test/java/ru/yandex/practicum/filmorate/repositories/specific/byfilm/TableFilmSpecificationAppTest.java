package ru.yandex.practicum.filmorate.repositories.specific.byfilm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

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
@Import({TableFilmSpecification.class, FilmRowMapper.class})
public class TableFilmSpecificationAppTest {

	private final TableFilmSpecification tableFilm;

	@Test
	void getTableData() {
		List<Film> films = tableFilm.specified(0, new ArrayList<>());

		assertTrue(!films.isEmpty());
	}
}
