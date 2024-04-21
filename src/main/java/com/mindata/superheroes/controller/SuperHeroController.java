package com.mindata.superheroes.controller;

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

    @GetMapping(path = "/all")
    public ResponseEntity<List<SuperHeroDto>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(SuperHeroDto.toDtos(superHeroService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuperHeroDto> getSuperHeroById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(SuperHeroDto.toDto(superHeroService.getById(id)));
    }

    @GetMapping("/search")
    public ResponseEntity<List<SuperHeroDto>> searchSuperHeroesByName(@RequestParam String name) {
        return ResponseEntity.status(HttpStatus.OK).body(SuperHeroDto.toDtos(superHeroService.getAllByName(name)));
    }

    @PostMapping
    public ResponseEntity<SuperHeroDto> createSuperHero(@RequestBody SuperHeroDto newSuperHeroDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(SuperHeroDto.toDto(superHeroService.create(newSuperHeroDto)));
    }

    @PutMapping
    public ResponseEntity<SuperHeroDto> updateSuperHero(@RequestBody SuperHeroDto updatedSuperHeroDto) {
        return ResponseEntity.status(HttpStatus.OK).body(SuperHeroDto.toDto(superHeroService.update(updatedSuperHeroDto)));
    }

    @DeleteMapping("/{id}")
    public void deleteSuperHero(@PathVariable Long id) {
        superHeroService.delete(id);
    }
}
