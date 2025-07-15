package ru.yandex.practicum.filmorate.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Event {
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	Timestamp timestamp;
	Long userId;
	EventType eventType;
	Operation operation;
	Long eventId; // primary key
	Long entityId; // идентификатор сущности, с которой произошло событие
}
