package ru.yandex.practicum.filmorate.dto;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Min;
import lombok.Value;

@Validated
public enum FriendDto {;
	private interface Id { @Min(value=0) Long getId(); }
    private interface FriendId { @Min(value=0) Long getFriendId(); }
    
    public enum Response{;
        @Value public static class Private implements Id, FriendId {
        	Long id;
            Long friendId;
        }
    }
}
