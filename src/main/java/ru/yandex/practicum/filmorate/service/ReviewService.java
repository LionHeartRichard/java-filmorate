package ru.yandex.practicum.filmorate.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Operation;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.repositories.EventRepository;
import ru.yandex.practicum.filmorate.repositories.FilmRepository;
import ru.yandex.practicum.filmorate.repositories.ReviewLikeRepository;
import ru.yandex.practicum.filmorate.repositories.ReviewRepository;
import ru.yandex.practicum.filmorate.repositories.UserRepository;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

	ReviewRepository reviewRepository;
	ReviewLikeRepository likeRepository;
	FilmRepository filmRepository;
	UserRepository userRepository;
	EventRepository eventRepository;

	@Transactional
	public Review createReview(Review review) {
		log.info("Creating review for film {} by user {}", review.getFilmId(), review.getUserId());
		validateFilmId(review.getFilmId());
		validateUserId(review.getUserId());

		Review ans = reviewRepository.save(review);

		Event event = new Event();
		event.setTimestamp(System.currentTimeMillis());
		event.setUserId(ans.getUserId());
		event.setEventType(EventType.REVIEW);
		event.setOperation(Operation.ADD);
		event.setEntityId(ans.getReviewId());

		eventRepository.save(event);

		return ans;
	}

	@Transactional
	public void updateReview(Review review) {
		log.info("Updating review {}", review.getReviewId());
		validateReviewId(review.getReviewId());
		validateFilmId(review.getFilmId());
		validateUserId(review.getUserId());
		Review ans = reviewRepository.save(review);

		Event event = new Event();
		event.setTimestamp(System.currentTimeMillis());
		event.setUserId(ans.getUserId());
		event.setEventType(EventType.REVIEW);
		event.setOperation(Operation.UPDATE);
		event.setEntityId(ans.getReviewId());

		eventRepository.save(event);
	}

	@Transactional
	public void removeReviewById(Long id) {
		log.info("Deleting review {}", id);
		validateReviewId(id);

		Review ans = reviewRepository.findById(id).get();

		reviewRepository.deleteById(id);

		Event event = new Event();
		event.setTimestamp(System.currentTimeMillis());
		event.setUserId(ans.getUserId());
		event.setEventType(EventType.REVIEW);
		event.setOperation(Operation.REMOVE);
		event.setEntityId(ans.getReviewId());

		eventRepository.save(event);
	}

	@Transactional(readOnly = true)
	public Review getReviewById(Long id) {
		log.debug("Getting review {}", id);
		validateReviewId(id);
		return reviewRepository.findById(id).get();
	}

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
		int likes = likeRepository.countLikes(reviewId);
		int dislikes = likeRepository.countDislikes(reviewId);
		int useful = likes - dislikes;
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