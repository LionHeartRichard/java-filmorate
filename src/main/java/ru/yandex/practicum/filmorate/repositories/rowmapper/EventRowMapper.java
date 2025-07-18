package ru.yandex.practicum.filmorate.repositories.rowmapper;

//import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Operation;

@Repository
public class EventRowMapper implements RowMapper<Event> {

	@Override
	public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
		// Timestamp timestamp = rs.getTimestamp("timestamp_field");
		EventType eventType = EventType.valueOf(rs.getString("event_type"));
		Operation operation = Operation.valueOf(rs.getString("operation"));
		Event row = new Event(rs.getLong("timestamp_field"), rs.getLong("person_id"), eventType, operation,
				rs.getLong("event_id"), rs.getLong("entity_id"));
		return row;
	}

}
