package ru.yandex.practicum.filmorate.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dto.FilmAnsDto;
import ru.yandex.practicum.filmorate.repositories.rowmapper.FilmExtendedRowMapper;

import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FilmExtendedRepository {
    protected final JdbcTemplate jdbc;
    protected final FilmExtendedRowMapper mapper;

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
                    "LEFT JOIN director d ON fd.director_id = d.director_id ";
    private static final String WHERE =
            "WHERE ";
    private static final String CONDITION_BY_TITLE = "UPPER(f.name) LIKE UPPER(?) ";
    private static final String OR = "OR ";
    private static final String CONDITION_BY_DIRECTOR = "UPPER(d.name) LIKE UPPER(?) ";
    private static final String CONDITION_BY_DIRECTOR_ID = "d.director_id = ? ";
    private static final String GROUP = "GROUP BY f.film_id, f.name, f.description, f.release_date, f.duration, m.id, m.name ";
    private static final String ORDER_BY_LIKES = "ORDER BY (SELECT COUNT(*) FROM film_person WHERE film_id = f.film_id) DESC ";
    private static final String ORDER_BY_YEAR = "ORDER BY f.release_date";

    public List<FilmAnsDto> getFilmsByDirectorId(Long directorId, String sortBy) {
        String query = "";

        if (sortBy == null || sortBy.isEmpty()) {
            query = SELECT + WHERE + CONDITION_BY_DIRECTOR_ID + GROUP;
            return jdbc.query(query, mapper, directorId);
        }

        if (sortBy.equals("year")) {
            query = SELECT + WHERE + CONDITION_BY_DIRECTOR_ID + GROUP + ORDER_BY_YEAR;
            return jdbc.query(query, mapper, directorId);
        } else if (sortBy.equals("likes")) {
            query = SELECT + WHERE + CONDITION_BY_DIRECTOR_ID + GROUP + ORDER_BY_LIKES;
            return jdbc.query(query, mapper, directorId);
        } else {
            query = SELECT + WHERE + CONDITION_BY_DIRECTOR_ID + GROUP;
            return jdbc.query(query, mapper, directorId);
        }
    }

    public List<FilmAnsDto> searchFilms(String subString, String by) {
        String query = "";

        switch (by) {
            case "title":
                query = SELECT + WHERE + CONDITION_BY_TITLE + GROUP + ORDER_BY_LIKES;
                return jdbc.query(query, mapper, "%" + subString + "%");
            case "director":
                query = SELECT + WHERE + CONDITION_BY_DIRECTOR + GROUP + ORDER_BY_LIKES;
                return jdbc.query(query, mapper, "%" + subString + "%");
            case "title,director":
            case "director,title":
                query = SELECT + WHERE + CONDITION_BY_TITLE + OR + CONDITION_BY_DIRECTOR + GROUP + ORDER_BY_LIKES;
                return jdbc.query(query, mapper, "%" + subString + "%", "%" + subString + "%");
            default:
                return Collections.emptyList();
        }
    }
}
