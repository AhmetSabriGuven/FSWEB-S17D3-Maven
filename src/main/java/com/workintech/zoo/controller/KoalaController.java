package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class KoalaController {
    private final Map<Integer, Koala> koalas = new HashMap<>();

    @GetMapping("/koalas")
    public List<Koala> findAll() {
        return new ArrayList<>(koalas.values());
    }

    @GetMapping("/koalas/{id}")
    public Koala findById(@PathVariable int id) {
        Koala koala = koalas.get(id);
        if (koala == null) {
            throw new ZooException("Koala not found with id: " + id, HttpStatus.NOT_FOUND);
        }
        return koala;
    }

    @PostMapping("/koalas")
    public Koala save(@RequestBody Koala koala) {
        validate(koala);
        koalas.put(koala.getId(), koala);
        return koala;
    }

    @PutMapping("/koalas/{id}")
    public Koala update(@PathVariable int id, @RequestBody Koala koala) {
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala not found with id: " + id, HttpStatus.NOT_FOUND);
        }
        validate(koala);
        koala.setId(id);
        koalas.put(id, koala);
        return koala;
    }

    @DeleteMapping("/koalas/{id}")
    public Koala delete(@PathVariable int id) {
        Koala koala = koalas.remove(id);
        if (koala == null) {
            throw new ZooException("Koala not found with id: " + id, HttpStatus.NOT_FOUND);
        }
        return koala;
    }

    private void validate(Koala koala) {
        if (koala == null || koala.getId() <= 0 || koala.getName() == null || koala.getName().isBlank()
                || koala.getWeight() <= 0 || koala.getSleepHour() <= 0 || koala.getGender() == null
                || koala.getGender().isBlank()) {
            throw new ZooException("Invalid koala data", HttpStatus.BAD_REQUEST);
        }
    }
}
