package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.DuplicatedDataException;
import ru.yandex.practicum.filmorate.exception.NotValidParamException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.util.ErrorResponse;

@RestControllerAdvice
public class ErrorHandler {

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ErrorResponse handleNotFound(NotFoundException e) {
		return new ErrorResponse(e.getMessage());
	}

	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(DuplicatedDataException.class)
	public ErrorResponse handleDuplicatedData(DuplicatedDataException e) {
		return new ErrorResponse(e.getMessage());
	}

	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(ConditionsNotMetException.class)
	public ErrorResponse handleConditionsNotMet(ConditionsNotMetException e) {
		return new ErrorResponse(e.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NotValidParamException.class)
	public ErrorResponse handleNotValidParam(NotValidParamException e) {
		return new ErrorResponse(e.getMessage());
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler
	public ErrorResponse handleRuntime(RuntimeException e) {
		return new ErrorResponse(e.getMessage());
	}
}
