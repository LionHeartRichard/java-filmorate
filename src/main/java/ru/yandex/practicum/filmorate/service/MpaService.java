package ru.yandex.practicum.filmorate.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.repositories.MpaRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class MpaService {
	private final MpaRepository rep;

	public Mpa create(Mpa mpa) {
		log.trace("save repository MPA");
		return rep.save(mpa);
	}

	public List<Mpa> read() {
		log.trace("read repository MPA");
		return rep.findAll();
	}

	public Mpa findById(Long id) {
		log.trace("find by id repository MPA");
		return rep.findById(id).orElseThrow(() -> new NotFoundException("MPA not found!!!"));
	}

	public void deleteById(Long id) {
		log.trace("Delete repository MPA by id");
		rep.deleteById(id);
	}
}
