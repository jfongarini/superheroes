package com.mindata.superheroes.service;

import com.mindata.superheroes.dto.SuperHeroDto;
import com.mindata.superheroes.exception.InvalidParameterException;
import com.mindata.superheroes.exception.NotFoundException;
import com.mindata.superheroes.model.SuperHero;
import com.mindata.superheroes.repository.SuperHeroRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
            throw new NotFoundException("SuperHero not found");
        }
        return optionalSuperHero.get();
    }

    public List<SuperHero> getAllByName(String name) {
        return superHeroRepository.findAllByName(name);
    }

    public SuperHero create(SuperHeroDto newSuperHeroDto) {
        validateSuperHero(newSuperHeroDto);
        SuperHero newSuperHero = SuperHeroDto.toSuperHero(newSuperHeroDto);
        newSuperHero.setCreatedAt(LocalDateTime.now());
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
        existingHero.setUpdatedAt(LocalDateTime.now());
        superHeroRepository.save(existingHero);
        return existingHero;
    }

    public void delete(Long id) {
        SuperHero heroToDelete = getById(id);
        superHeroRepository.delete(heroToDelete);
    }

    private void validateSuperHero(SuperHeroDto superHero) {
        if (StringUtils.isBlank(superHero.getName())) {
            throw new InvalidParameterException("Superhero name cannot be null or empty.");
        }
        if (StringUtils.isBlank(superHero.getDescription())) {
            throw new InvalidParameterException("The superhero description cannot be null or empty.");
        }
        if (superHero.getSuperPowers().isEmpty()) {
            throw new InvalidParameterException("The list of superhero superpowers cannot be null or empty.");
        }
        if (superHero.getVulnerabilities().isEmpty()) {
            throw new InvalidParameterException("The list of superhero vulnerabilities cannot be null or empty.");
        }
    }

}
