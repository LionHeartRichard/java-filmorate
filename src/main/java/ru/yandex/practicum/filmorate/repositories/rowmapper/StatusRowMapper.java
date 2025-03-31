package ru.yandex.practicum.filmorate.repositories.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.impl.Status;

@Repository
public class StatusRowMapper implements RowMapper<Status> {

	@Override
	public Status mapRow(ResultSet rs, int rowNum) throws SQLException {
		Status status = Status.builder().name(rs.getString("name")).build();
		return status;
	}

}
