package ru.yandex.practicum.filmorate.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.dto.UserDtoCreate;
import ru.yandex.practicum.filmorate.dto.UserDtoUpdate;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repositories.FriendRepository;
import ru.yandex.practicum.filmorate.repositories.UserRepository;
import ru.yandex.practicum.filmorate.util.dtomapper.UserDtoMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepo;
	private final FriendRepository friendRepo;

	public User create(@Valid UserDtoCreate dto) {
		User user = UserDtoMapper.returnUser(dto);
		log.trace("User create: {}", user.toString());
		return userRepo.save(user);
	}

	public User update(UserDtoUpdate dto) {
		Long id = dto.getId();
		User user = userRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Failed update user! User not found!"));
		user = UserDtoMapper.returnUser(user, dto);
		userRepo.update(user);
		log.trace("user update - done");
		return user;
	}

	public List<User> read() {
		return userRepo.findAll();
	}

	public User findById(Long id) {
		User user = userRepo.findById(id).orElseThrow(() -> new NotFoundException("User not found!"));
		return user;
	}

	public void addFriend(Long id, Long friendId) {
		log.trace("add friend: id: {}, friendId: {}", id, friendId);
		userRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Failed add friend in data base! User not found!"));
		userRepo.findById(friendId)
				.orElseThrow(() -> new NotFoundException("Failed add friend in data base! User not found!"));
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
