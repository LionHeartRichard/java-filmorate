package ru.yandex.practicum.filmorate.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Operation;

@Repository
public class EventRepository extends BaseRepository<Event> {

	public EventRepository(JdbcTemplate jdbc, RowMapper<Event> mapper) {
		super(jdbc, mapper);
	}

	// TODO
	public boolean isPresentUser(Long userId) {
		String sql = String.format("SELECT COUNT(*) > 0 AS is_present FROM person WHERE person_id = %d", userId);
		boolean[] ans = {true};
		return jdbc.query(sql, (rs) -> {
			while (rs.next()) {
				ans[0] = rs.getBoolean("is_present");
			}
			return ans[0];
		});
	}

	public boolean isPresentEvent(Long id) {
		String sql = String.format("SELECT COUNT(*) > 0 AS is_present FROM event WHERE event_id = %d", id);
		boolean[] ans = {true};
		return jdbc.query(sql, (rs) -> {
			while (rs.next()) {
				ans[0] = rs.getBoolean("is_present");
			}
			return ans[0];
		});
	}

	public List<Event> findAll() {
		String sql = "SELECT * FROM event";
		return findMany(sql);
	}

	public Optional<Event> findById(Long id) {
		String sql = "SELECT * FROM event WHERE event_id = ?";
		return findOne(sql, id);
	}

	public List<Event> findByEntityId(Long entityId) {
		String sql = "SELECT * FROM event WHERE entity_id = ?";
		return findMany(sql, entityId);
	}

	public List<Event> findEventByUserId(Long userId) {
		String sql = "SELECT * FROM event WHERE person_id = ?";
		return findMany(sql, userId);
	}

	public List<Event> findEventByOperation(Operation operation) {
		String sql = "SELECT * FROM event WHERE operation = ?";
		return findMany(sql, operation);
	}

	public List<Event> findEventByEventType(EventType eventType) {
		String sql = "SELECT * FROM event WHERE event_type = ?";
		return findMany(sql, eventType);
	}

	public Event save(Event event) {
		String sql = "INSERT INTO event(timestamp_field, person_id, event_type, operation, entity_id) VALUES (?, ?, ?, ?, ?)";
		Long id = insert(sql, event.getTimestamp(), event.getUserId(), event.getEventType().toString(),
				event.getOperation().toString(), event.getEntityId());
		event.setEventId(id);
		return event;
	}
}
