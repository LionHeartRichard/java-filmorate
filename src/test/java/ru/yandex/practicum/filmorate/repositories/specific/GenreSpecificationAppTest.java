package ru.yandex.practicum.filmorate.repositories.specific;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.repositories.rowmapper.GenreRowMapper;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({GenreSpecification.class, GenreRowMapper.class})
public class GenreSpecificationAppTest {

	private final GenreSpecification genreSpecification;

	@Test
	void getTableData() {
		Set<String> genre = genreSpecification.specified(0, new HashSet<>());

		assertTrue(genre.isEmpty());
	}
}
