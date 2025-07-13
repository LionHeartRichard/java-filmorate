package ru.yandex.practicum.filmorate.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class FilmRecommendationResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
}