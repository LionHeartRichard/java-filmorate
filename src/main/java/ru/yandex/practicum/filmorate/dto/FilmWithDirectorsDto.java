package ru.yandex.practicum.filmorate.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.filmorate.model.Director;

import java.time.LocalDate;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class FilmWithDirectorsDto {
    Long id;
    String name;
    String description;
    LocalDate releaseDate;
    Integer duration;
    List<Director> directors;
}
