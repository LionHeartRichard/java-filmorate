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
import ru.yandex.practicum.filmorate.repositories.rowmapper.RatingRowMapper;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({RatingSpecification.class, RatingRowMapper.class})
public class RatingSpecificationAppTest {

	private final RatingSpecification ratingSpecification;

	@Test
	void getTableData() {
		Set<String> rating = ratingSpecification.specified(0, new HashSet<>());

		assertTrue(!rating.isEmpty());
	}

}
