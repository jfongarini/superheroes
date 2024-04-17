package com.mindata.superheroes.service;

import com.mindata.superheroes.dto.SuperHeroDto;
import com.mindata.superheroes.model.SuperHero;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SuperHeroService {

    private final List<SuperHero> superHeroes = new ArrayList<>();

    public List<SuperHero> getAll() {
        return superHeroes;
    }

    public SuperHero getById(Long id) {
        for (SuperHero hero : superHeroes) {
            if (hero.getId().equals(id)) {
                return hero;
            }
        }
        throw new RuntimeException("Not found");
    }

    public List<SuperHero> getAllByName(String name) {
        List<SuperHero> heroesByName = new ArrayList<>();
        for (SuperHero hero : superHeroes) {
            if (hero.getName().equals(name)) {
                heroesByName.add(hero);
            }
        }
        return heroesByName;
    }

    public SuperHero create(SuperHeroDto newSuperHeroDto) {
        validateSuperHero(newSuperHeroDto);
        SuperHero newSuperHero = SuperHeroDto.toSuperHero(newSuperHeroDto);
        superHeroes.add(newSuperHero);
        return newSuperHero;
    }

    public SuperHero update(SuperHeroDto updatedSuperHeroDto) {
        validateSuperHero(updatedSuperHeroDto);
        SuperHero existingHero = getById(updatedSuperHeroDto.getId());

        existingHero.setName(updatedSuperHeroDto.getName());
        existingHero.setDescription(updatedSuperHeroDto.getDescription());
        existingHero.setSuperPowers(updatedSuperHeroDto.getSuperPowers());
        existingHero.setVulnerabilities(updatedSuperHeroDto.getVulnerabilities());
        return existingHero;

    }

    public SuperHero delete(Long id) {
        SuperHero heroToDelete = getById(id);
        superHeroes.remove(heroToDelete);
        return heroToDelete;
    }

    private void validateSuperHero(SuperHeroDto superHero) {
        if (superHero.getName() == null || superHero.getName().isEmpty()) {
            throw new IllegalArgumentException("Superhero name cannot be null or empty.");
        }
        if (superHero.getDescription() == null || superHero.getDescription().isEmpty()) {
            throw new IllegalArgumentException("The superhero description cannot be null or empty.");
        }
        if (superHero.getSuperPowers().isEmpty()) {
            throw new IllegalArgumentException("The list of superhero superpowers cannot be null or empty.");
        }
        if (superHero.getVulnerabilities().isEmpty()) {
            throw new IllegalArgumentException("The list of superhero vulnerabilities cannot be null or empty.");
        }
    }

}
