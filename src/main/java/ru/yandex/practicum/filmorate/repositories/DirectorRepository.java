package ru.yandex.practicum.filmorate.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;
import java.util.Optional;

@Repository
public class DirectorRepository extends BaseRepository<Director> {

    private static final String FIND_ALL_QUERY =
        "SELECT d.* FROM director d";

    private static final String FIND_BY_ID =
        "SELECT d.* FROM director d WHERE director_id = ?";

    private static final String FIND_DIRECTORS_BY_FILM_ID =
            "SELECT d.* FROM director d " +
            "JOIN film_director fd ON d.director_id = fd.director_id " +
            "WHERE fd.film_id = ?";

    private static final String INSERT_QUERY =
        "INSERT INTO director(name) VALUES (?)";
    private static final String UPDATE_QUERY =
        "UPDATE director SET name = ? WHERE director_id = ?";

    private static final String DELETE_DIRECTOR_BY_ID =
        "DELETE FROM director WHERE director_id = ?";

    public DirectorRepository(JdbcTemplate jdbc, RowMapper<Director> mapper) {
        super(jdbc, mapper);
    }

    public List<Director> findAll() {
        return findMany(FIND_ALL_QUERY);
    }

    public Optional<Director> findById(Long id) {
        return findOne(FIND_BY_ID, id);
    }

    public Long save(Director director) {
        return insert(INSERT_QUERY, director.getName());
    }

    public void update(Director director) {
        update(UPDATE_QUERY, director.getName(), director.getId());
    }

    public boolean deleteById(Long id) {
        return delete(DELETE_DIRECTOR_BY_ID, id);
    }

    public List<Director> findByFilmId(Long id) {
        return findMany(FIND_DIRECTORS_BY_FILM_ID, id);
    }


}
