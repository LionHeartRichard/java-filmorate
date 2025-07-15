package ru.yandex.practicum.filmorate.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.dto.UserDtoCreate;
import ru.yandex.practicum.filmorate.dto.UserDtoUpdate;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.model.Operation;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repositories.EventRepository;
import ru.yandex.practicum.filmorate.repositories.FriendRepository;
import ru.yandex.practicum.filmorate.repositories.LikeRepository;
import ru.yandex.practicum.filmorate.repositories.UserRepository;
import ru.yandex.practicum.filmorate.util.dtomapper.DtoMapperUser;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private static final String NOT_FOUND_ID = "Failed! User not found by id in database!";
	private static final String EXISTS_EMAIL = "Failed! A user with this email already exists!";
	private static final String ADD_FRIEND = "Failed add friend in data base! User not found!";

	UserRepository repUser;
	FriendRepository repFriend;
	LikeRepository repLike;
	EventRepository repEvent;

	public User create(UserDtoCreate dto) {
		User user = DtoMapperUser.getUser(dto);
		user = repUser.save(user);
		log.trace("Done: User create! {}", user.toString());
		return user;
	}

	public User update(UserDtoUpdate dto) {
		checkEmail(dto.getEmail());
		User user = new User();
		user = repUser.findById(dto.getId()).orElseThrow(() -> new NotFoundException(NOT_FOUND_ID));
		user = DtoMapperUser.getUser(user, dto);
		repUser.update(user);
		log.trace("Done: User update! {}", user.toString());
		return user;
	}

	private void checkEmail(String email) {
		if (email != null) {
			Optional<User> userOpt = repUser.findByEmail(email);
			if (userOpt.isPresent())
				throw new NotFoundException(EXISTS_EMAIL);
		}
	}

	public List<User> read() {
		return repUser.findAll();
	}

	public User findById(Long userId) {
		return repUser.findById(userId).orElseThrow(() -> new NotFoundException(NOT_FOUND_ID));
	}

	public void addFriend(Long id, Long friendId) {
		log.trace("add friend: id: {}, friendId: {}", id, friendId);
		repUser.findById(id).orElseThrow(() -> new NotFoundException(ADD_FRIEND));
		repUser.findById(friendId).orElseThrow(() -> new NotFoundException(ADD_FRIEND));
		Friend friend = new Friend();
		friend.setUserId(id);
		friend.setFriendId(friendId);
		repFriend.save(friend);

		Event event = new Event();
		event.setTimestamp(System.currentTimeMillis());
		event.setUserId(id);
		event.setEventType(EventType.FRIEND);
		event.setOperation(Operation.ADD);
		event.setEntityId(friendId);

		repEvent.save(event);
	}

	public void deleteFriend(Long id, Long friendId) {
		log.trace("delete friend: id: {}, friendId: {}", id, friendId);
		repUser.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_ID));
		repUser.findById(friendId).orElseThrow(() -> new NotFoundException(NOT_FOUND_ID));
		repFriend.deleteFriend(id, friendId);

		Event event = new Event();
		event.setTimestamp(System.currentTimeMillis());
		event.setUserId(id);
		event.setEventType(EventType.FRIEND);
		event.setOperation(Operation.REMOVE);
		event.setEntityId(id);

		repEvent.save(event);
	}

	public Friend getFriendByPrimaryKey(Long pK) {
		return repFriend.findByPrimaryKey(pK).orElseThrow(() -> new NotFoundException(NOT_FOUND_ID));
	}

	public List<User> getFriends(Long id) {
		repUser.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_ID));
		List<Friend> swap = repFriend.findFriendsById(id);
		List<User> ans = new ArrayList<>();
		swap.forEach(v -> {
			Optional<User> userOpt = repUser.findById(v.getFriendId());
			if (userOpt.isPresent()) {
				ans.add(userOpt.get());
			}
		});
		return ans;
	}

	public Set<User> commonFriends(Long id, Long friendId) {
		repUser.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_ID));
		repUser.findById(friendId).orElseThrow(() -> new NotFoundException(NOT_FOUND_ID));
		List<Friend> swap = repFriend.commonFriends(id, friendId);
		Set<User> ans = new HashSet<>();
		swap.forEach(v -> {
			Optional<User> userOpt = repUser.findById(v.getFriendId());
			if (userOpt.isPresent()) {
				ans.add(userOpt.get());
			}
		});
		return ans;
	}

	public void deleteUser(Long id) {
		log.trace("delete user: id: {}", id);
		repUser.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_ID));
		repFriend.deleteUserById(id);
		repLike.deleteByUserId(id);
		repUser.deleteUserById(id);
	}

	public List<Event> getEventsUserById(Long id) {
		log.trace("events (feed) user: id: {}", id);
		if (repEvent.isPresentUser(id)) {
			return repEvent.findEventByUserId(id);
		}
		throw new NotFoundException(NOT_FOUND_ID);
	}
}
