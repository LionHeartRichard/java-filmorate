package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.util.LocalValidator;
import ru.yandex.practicum.filmorate.dto.FriendDto;
import ru.yandex.practicum.filmorate.dto.UserDto;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final LocalValidator validator;
	private final UserService userService;

	@PostMapping
	public UserDto.Response.Private create(@Valid @RequestBody UserDto.Request.Create userDto) {
		log.trace("POST /users; userDto:", userDto.toString());
		return userService.create(userDto);
	}

	@PutMapping
	public UserDto.Response.Private update(@Valid @RequestBody UserDto.Request.Update userDto) {
		log.trace("PUT /users; userDto: {}", userDto.toString());
		return userService.update(userDto);
	}

	@GetMapping
	public Collection<UserDto.Response.Private> read() {
		log.trace("GET /users");
		return userService.read();
	}

	@GetMapping("/{id}")
	public UserDto.Response.Private findById(@PathVariable final Long id) {
		log.trace("GET /id: {}", id);
		validator.positiveValue(id, String.format("ID cannot be negative: %d", id));
		return userService.findById(id);
	}

	@PutMapping("/{id}/friends/{friend_id}")
	public FriendDto.Response.Private addFriend(@PathVariable Long id,
			@PathVariable(value = "friend_id") Long friendId) {
		log.trace("PUT /id/friend/friend_id");
		validator.positiveValue(id, String.format("ID cannot be negative: %d", id));
		validator.positiveValue(friendId, String.format("ID cannot be negative: %d", friendId));
		return userService.addFriend(id, friendId);
	}

	@DeleteMapping("/{id}/friends/{friend_id}")
	public void deleteFriend(@PathVariable final Long id, @PathVariable(value = "friend_id") final Long friendId) {
		log.trace("DELETE /{id}/friends/{friend_id}");
		validator.positiveValue(id, String.format("ID cannot be negative: %d", id));
		validator.positiveValue(friendId, String.format("ID cannot be negative: %d", friendId));
		userService.deleteFriend(id, friendId);
	}

	@GetMapping("/{id}/friends")
	public Collection<Long> getFriends(@PathVariable final Long id) {
		log.trace("GET /{id}/friends");
		validator.positiveValue(id, String.format("ID cannot be negative: %d", id));
		return userService.getFriends(id);
	}

}
