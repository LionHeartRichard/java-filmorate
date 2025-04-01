package ru.yandex.practicum.filmorate.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.impl.Friend;
import ru.yandex.practicum.filmorate.model.impl.User;
import ru.yandex.practicum.filmorate.repositories.impl.FriendRepositories;
import ru.yandex.practicum.filmorate.repositories.impl.UserRepositories;
import ru.yandex.practicum.filmorate.util.dtomapper.FriendDtoMapper;
import ru.yandex.practicum.filmorate.util.dtomapper.UserDtoMapper;
import ru.yandex.practicum.filmorate.dto.FriendDto;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.dto.UserDto.Response.Private;

@Slf4j
@Service
public class UserService {

	private final FriendRepositories friendRepo;
	private final UserRepositories userRepo;
	private final Map<Long, User> cache;

	@Autowired
	public UserService(UserRepositories userRepo, FriendRepositories friendRepo) {
		this.userRepo = userRepo;
		this.friendRepo = friendRepo;
		cache = this.userRepo.getTable(0).stream().collect(Collectors.toMap(User::getId, u -> u));
	}

	public Private create(UserDto.Request.Create userDto) {
		User user = UserDtoMapper.returnUser(userDto);
		Long id = userRepo.add(user)
				.orElseThrow(() -> new InternalServerException("Error - when adding a user to the database!"));
		user.setId(id);
		cache.put(id, user);
		return UserDtoMapper.returnPrivateDto(user);
	}

	public Private update(@Valid UserDto.Request.Update userDto) {
		Long id = userDto.getId();
		if (cache.containsKey(id)) {
			User user = UserDtoMapper.returnUser(userDto);
			userRepo.update(user).orElseThrow(() -> new InternalServerException("Failed! Update user in data base!"));
			cache.put(id, user);
			return UserDtoMapper.returnPrivateDto(user);
		}
		throw new NotFoundException("Failed update user! User not found!");
	}

	public Collection<Private> read() {
		return cache.values().stream().map(UserDtoMapper::returnPrivateDto).toList();
	}

	public Private findById(Long id) {
		User user = cache.get(id);
		if (user == null)
			throw new NotFoundException("User not found!");
		return UserDtoMapper.returnPrivateDto(user);
	}

	public FriendDto.Response.Private addFriend(Long id, Long friendId) {
		if (cache.containsKey(id) && cache.containsKey(friendId)) {
			Friend friend = Friend.builder().userId(id).friendId(friendId).build();
			friendRepo.add(friend).orElseThrow(() -> new InternalServerException("Failed! Add friend in data base!"));
			return FriendDtoMapper.returnDto(friend);
		}
		throw new NotFoundException("Faild: friends not found!");
	}

	public void deleteFriend(Long id, Long friendId) {
		if (cache.containsKey(id) && cache.containsKey(friendId))
			friendRepo.remove(id, friendId);
		else
			throw new NotFoundException("Failed! Friends not found!!!");
	}

	public Collection<Long> getFriends(Long id) {
		if (cache.containsKey(id))
			return friendRepo.getFriends(id);
		throw new NotFoundException("Failed! User not found!");
	}

	public Collection<Long> getCommonFriends(Long id, Long otherId) {
		if (cache.containsKey(id) && cache.containsKey(otherId)) {
			Set<Long> idx = friendRepo.getFriends(id);
			Set<Long> otherIdx = friendRepo.getFriends(otherId);
			if (!idx.isEmpty() && !otherIdx.isEmpty())
				return otherIdx.stream().filter(i -> idx.contains(i)).collect(Collectors.toSet());
		}
		throw new NotFoundException("Failed! Friends not found!!!");
	}
}
