package ru.yandex.practicum.filmorate.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.FilmAnsDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.repositories.*;
import ru.yandex.practicum.filmorate.util.dtomapper.DtoMapperFilm;

import java.util.*;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationsService {

	LikeRepository repLike;
	FilmRepository repFilm;
	UserRepository repUser;
	DirectorRepository repDirector;
	MpaRepository repMpa;
	FilmGenreRepository repFilmGenre;
	GenreRepository repGenre;

	public List<FilmAnsDto> getFilmRecommendations(Long userId) {
		log.info("Starting search recommendations for user {}", userId);
		repUser.findById(userId).orElseThrow(() -> new NotFoundException("User not found!"));

		Map<Long, List<Long>> userLikes = getAllUserLikes();
		log.info("Loaded likes for {} users", userLikes.size());

		List<Long> similarUsers = findSimilarUsers(userId, userLikes);
		log.info("Found {} similar users for user {}", similarUsers.size(), userId);

		List<FilmAnsDto> recommendations = getRecommendedFilms(userId, similarUsers, userLikes);
		log.info("Generated {} recommendations for user {}", recommendations.size(), userId);

		return recommendations;
	}

	private Map<Long, List<Long>> getAllUserLikes() {
		log.trace("Loading all user likes from repository");
		List<Like> allLikes = repLike.findAll();
		Map<Long, List<Long>> userToFilmsMap = new HashMap<>();
		for (Like like : allLikes) {
			Long userId = like.getUserId();
			Long filmId = like.getFilmId();
			userToFilmsMap.putIfAbsent(userId, new ArrayList<>());
			userToFilmsMap.get(userId).add(filmId);
		}
		log.trace("Processed likes into user-film map");

		return userToFilmsMap;
	}

	private List<Long> findSimilarUsers(Long userId, Map<Long, List<Long>> userLikes) {
		log.trace("Finding similar users for user {}", userId);
		List<Long> targetLikes = userLikes.getOrDefault(userId, Collections.emptyList());
		log.debug("User {} has {} likes", userId, targetLikes.size());

		List<Long> similarUsers = userLikes.entrySet().stream().filter(entry -> !entry.getKey().equals(userId))
				.filter(entry -> entry.getValue().stream().anyMatch(targetLikes::contains)).sorted((e1, e2) -> {
					long common1 = countCommonLikes(e1.getValue(), targetLikes);
					long common2 = countCommonLikes(e2.getValue(), targetLikes);
					return Long.compare(common2, common1);
				}).map(Map.Entry::getKey).collect(Collectors.toList());

		log.trace("Found {} similar users after filtering and sorting", similarUsers.size());
		return similarUsers;
	}

	private int countCommonLikes(List<Long> userLikes, List<Long> targetLikes) {
		Set<Long> targetSet = new HashSet<>(targetLikes);
		targetSet.retainAll(userLikes);
		return targetSet.size();
	}

	private List<FilmAnsDto> getRecommendedFilms(Long userId, List<Long> similarUsers,
			Map<Long, List<Long>> userLikes) {
		log.trace("Getting recommended films for user {}", userId);
		Set<Long> recommendedFilmIds = getRecommendedFilmIds(userId, similarUsers, userLikes);
		log.debug("Found {} unique recommended film IDs", recommendedFilmIds.size());

		return getFilmRecommendationResponses(recommendedFilmIds);
	}

	private Set<Long> getRecommendedFilmIds(Long userId, List<Long> similarUsers, Map<Long, List<Long>> userLikes) {
		log.trace("Getting recommended film IDs for user {}", userId);
		Set<Long> targetUserLikes = new HashSet<>(userLikes.getOrDefault(userId, Collections.emptyList()));
		Set<Long> recommendations = new LinkedHashSet<>();

		for (Long similarUserId : similarUsers) {
			List<Long> similarUserLikes = userLikes.getOrDefault(similarUserId, Collections.emptyList());
			for (Long filmId : similarUserLikes) {
				if (!targetUserLikes.contains(filmId)) {
					recommendations.add(filmId);
				}
			}
		}

		return recommendations;
	}

	private List<FilmAnsDto> getFilmRecommendationResponses(Set<Long> filmIds) {
		if (filmIds.isEmpty()) {
			log.debug("No film IDs provided for DTO preparation");
			return Collections.emptyList();
		}

		List<Film> films = repFilm.findFilmsByIds(filmIds);
		log.debug("Retrieved {} films from repository", films.size());

		return films.stream()
				.map(film -> {
					Mpa mpa = repMpa.findById(film.getMpaId()).orElse(null);
					List<Genre> genres = getGenresForFilm(film.getId());
					List<Director> directors = repDirector.findByFilmId(film.getId());
					return DtoMapperFilm.getAnsDtoForFilm(film, mpa, genres, directors);
				})
				.collect(Collectors.toList());
	}

	private List<Genre> getGenresForFilm(Long filmId) {
		List<FilmGenre> filmGenres = repFilmGenre.findByFilmId(filmId);
		return filmGenres.stream()
				.map(fg -> repGenre.findById(fg.getGenreId()))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());
	}
}
