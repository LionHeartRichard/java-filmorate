package ru.yandex.practicum.filmorate.repositories.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Director;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class DirectorRowMapper implements RowMapper<Director> {

    public Director mapRow(ResultSet rs, int rowNum) throws SQLException {
        Director row = new Director(rs.getLong("director_id"), rs.getString("name"));
        return row;
    }
}
