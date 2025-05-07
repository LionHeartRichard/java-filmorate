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
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.repositories.rowmapper.MpaRowMapper;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({MpaRepositories.class, MpaRowMapper.class})
public class MpaRepositoriesAppTest {
	private final MpaRepositories rep;

	private Mpa mpa;

	private static int count = 1;

	@BeforeEach
	void setUp() {
		while (count++ <= 3) {
			mpa = Mpa.builder()
					.mpaId(null)
					.filmId((long)count)
					.name("Mpa-Name")
					.build();
			Optional<Long> actual = rep.add(mpa);
			
			assertTrue(actual.isPresent());
		}
	}

	@Test
	void removeGenreWhenValidDataThrenReturnNumbersRows() {
		Optional<Integer> actual = rep.remove(3L);

		assertTrue(actual.isPresent());
	}
}
