package ru.yandex.practicum.filmorate.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.impl.Friend;
import ru.yandex.practicum.filmorate.model.impl.User;
import ru.yandex.practicum.filmorate.repositories.impl.FriendRepositories;
import ru.yandex.practicum.filmorate.repositories.impl.UserRepositories;
import ru.yandex.practicum.filmorate.util.dtomapper.FriendDtoMapper;
import ru.yandex.practicum.filmorate.util.dtomapper.UserDtoMapper;
import ru.yandex.practicum.filmorate.dto.FriendDto;
import ru.yandex.practicum.filmorate.dto.UserDto;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserService {

	private final FriendRepositories friendRepo;
	private final UserRepositories userRepo;

	public FriendDto.Response.Private addFriend(final Long id, final Long friendId) {
		log.trace("Начало. Добавление в друзья");
		userRepo.query(id).orElseThrow(() -> new NotFoundException("User not found in database!!!"));
		userRepo.query(friendId).orElseThrow(() -> new NotFoundException("Friend not found in database!!!"));
		log.trace("Обработка. Добавление в друзья");
		Friend ans = Friend.builder().userId(id).friendId(friendId).statusName("friend").build();
		friendRepo.add(ans);
		return FriendDtoMapper.returnDto(ans);
	}

	public void deleteFriend(final Long id, final Long friendId) {
		log.trace("Начало. Удаление из друзей");
		Optional<Friend> friendOpt = friendRepo.query(id, friendId);
		log.trace("Обработка. Удаление из друзей");
		if (friendOpt.isPresent())
			friendRepo.remove(id, friendId);
	}

	public Collection<UserDto.Response.Private> getFriends(final Long id) {
		Set<Long> friendsId = friendRepo.query(id, 0).keySet();
		return friendsId.stream().map(i -> {
			Optional<User> userOpt = userRepo.query(i);
			if (userOpt.isPresent())
				return UserDtoMapper.returnPrivateDto(userOpt.get());
			return null;
		}).toList();
	}

	public Collection<UserDto.Response.Private> getCommonFriends(final Long id, final Long otherId) {
		log.trace("Начало. Получение списка общих друзей");
		Set<Long> userIdexes = friendRepo.query(id, 0).keySet();
		Set<Long> otherIdexes = friendRepo.query(otherId, 0).keySet();
		log.trace("Обработка. Получение списка общих друзей");
		Set<Long> common = userIdexes.stream().filter(otherIdexes::contains).collect(Collectors.toSet());
		common = common == null ? new HashSet<>() : common;
		return common.stream().map(i -> {
			Optional<User> userOpt = userRepo.query(i);
			if (userOpt.isPresent())
				return UserDtoMapper.returnPrivateDto(userOpt.get());
			return null;
		}).toList();
	}

}
