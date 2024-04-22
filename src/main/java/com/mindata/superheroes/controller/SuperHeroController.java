package com.mindata.superheroes.controller;

import com.mindata.superheroes.annotation.CustomTimed;
import com.mindata.superheroes.dto.SuperHeroDto;
import com.mindata.superheroes.service.SuperHeroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/superheroes")
@RestController
public class SuperHeroController {

    private final SuperHeroService superHeroService;

    public SuperHeroController(SuperHeroService superHeroService) {
        this.superHeroService = superHeroService;
    }

    @CustomTimed
    @GetMapping(path = "/all")
    public ResponseEntity<List<SuperHeroDto>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(SuperHeroDto.toDtos(superHeroService.getAll()));
    }

    @CustomTimed
    @GetMapping("/{id}")
    public ResponseEntity<SuperHeroDto> getSuperHeroById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(SuperHeroDto.toDto(superHeroService.getById(id)));
    }

    @CustomTimed
    @GetMapping("/search")
    public ResponseEntity<List<SuperHeroDto>> searchSuperHeroesByName(@RequestParam String name) {
        return ResponseEntity.status(HttpStatus.OK).body(SuperHeroDto.toDtos(superHeroService.getAllByName(name)));
    }

    @CustomTimed
    @PostMapping
    public ResponseEntity<SuperHeroDto> createSuperHero(@RequestBody SuperHeroDto newSuperHeroDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(SuperHeroDto.toDto(superHeroService.create(newSuperHeroDto)));
    }

    @CustomTimed
    @PutMapping
    public ResponseEntity<SuperHeroDto> updateSuperHero(@RequestBody SuperHeroDto updatedSuperHeroDto) {
        return ResponseEntity.status(HttpStatus.OK).body(SuperHeroDto.toDto(superHeroService.update(updatedSuperHeroDto)));
    }

    @CustomTimed
    @DeleteMapping("/{id}")
    public void deleteSuperHero(@PathVariable Long id) {
        superHeroService.delete(id);
    }
}
