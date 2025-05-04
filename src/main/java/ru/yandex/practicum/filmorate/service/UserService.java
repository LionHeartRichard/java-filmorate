package ru.yandex.practicum.filmorate.service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.impl.User;
import ru.yandex.practicum.filmorate.repositories.impl.FriendRepositories;
import ru.yandex.practicum.filmorate.repositories.impl.UserRepositories;
import ru.yandex.practicum.filmorate.util.dtomapper.UserDtoMapper;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.dto.UserDto.Response.Private;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepositories userRepo;
	private final FriendRepositories friendRepo;

	public Private create(UserDto.Request.Create userDto) {
		User user = UserDtoMapper.returnUser(userDto);
		log.trace("User create: {}", user.toString());
		Long id = userRepo.add(user)
				.orElseThrow(() -> new InternalServerException("Error - when adding a user to the database!"));
		user.setId(id);
		return UserDtoMapper.returnPrivateDto(user);
	}

	public Private update(@Valid UserDto.Request.Update userDto) {
		Long id = userDto.getId();
		Optional<User> userOpt = userRepo.getById(id);
		if (userOpt.isPresent()) {
			User user = UserDtoMapper.returnUser(userDto);
			userRepo.update(user).orElseThrow(() -> new InternalServerException("Failed update user in data base!"));
			log.trace("user update - done");
			return UserDtoMapper.returnPrivateDto(user);
		}
		throw new NotFoundException("Failed update user! User not found!");
	}

	public Collection<Private> read() {
		return userRepo.getStream().map(UserDtoMapper::returnPrivateDto).toList();
	}

	public Private findById(Long id) {
		User user = userRepo.getById(id).orElseThrow(() -> new NotFoundException("User not found!"));
		return UserDtoMapper.returnPrivateDto(user);
	}

	public void addFriend(Long id, Long friendId) {
		log.trace("add friend: id: {}, friendId: {}", id, friendId);
		Optional<User> userOpt = userRepo.getById(id);
		Optional<User> friendOpt = userRepo.getById(friendId);
		if (userOpt.isEmpty() || friendOpt.isEmpty()) {
			log.warn("Failed: friend not found!");
			throw new NotFoundException("ADD Failed: friend not found!");
		}
		friendRepo.add(id, friendId).orElseThrow(() -> new InternalServerException("Failed add friend in data base!"));
	}

	public void deleteFriend(Long id, Long friendId) {
		log.trace("delete friend: id: {}, friendId: {}", id, friendId);
		friendRepo.removeRow(id, friendId).orElseThrow(() -> new NotFoundException("DELETE Failed: friend not found!"));
	}

	public Collection<Long> getFriends(Long id) {
		return friendRepo.getFriends(id);
	}

	public Collection<Long> getCommonFriends(Long id, Long otherId) {
		Set<Long> idx = friendRepo.getFriends(id);
		Set<Long> idxOther = friendRepo.getFriends(otherId);
		if (idx.isEmpty() || idxOther.isEmpty())
			return null;
		return idxOther.stream().filter(i -> idx.contains(i)).toList();
	}
}
