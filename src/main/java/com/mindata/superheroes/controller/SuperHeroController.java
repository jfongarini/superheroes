package com.mindata.superheroes.controller;

import com.mindata.superheroes.dto.SuperHeroDto;
import com.mindata.superheroes.service.SuperHeroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
