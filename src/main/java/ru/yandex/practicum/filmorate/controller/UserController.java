package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.FilmRecommendationResponse;
import ru.yandex.practicum.filmorate.dto.UserDtoCreate;
import ru.yandex.practicum.filmorate.dto.UserDtoUpdate;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.RecommendationsService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.util.LocalValidator;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private static final String NOT_NEGATIVE = "ID cannot be negative: %d";

    private final LocalValidator validator;
    private final UserService userService;
    private final RecommendationsService recommendationsService;

    @PostMapping
    public User create(@Valid @RequestBody UserDtoCreate dto) {
        log.trace("POST /users; userDto:", dto.toString());
        return userService.create(dto);
    }

    @PutMapping
    public User update(@RequestBody UserDtoUpdate dto) {
        log.trace("PUT /users; userDto: {}", dto.toString());
        return userService.update(dto);
    }

    @GetMapping
    public List<User> read() {
        log.trace("GET /users");
        return userService.read();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable final Long id) {
        log.trace("GET users/id: {}", id);
        validator.positiveValue(id, String.format(NOT_NEGATIVE, id));
        return userService.findById(id);
    }

    @PutMapping("/{id}/friends/{friend_id}")
    public void addFriend(@PathVariable Long id, @PathVariable(value = "friend_id") Long friendId) {
        log.trace("PUT users/id/friend/friend_id");
        validator.positiveValue(id, String.format(NOT_NEGATIVE, id));
        validator.positiveValue(friendId, String.format(NOT_NEGATIVE, friendId));
        if (id.equals(friendId)) {
            log.warn("Failed! Identifiers cannot be equal");
            throw new ConditionsNotMetException("Failed! Identifiers cannot be equal");
        }
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friend_id}")
    public void deleteFriend(@PathVariable final Long id, @PathVariable(value = "friend_id") final Long friendId) {
        log.trace("DELETE users/{id}/friends/{friend_id}");
        validator.positiveValue(id, String.format(NOT_NEGATIVE, id));
        validator.positiveValue(friendId, String.format(NOT_NEGATIVE, friendId));
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable final Long id) {
        log.trace("GET users/{id}/friends");
        validator.positiveValue(id, String.format(NOT_NEGATIVE, id));
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{friend_id}")
    public Set<User> commonFriends(@PathVariable final Long id,
                                   @PathVariable(value = "friend_id") final Long friendId) {
        log.trace("COMMON users/{id}/friends/common/{friend_id}");
        validator.positiveValue(id, String.format(NOT_NEGATIVE, id));
        validator.positiveValue(friendId, String.format(NOT_NEGATIVE, friendId));
        return userService.commonFriends(id, friendId);
    }

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable final Long id) {
		log.trace("DELETE users/{id}");
		validator.positiveValue(id, String.format(NOT_NEGATIVE, id));
		userService.deleteUser(id);
	}

    @GetMapping("/{id}/recommendations")
    public List<FilmRecommendationResponse> getFilmRecommendations(@PathVariable final Long id) {
        log.trace("GET users/{id}/recommendations");
        validator.positiveValue(id, String.format(NOT_NEGATIVE, id));
        return recommendationsService.getFilmRecommendations(id);
    }
}

