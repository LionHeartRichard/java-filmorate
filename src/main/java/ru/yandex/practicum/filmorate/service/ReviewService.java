package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.repositories.FilmRepository;
import ru.yandex.practicum.filmorate.repositories.ReviewLikeRepository;
import ru.yandex.practicum.filmorate.repositories.ReviewRepository;
import ru.yandex.practicum.filmorate.repositories.UserRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository likeRepository;
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;

    @Transactional
    public Review createReview(Review review) {
        log.info("Creating review for film {} by user {}", review.getFilmId(), review.getUserId());
        validateFilmId(review.getFilmId());
        validateUserId(review.getUserId());
        return reviewRepository.save(review);
    }

    @Transactional
    public void updateReview(Review review) {
        log.info("Updating review {}", review.getReviewId());
        validateReviewId(review.getReviewId());
        validateFilmId(review.getFilmId());
        validateUserId(review.getUserId());
        reviewRepository.save(review);
    }

    @Transactional
    public void removeReviewById(Long id) {
        log.info("Deleting review {}", id);
        validateReviewId(id);
        reviewRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Review getReviewById(Long id) {
        log.debug("Getting review {}", id);
        validateReviewId(id);
        return reviewRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public List<Review> getReviews(Long filmId, Long count) {
        log.debug("Getting reviews: filmId={}, count={}", filmId, count);
        if (filmId == null) {
            return reviewRepository.findAllReviews(count);
        } else {
            return reviewRepository.findReviewsByFilmId(filmId, count);
        }
    }

    @Transactional
    public void addLike(Long reviewId, Long userId) {
        log.info("Adding like: review={}, user={}", reviewId, userId);
        validateReviewId(reviewId);
        validateUserId(userId);
        likeRepository.addOrUpdate(reviewId, userId, true);
        recalcUseful(reviewId);
    }

    @Transactional
    public void addDislike(Long reviewId, Long userId) {
        log.info("Adding dislike: review={}, user={}", reviewId, userId);
        validateReviewId(reviewId);
        validateUserId(userId);
        likeRepository.addOrUpdate(reviewId, userId, false);
        recalcUseful(reviewId);
    }

    @Transactional
    public void deleteLikeOrDislike(Long reviewId, Long userId) {
        log.info("Removing like/dislike: review={}, user={}", reviewId, userId);
        validateReviewId(reviewId);
        validateUserId(userId);
        likeRepository.remove(reviewId, userId);
        recalcUseful(reviewId);
    }

    @Transactional
    private void recalcUseful(Long reviewId) {
        int likes    = likeRepository.countLikes(reviewId);
        int dislikes = likeRepository.countDislikes(reviewId);
        int useful   = likes - dislikes;
        log.debug("Recalculated useful for review {}: {}", reviewId, useful);
        reviewRepository.updateUseful(reviewId, useful);
    }

    // валидации
    @Transactional(readOnly = true)
    private void validateReviewId(Long id) {
        if (reviewRepository.findById(id).isEmpty()) {
            String msg = "Review with this ID not found: " + id;
            log.warn(msg);
            throw new NotFoundException(msg);
        }
    }

    @Transactional(readOnly = true)
    private void validateFilmId(Long id) {
        if (filmRepository.findById(id).isEmpty()) {
            String msg = "Film with this ID not found: " + id;
            log.warn(msg);
            throw new NotFoundException(msg);
        }
    }

    @Transactional(readOnly = true)
    private void validateUserId(Long userId) {
        if (userRepository.findById(userId).isEmpty()) {
            String msg = "User with this ID not found: " + userId;
            log.warn(msg);
            throw new NotFoundException(msg);
        }
    }
}