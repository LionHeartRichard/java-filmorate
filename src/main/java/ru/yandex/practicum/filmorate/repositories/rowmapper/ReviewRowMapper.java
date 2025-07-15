package ru.yandex.practicum.filmorate.repositories.rowmapper;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Review;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ReviewRowMapper implements RowMapper<Review> {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Review mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        long reviewId = rs.getLong("REVIEW_ID");

        return Review.builder()
                .reviewId(reviewId)
                .content(rs.getString("CONTENT"))
                .userId(rs.getLong("USER_ID"))
                .filmId(rs.getLong("FILM_ID"))
                .isPositive(rs.getBoolean("IS_POSITIVE"))
                .useful(rs.getInt("USEFUL"))
                .likes(getLikes(reviewId))
                .dislikes(getDislikes(reviewId))
                .build();
    }

    private Set<Long> getLikes(long reviewId) {
        String sql = "SELECT USER_ID FROM REVIEW_LIKES WHERE REVIEW_ID = ? AND IS_LIKE = true";
        List<Long> userIds = jdbcTemplate.queryForList(sql, Long.class, reviewId);
        return new HashSet<>(userIds);
    }
    private Set<Long> getDislikes(long reviewId) {
        String sql = "SELECT USER_ID FROM REVIEW_LIKES WHERE REVIEW_ID = ? AND IS_LIKE = false";
        List<Long> userIds = jdbcTemplate.queryForList(sql, Long.class, reviewId);
        return new HashSet<>(userIds);
    }
}
