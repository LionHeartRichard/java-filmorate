package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.service.DirectorService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/directors")
@RequiredArgsConstructor
public class DirectorController {
    private final DirectorService service;

    @PostMapping
    public Director create(@Valid @RequestBody Director director) {
        log.trace("POST /directors");
        return service.create(director);
    }

    @GetMapping
    public List<Director> read() {
        log.trace("GET /directors");
        return service.read();
    }

    @GetMapping("/{id}")
    public Director findById(@PathVariable Long id) {
        log.trace("GET /directors/{id}");
        return service.findById(id);
    }

    @PutMapping
    public Director update(@Valid @RequestBody Director director) {
        log.trace("PUT /directors");
        return service.update(director);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        log.trace("DELETE /directors/{id}");
        service.delete(id);
    }
}
