package ru.yandex.practicum.filmorate.repositories.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dto.FilmAnsDto;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FilmAnsDtoRowMapper implements RowMapper<FilmAnsDto> {

    public FilmAnsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        FilmAnsDto row = new FilmAnsDto();
        row.setId(rs.getLong("film_id"));
        row.setName(rs.getString("name"));
        row.setDescription(rs.getString("description"));
        row.setDuration(rs.getInt("duration"));
        row.setReleaseDate(rs.getDate("release_date").toLocalDate());

        Mpa mpa = new Mpa();
        mpa.setId(rs.getLong("mpa_id"));
        mpa.setName(rs.getString("mpa_name"));
        row.setMpa(mpa);

        String genresString = rs.getString("genres");
        String cleanGenresString = genresString.replaceAll("\\[|\\]", "");
        List<Genre> genres = new ArrayList<>();
        if (genresString != null) {
            String[] genrePairs = cleanGenresString.split(",");
            for (String pair : genrePairs) {
                if (pair.equals("null")) continue;
                String[] parts = pair.split(":");
                Genre genre = new Genre();
                genre.setId(Long.parseLong(parts[0].trim()));
                genre.setName(parts[1]);
                genres.add(genre);
            }
        }
        row.setGenres(genres);

        String directorsString = rs.getString("directors");
        String cleanDirectorsString = directorsString.replaceAll("\\[|\\]", "");
        List<Director> directors = new ArrayList<>();
        if (directorsString != null) {
            String[] directorPairs = cleanDirectorsString.split(",");
            for (String pair : directorPairs) {
                if (pair.equals("null")) continue;
                String[] parts = pair.split(":");
                Director director = new Director();
                director.setId(Long.parseLong(parts[0].trim()));
                director.setName(parts[1]);
                directors.add(director);
            }
        }
        row.setDirectors(directors);

        return row;
    }
}
