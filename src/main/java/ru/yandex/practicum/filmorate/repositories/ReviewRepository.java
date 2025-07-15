package ru.yandex.practicum.filmorate.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;
import java.util.Optional;

@Repository
public class ReviewRepository extends BaseRepository<Review> {

    public ReviewRepository(JdbcTemplate jdbcTemplate, RowMapper<Review> reviewRowMapper) {
        super(jdbcTemplate, reviewRowMapper);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM REVIEWS WHERE REVIEW_ID = ?";
        delete(sql, id);
    }

    public Optional<Review> findById(Long id) {
        String sql = "SELECT * FROM REVIEWS WHERE REVIEW_ID = ?";
        return findOne(sql, id);
    }

    public List<Review> findAllReviews(Long count) {
        String sql = "SELECT R.* FROM REVIEWS AS R ORDER BY R.USEFUL DESC LIMIT ?";
        return findMany(sql, count);
    }

    public List<Review> findReviewsByFilmId(Long filmId, Long count) {
        String sql = "SELECT R.* FROM REVIEWS AS R WHERE FILM_ID = ? ORDER BY R.USEFUL DESC LIMIT ?";
        return findMany(sql, filmId, count);
    }

    public Review save(Review review) {
        if (review.getReviewId() != null && review.getReviewId() > 0) {
            String sql = "UPDATE REVIEWS " +
                    "SET CONTENT = ?, USER_ID = ?, FILM_ID = ?, IS_POSITIVE = ?, USEFUL = ? " +
                    "WHERE REVIEW_ID = ?";
            jdbc.update(sql,
                    review.getContent(),
                    review.getUserId(),
                    review.getFilmId(),
                    review.getIsPositive(),
                    review.getUseful(),
                    review.getReviewId()
            );
            return review;
        } else {
            return insert(review);
        }
    }

    private Review insert(Review review) {
        String sql = "INSERT INTO REVIEWS (CONTENT, USER_ID, FILM_ID, IS_POSITIVE) " +
                "VALUES (?, ?, ?, ?)";
        Long id = insert(sql,
                review.getContent(),
                review.getUserId(),
                review.getFilmId(),
                review.getIsPositive());
        return review.toBuilder().reviewId(id).build();
    }

    public void updateUseful(Long reviewId, int useful) {
        String sql = "UPDATE REVIEWS SET USEFUL = ? WHERE REVIEW_ID = ?";
        jdbc.update(sql, useful, reviewId);
    }
}