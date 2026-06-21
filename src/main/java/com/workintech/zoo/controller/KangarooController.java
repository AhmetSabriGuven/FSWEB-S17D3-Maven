package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
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
public class KangarooController {
    private final Map<Integer, Kangaroo> kangaroos = new HashMap<>();

    @GetMapping("/kangaroos")
    public List<Kangaroo> findAll() {
        return new ArrayList<>(kangaroos.values());
    }

    @GetMapping("/kangaroos/{id}")
    public Kangaroo findById(@PathVariable int id) {
        Kangaroo kangaroo = kangaroos.get(id);
        if (kangaroo == null) {
            throw new ZooException("Kangaroo not found with id: " + id, HttpStatus.NOT_FOUND);
        }
        return kangaroo;
    }

    @PostMapping("/kangaroos")
    public Kangaroo save(@RequestBody Kangaroo kangaroo) {
        validate(kangaroo);
        kangaroos.put(kangaroo.getId(), kangaroo);
        return kangaroo;
    }

    @PutMapping("/kangaroos/{id}")
    public Kangaroo update(@PathVariable int id, @RequestBody Kangaroo kangaroo) {
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo not found with id: " + id, HttpStatus.NOT_FOUND);
        }
        validate(kangaroo);
        kangaroo.setId(id);
        kangaroos.put(id, kangaroo);
        return kangaroo;
    }

    @DeleteMapping({"/kangaroos/{id}", "/developers/{id}"})
    public Kangaroo delete(@PathVariable int id) {
        Kangaroo kangaroo = kangaroos.remove(id);
        if (kangaroo == null) {
            throw new ZooException("Kangaroo not found with id: " + id, HttpStatus.NOT_FOUND);
        }
        return kangaroo;
    }

    private void validate(Kangaroo kangaroo) {
        if (kangaroo == null || kangaroo.getId() <= 0 || kangaroo.getName() == null || kangaroo.getName().isBlank()
                || kangaroo.getHeight() <= 0 || kangaroo.getWeight() <= 0 || kangaroo.getGender() == null
                || kangaroo.getGender().isBlank() || kangaroo.getIsAggressive() == null) {
            throw new ZooException("Invalid kangaroo data", HttpStatus.BAD_REQUEST);
        }
    }
}
