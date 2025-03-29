package ru.yandex.practicum.filmorate.repositories.specific.bylikefilm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import lombok.RequiredArgsConstructor;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({GetCountLikeByFilmSpecification.class})
public class GetCountLikeByFilmSpecificationAppTest {

	private final GetCountLikeByFilmSpecification getLiksSpec;

	@Test
	void getLikes() {
		Integer count = 0;
		count = getLiksSpec.specified(1L, count);

		assertTrue(count > 0);
	}
}
