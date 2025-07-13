package ru.yandex.practicum.filmorate.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dto.FilmAnsDto;

import java.util.Collections;
import java.util.List;

@Repository
public class FilmAnsDtoRepository extends BaseRepository<FilmAnsDto> {

    private static final String SELECT =
            "SELECT " +
                    "f.film_id, " +
                    "f.name, " +
                    "f.description, " +
                    "f.release_date, " +
                    "f.duration, " +
                    "m.id as mpa_id, " +
                    "m.name as mpa_name, " +
                    "ARRAY_AGG(DISTINCT(g.genre_id || ':' || g.name)) AS genres, " +
                    "ARRAY_AGG(DISTINCT(d.director_id || ':' || d.name)) AS directors " +
                    "FROM film f " +
                    "LEFT JOIN mpa m ON f.mpa_id = m.id " +
                    "LEFT JOIN film_genre fg ON f.film_id = fg.film_id " +
                    "LEFT JOIN genre g ON fg.genre_id = g.genre_id " +
                    "LEFT JOIN film_director fd ON f.film_id = fd.film_id " +
                    "LEFT JOIN director d ON fd.director_id = d.director_id";
    private static final String WHERE =
            "WHERE";
    private static final String CONDITION_BY_TITLE = "UPPER(f.name) LIKE UPPER(?)";
    private static final String OR = "OR";
    private static final String CONDITION_BY_DIRECTOR = "UPPER(d.name) LIKE UPPER(?)";
    private static final String CONDITION_BY_DIRECTOR_ID = "d.director_id = ?";
    private static final String GROUP = "GROUP BY f.film_id, f.name, f.description, f.release_date, f.duration, m.id, m.name";
    private static final String ORDER_BY_LIKES = "ORDER BY (SELECT COUNT(*) FROM film_person WHERE film_id = f.film_id) DESC";
    private static final String ORDER_BY_YEAR = "ORDER BY f.release_date";

    public FilmAnsDtoRepository(JdbcTemplate jdbc, RowMapper<FilmAnsDto> mapper) {
        super(jdbc, mapper);
    }

    public List<FilmAnsDto> getFilmsByDirectorId(Long directorId, String sortBy) {
        String query = String.join(" ", SELECT, WHERE, CONDITION_BY_DIRECTOR_ID, GROUP);

        if (sortBy == null) {
            return findMany(query, directorId);
        }

        if (sortBy.equals("year")) {
            query = String.join(" ", query, ORDER_BY_YEAR);
            return findMany(query, directorId);
        } else if (sortBy.equals("likes")) {
            query = String.join(" ", query, ORDER_BY_LIKES);
            return findMany(query, directorId);
        }

        return findMany(query, directorId);
    }

    public List<FilmAnsDto> searchFilms(String subString, String by) {
        String query = String.join(" ", SELECT, WHERE);

        switch (by) {
            case "title":
                query = String.join(" ", query, CONDITION_BY_TITLE, GROUP, ORDER_BY_LIKES);
                return findMany(query, "%" + subString + "%");
            case "director":
                query = String.join(" ", query, CONDITION_BY_DIRECTOR, GROUP, ORDER_BY_LIKES);
                return findMany(query, "%" + subString + "%");
            case "title,director":
            case "director,title":
                query = String.join(" ", query, CONDITION_BY_TITLE, OR, CONDITION_BY_DIRECTOR, GROUP, ORDER_BY_LIKES);
                return findMany(query, "%" + subString + "%", "%" + subString + "%");
            default:
                return Collections.emptyList();
        }
    }
}
