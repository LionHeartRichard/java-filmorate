package ru.yandex.practicum.filmorate.repositories;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.repositories.rowmapper.DirectorRowMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({DirectorRepository.class, DirectorRowMapper.class})
public class DirectorRepositoryAppTest {

    @Autowired
    private DirectorRepository repository;

    @BeforeEach
    void setUp() {
        Director director = new Director();
        director.setName("name");
        repository.save(director);
    }

    @Test
    void readTest() {
        List<Director> directors = repository.findAll();
        assertTrue(!directors.isEmpty());
    }

    @Test
    void findByIdTest() {
        List<Director> directors = repository.findAll();
        Director director = directors.getFirst();

        assertEquals("name", director.getName());
    }

    @Test
    void updateTest() {
        List<Director> directors = repository.findAll();
        Director director = directors.getFirst();

        director.setName("updated_name");
        repository.update(director);

        Director updatedDirector = repository.findById(director.getId()).get();

        assertEquals("updated_name", updatedDirector.getName());
    }
}
