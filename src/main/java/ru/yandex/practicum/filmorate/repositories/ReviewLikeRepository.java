package ru.yandex.practicum.filmorate.repositories;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ReviewLikeRepository {
    private final JdbcTemplate jdbc;

    public void addOrUpdate(Long reviewId, Long userId, boolean isLike) {

        // сначала удаляем старый лайк/дизлайк
        String deleteSql = "DELETE FROM REVIEW_LIKES WHERE REVIEW_ID = ? AND USER_ID = ?";
        jdbc.update(deleteSql, reviewId, userId);

        // добавляем новый
        String insertSql = "INSERT INTO REVIEW_LIKES (REVIEW_ID, USER_ID, IS_LIKE) VALUES (?, ?, ?)";
        jdbc.update(insertSql, reviewId, userId, isLike);
        log.info("Inserted new {} for review {}", isLike ? "like" : "dislike", reviewId);
    }

    public void remove(Long reviewId, Long userId) {
        log.debug("remove rating: reviewId={}, userId={}", reviewId, userId);
        String sql = "DELETE FROM REVIEW_LIKES WHERE REVIEW_ID = ? AND USER_ID = ?";
        int deleted = jdbc.update(sql, reviewId, userId);
        log.info("Removed rating ({} rows) for review {}", deleted, reviewId);
    }

    public int countLikes(Long reviewId) {
        String sql = "SELECT COUNT(*) FROM REVIEW_LIKES WHERE REVIEW_ID = ? AND IS_LIKE = TRUE";
        int cnt = jdbc.queryForObject(sql, Integer.class, reviewId);
        log.debug("countLikes for review {} = {}", reviewId, cnt);
        return cnt;
    }

    public int countDislikes(Long reviewId) {
        String sql = "SELECT COUNT(*) FROM REVIEW_LIKES WHERE REVIEW_ID = ? AND IS_LIKE = FALSE";
        int cnt = jdbc.queryForObject(sql, Integer.class, reviewId);
        log.debug("countDislikes for review {} = {}", reviewId, cnt);
        return cnt;
    }
}
