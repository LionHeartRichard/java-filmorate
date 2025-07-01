package ru.yandex.practicum.filmorate.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repositories.GenreRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreService {
	private final GenreRepository rep;

	public Genre create(Genre genre) {
		rep.save(genre);
		return genre;
	}

	public List<Genre> read() {
		return rep.findAll();
	}

	public Genre findById(Long id) {
		return rep.findById(id).orElseThrow(() -> new NotFoundException("Genre not found!"));
	}

	public void delete(Long id) {
		log.trace("Delete genre by id: {}", id);
		rep.deleteGenreById(id);
	}
}
