package ru.yandex.practicum.filmorate.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;
import ru.yandex.practicum.filmorate.util.LocalValidator;

@Slf4j
@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class MpaController {
	private final LocalValidator validator;
	private final MpaService service;

	@PostMapping
	public Mpa create(Mpa mpa) {
		log.trace("Controller create MPA");
		validator.positiveValue(mpa.getId(), "Id for MPA is not positive!");
		return service.create(mpa);
	}

	@GetMapping
	public List<Mpa> read() {
		log.trace("Controller read MPA");
		return service.read();
	}

	@GetMapping("/{id}")
	public Mpa findById(@PathVariable Long id) {
		log.trace("Controller find by id MPA");
		validator.positiveValue(id, "Id for MPA is not positive!");
		return service.findById(id);
	}

}
