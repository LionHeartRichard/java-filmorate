package ru.yandex.practicum.filmorate.repositories;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BaseOperations<T> {

	private final JdbcTemplate jdbc;

	public Optional<Long> add(T t, String nameTable, String primaryKey) {
		log.trace("Object: {}, Name-table: {}, PK: {}", t.toString(), nameTable, primaryKey);
		Object[] params = getParam(t);
		String values = getValues(params.length);
		String query = String.format("INSERT INTO %s VALUES (%s) returning %s", nameTable, values, primaryKey);
		log.trace("SQL: ", query);
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			int[] idx = {1};
			for (Object p : params)
				ps.setObject(idx[0]++, p);
			return ps;
		}, keyHolder);
		Long id = keyHolder.getKeyAs(Long.class);
		log.trace("id={}", id);
		return Optional.ofNullable(id);
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

	@SneakyThrows
	private Object[] getParam(T t) {
		log.trace("Reflection field for Object: {}", t.toString());
		Field[] fields = t.getClass().getDeclaredFields();
		Object[] params = new Object[fields.length];
		int[] idx = {0};
		for (Field f : fields) {
			f.setAccessible(true);
			params[idx[0]++] = f.get(t);
		}
		return params;
	}

	private String getValues(int len) {
		log.trace("Length for formation SQL-VALUES: {}", len);
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < len - 1; ++i) {
			builder.append("?,");
		}
		builder.append("?");
		return builder.toString();
	}

	public Optional<Long> add(String query, PreparedStatementSetter pss) {
		log.trace("SQL: ", query);
		Integer rowAdd = jdbc.update(query, pss);
		rowAdd = rowAdd == 0 ? null : rowAdd;
		log.trace("Row add: {}", rowAdd);
		return Optional.ofNullable(rowAdd.longValue());
	}
}
