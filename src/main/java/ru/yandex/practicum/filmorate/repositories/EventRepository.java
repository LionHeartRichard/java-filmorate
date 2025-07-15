package ru.yandex.practicum.filmorate.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.Event;

@Repository
public class EventRepository extends BaseRepository<Event> {

	// GET /users/{id}/feed

	public EventRepository(JdbcTemplate jdbc, RowMapper<Event> mapper) {
		super(jdbc, mapper);
	}
}
