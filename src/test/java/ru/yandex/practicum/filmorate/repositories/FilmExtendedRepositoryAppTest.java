package ru.yandex.practicum.filmorate.repositories;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import ru.yandex.practicum.filmorate.dto.FilmAnsDto;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ComponentScan("ru.yandex.practicum.filmorate")
public class FilmExtendedRepositoryAppTest {

    @Autowired
    private final FilmRepository filmRepository;

    @Autowired
    private final FilmExtendedRepository filmExtendedRepository;

    @Autowired
    private final DirectorRepository directorRepository;

    @BeforeEach
    void setUp() {
        Director director = new Director();
        director.setName("name");
        Long directorId = directorRepository.save(director);
        director.setId(directorId);

        Film film = new Film();
        film.setId(null);
        film.setName("name");
        film.setDescription("description");
        film.setReleaseDate(LocalDate.now());
        film.setDuration(120);
        film.setMpaId(1L);
        filmRepository.save(film);

        List<Director> directors = new ArrayList<>();
        directors.add(director);
        filmRepository.saveFilmDirectors(film, directors);
    }

    @Test
    void getFilmsByDirectorIdTest() {
        List<Director> directors = directorRepository.findAll();

        List<FilmAnsDto> result = filmExtendedRepository.getFilmsByDirectorId(directors.getFirst().getId(), "year");
        assertTrue(!result.isEmpty());
    }

    @Test
    void searchFilmsTest() {
        List<FilmAnsDto> result = filmExtendedRepository.searchFilms("am", "title");
        assertTrue(!result.isEmpty());
    }

}
