package ru.yandex.practicum.filmorate.repositories.specific.byuser;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.impl.User;
import ru.yandex.practicum.filmorate.repositories.rowmapper.UserRowMapper;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({TableUserSpecification.class, UserRowMapper.class})
public class TableUserSpecificationAppTest {

	private final TableUserSpecification tableSpec;

	@Test
	void getTableData() {
		List<User> users = tableSpec.specified(0, new ArrayList<>());

		assertTrue(!users.isEmpty());
	}
}
