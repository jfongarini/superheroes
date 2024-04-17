package com.mindata.superheroes.service;

import com.mindata.superheroes.dto.SuperHeroDto;
import com.mindata.superheroes.model.SuperHero;
import com.mindata.superheroes.repository.SuperHeroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SuperHeroService {

    private final SuperHeroRepository superHeroRepository;

    public SuperHeroService(SuperHeroRepository superHeroRepository) {
        this.superHeroRepository = superHeroRepository;
    }

    public List<SuperHero> getAll() {
        return superHeroRepository.findAll();
    }

    public SuperHero getById(Long id) {
        Optional<SuperHero> optionalSuperHero = superHeroRepository.findById(id);
        if (optionalSuperHero.isEmpty()){
            throw new RuntimeException("SuperHero not found");
        }
        return optionalSuperHero.get();
    }

    public List<SuperHero> getAllByName(String name) {
        return superHeroRepository.findAllByNameIgnoreCaseContaining(name.toLowerCase());
    }

    public SuperHero create(SuperHeroDto newSuperHeroDto) {
        validateSuperHero(newSuperHeroDto);
        SuperHero newSuperHero = SuperHeroDto.toSuperHero(newSuperHeroDto);
        superHeroRepository.save(newSuperHero);
        return newSuperHero;
    }

    public SuperHero update(SuperHeroDto updatedSuperHeroDto) {
        validateSuperHero(updatedSuperHeroDto);
        SuperHero existingHero = getById(updatedSuperHeroDto.getId());

        existingHero.setName(updatedSuperHeroDto.getName());
        existingHero.setDescription(updatedSuperHeroDto.getDescription());
        existingHero.setSuperPowers(updatedSuperHeroDto.getSuperPowers());
        existingHero.setVulnerabilities(updatedSuperHeroDto.getVulnerabilities());
        superHeroRepository.save(existingHero);
        return existingHero;
    }

    public void delete(Long id) {
        SuperHero heroToDelete = getById(id);
        superHeroRepository.delete(heroToDelete);
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
