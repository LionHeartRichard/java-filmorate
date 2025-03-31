package ru.yandex.practicum.filmorate.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public abstract class BaseRepo<T> {

	private final String SELECT_TABELE = "SELECT * FROM %s LIMIT %d OFFSET %d";

	protected final JdbcTemplate jdbc;
	protected final RowMapper<T> rowMapper;
	protected final String nameTable;
	protected final String namePk;
	protected Integer limit = 200;

	public Optional<Long> addByGeneratedKey(String queryInsert, Object[] fields) {
		log.trace("SQL: ", queryInsert);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(queryInsert, Statement.RETURN_GENERATED_KEYS);
			int[] idx = {1};
			for (Object field : fields)
				ps.setObject(idx[0]++, field);
			return ps;
		}, keyHolder);
		Long id = keyHolder.getKeyAs(Long.class);
		log.trace("id={}", id);
		return Optional.ofNullable(id);
	}

	public Optional<Long> add(String query, PreparedStatementSetter pss) {
		log.trace("SQL: {}", query);
		Long rowAdd = (long) jdbc.update(query, pss);
		rowAdd = rowAdd == 0 ? null : rowAdd;
		log.trace("Row add: {}", rowAdd);
		return Optional.ofNullable(rowAdd);
	}

	public Optional<Integer> update(String query, PreparedStatementSetter pss) {
		log.trace("SQL: {}", query);
		Integer rowsUpdated = jdbc.update(query, pss);
		rowsUpdated = rowsUpdated == 0 ? null : rowsUpdated;
		log.trace("End update/remove operation");
		return Optional.ofNullable(rowsUpdated);
	}

	public Optional<Integer> remove(Long id) {
		String query = String.format("DELETE FROM %s WHERE %s=?", nameTable, namePk);
		log.trace("SQL: {}; PK:{} = {}, nameTable={}", query, namePk, id, nameTable);
		Integer rowsDeleted = jdbc.update(query, id);
		rowsDeleted = rowsDeleted == 0 ? null : rowsDeleted;
		log.trace("End operation remove");
		return Optional.ofNullable(rowsDeleted);
	}

	public Collection<T> getTable(Integer offset) {
		String query = String.format(SELECT_TABELE, nameTable, limit, offset);
		log.trace("SQL: {}; nameTable = {}; LIMIT = {}, OFFSET = {}", query, namePk, nameTable, limit, offset);
		return jdbc.queryForStream(query, rowMapper).toList();
	}

	public Stream<T> getStream(Integer offset) {
		String query = String.format(SELECT_TABELE, nameTable, limit, offset);
		log.trace("SQL: {}; nameTable = {}; LIMIT = {}, OFFSET = {}", query, namePk, nameTable, limit, offset);
		return jdbc.queryForStream(query, rowMapper);
	}

}
