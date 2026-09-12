package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/koalas")
public class KoalaController {

    private Map<Integer, Koala> koalas;

    @PostConstruct
    public void init() {
        koalas = new HashMap<>();
    }

    @GetMapping
    public List<Koala> getAllKoalas() {
        return new ArrayList<>(koalas.values());
    }

    @GetMapping("/{id}")
    public Koala getKoalaById(@PathVariable int id) {
        Koala koala = koalas.get(id);
        if (koala == null) {
            throw new ZooException("Koala not found with id: " + id, HttpStatus.NOT_FOUND);
        }
        return koala;
    }

    @PostMapping
    public Koala saveKoala(@RequestBody Koala koala) {
        validateKoala(koala);
        koalas.put(koala.getId(), koala);
        return koala;
    }

    @PutMapping("/{id}")
    public Koala updateKoala(@PathVariable int id, @RequestBody Koala koala) {
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala not found with id: " + id, HttpStatus.NOT_FOUND);
        }
        koala.setId(id);
        koalas.put(id, koala);
        return koala;
    }

    @DeleteMapping("/{id}")
    public Koala deleteKoala(@PathVariable int id) {
        Koala removed = koalas.remove(id);
        if (removed == null) {
            throw new ZooException("Koala not found with id: " + id, HttpStatus.NOT_FOUND);
        }
        return removed;
    }

    private void validateKoala(Koala koala) {
        if (koala.getName() == null || koala.getGender() == null) {
            throw new IllegalArgumentException("Koala name and gender must not be null");
        }
    }
}