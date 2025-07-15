package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;


import java.util.Set;

@Value
@Builder(toBuilder = true)
public class Review {
    Long reviewId;
    @NotBlank
    String content;
    @NonNull
    Long userId;
    @NonNull
    Long filmId;
    @NonNull
    Boolean isPositive;
    int useful;
    Set<Long> likes;
    Set<Long> dislikes;

}
