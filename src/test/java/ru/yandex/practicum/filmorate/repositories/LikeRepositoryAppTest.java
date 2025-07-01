package ru.yandex.practicum.filmorate.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.repositories.rowmapper.LikeRowMapper;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({LikeRepository.class, LikeRowMapper.class})
public class LikeRepositoryAppTest {

	private final LikeRepository rep;
	private Like like;
	private static Long filmId = 0L;
	private static Long userId = 0L;

	@BeforeEach
	void setUp() {
		like = new Like();
		filmId = filmId < 3 ? filmId + 1 : filmId - 1;
		like.setFilmId(filmId);
		userId = userId < 3 ? userId + 1 : userId - 1;
		like.setUserId(userId);
		rep.save(like);
	}

}
