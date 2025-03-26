package ru.yandex.practicum.filmorate.repositories.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ru.yandex.practicum.filmorate.model.impl.Status;

public class StatusRowMapper implements RowMapper<Status> {

	@Override
	public Status mapRow(ResultSet rs, int rowNum) throws SQLException {
		Status status = new Status(rs.getString("name"));
		return status;
	}

}
