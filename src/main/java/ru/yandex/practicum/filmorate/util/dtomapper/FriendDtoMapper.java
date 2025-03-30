package ru.yandex.practicum.filmorate.util.dtomapper;

import ru.yandex.practicum.filmorate.dto.FriendDto.Response.Private;
import ru.yandex.practicum.filmorate.model.impl.Friend;

public class FriendDtoMapper {
	private FriendDtoMapper() {
	}

	public static Private returnDto(Friend friend) {
		Private dto = new Private(friend.getUserId(), friend.getFriendId());
		return dto;
	}
}
