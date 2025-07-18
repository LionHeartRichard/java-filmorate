package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.ReviewService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public Review createReview(@Valid @RequestBody Review review) {
        log.info("POST /reviews");
        return reviewService.createReview(review);
    }

    @PutMapping
    public Review updateReview(@Valid @RequestBody Review review) {
        log.info("PUT /reviews");
        return reviewService.updateReview(review);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReviewById(@PathVariable Long reviewId) {
        log.info("DELETE /reviews/{}", reviewId);
        reviewService.removeReviewById(reviewId);
    }

    @GetMapping("/{reviewId}")
    public Review getReviewById(@PathVariable Long reviewId) {
        log.info("GET /reviews/{}", reviewId);
        return reviewService.getReviewById(reviewId);
    }

    @GetMapping
    public List<Review> getAllReviews(@RequestParam(required = false) Long filmId,
                                      @RequestParam(defaultValue = "10") Long count) {
        log.info("GET /reviews?filmId={}&count={}", filmId, count);
        return reviewService.getReviews(filmId, count);
    }

    @PutMapping("/{reviewId}/like/{userId}")
    public void addLike(@PathVariable Long reviewId,
                        @PathVariable Long userId) {
        log.info("PUT /reviews/{}/like/{}", reviewId, userId);
        reviewService.addLike(reviewId, userId);
    }

    @PutMapping("/{reviewId}/dislike/{userId}")
    public void addDislike(@PathVariable Long reviewId,
                           @PathVariable Long userId) {
        log.info("PUT /reviews/{}/dislike/{}", reviewId, userId);
        reviewService.addDislike(reviewId, userId);
    }

    @DeleteMapping("/{reviewId}/like/{userId}")
    public void removeLike(@PathVariable Long reviewId,
                           @PathVariable Long userId) {
        log.info("DELETE /reviews/{}/like/{}", reviewId, userId);
        reviewService.deleteLikeOrDislike(reviewId, userId);
    }

    @DeleteMapping("/{reviewId}/dislike/{userId}")
    public void removeDislike(@PathVariable Long reviewId,
                              @PathVariable Long userId) {
        log.info("DELETE /reviews/{}/dislike/{}", reviewId, userId);
        reviewService.deleteLikeOrDislike(reviewId, userId);
    }
}