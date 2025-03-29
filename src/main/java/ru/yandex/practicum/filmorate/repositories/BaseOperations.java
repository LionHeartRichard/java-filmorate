package ru.yandex.practicum.filmorate.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BaseOperations<T> {

	private final JdbcTemplate jdbc;

	public Optional<Long> add(String queryInsert, Object[] params) {
		log.trace("SQL: ", queryInsert);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(queryInsert, Statement.RETURN_GENERATED_KEYS);
			int[] idx = {1};
			for (Object param : params)
				ps.setObject(idx[0]++, param);
			return ps;
		}, keyHolder);
		Long id = keyHolder.getKeyAs(Long.class);
		log.trace("id={}", id);
		return Optional.ofNullable(id);
	}

	public Optional<Long> add(String query, PreparedStatementSetter pss) {
		log.trace("SQL: {}", query);
		Integer rowAdd = jdbc.update(query, pss);
		rowAdd = rowAdd == 0 ? null : rowAdd;
		log.trace("Row add: {}", rowAdd);
		return Optional.ofNullable(rowAdd.longValue());
	}

	public Optional<Integer> update(String query, PreparedStatementSetter pss) {
		log.trace("SQL: {}", query);
		Integer rowsUpdated = jdbc.update(query, pss);
		rowsUpdated = rowsUpdated == 0 ? null : rowsUpdated;
		log.trace("End update operation");
		return Optional.ofNullable(rowsUpdated);
	}

	public Optional<Integer> remove(Long id, String nameTable, String primaryKeyNameColumn) {
		String query = String.format("DELETE FROM %s WHERE %s=?", nameTable, primaryKeyNameColumn);
		log.trace("SQL: {}; PK:{} = {}, nameTable={}", query, primaryKeyNameColumn, id, nameTable);
		Integer rowsDeleted = jdbc.update(query, id);
		rowsDeleted = rowsDeleted == 0 ? null : rowsDeleted;
		log.trace("End operation remove");
		return Optional.ofNullable(rowsDeleted);
	}

}
