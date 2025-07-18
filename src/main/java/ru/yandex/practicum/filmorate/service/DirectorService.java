package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.repositories.DirectorRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectorService {
    private final DirectorRepository repository;

    public Director findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Director not found"));
    }

    public List<Director> read() {
        return repository.findAll();
    }

    public Director create(Director director) {
        Long id = repository.save(director);
        return findById(id);
    }

    public Director update(Director director) {
        repository.findById(director.getId()).orElseThrow(() -> new NotFoundException("Director not found"));
        repository.update(director);
        return findById(director.getId());
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
