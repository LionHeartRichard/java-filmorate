package ru.yandex.practicum.filmorate.repositories.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.expression.Operation;
import org.springframework.jdbc.core.RowMapper;

import ch.qos.logback.core.spi.ConfigurationEvent.EventType;
import ru.yandex.practicum.filmorate.model.Event;

public class EventRowMapper implements RowMapper<Event> {

	@Override
	public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
		Event row = new Event(
				rs.getTimestamp("timest"), 
				rs.getLong("person_id"), 
				EventType.valueOf(rs.getString("event_type")), 
				Operation.valueOf(rs.getString("operation")),
				rs.getLong("event_id"),
				rs.getLong("entity_id");
		return row;
	}

}
