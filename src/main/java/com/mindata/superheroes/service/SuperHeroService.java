package com.mindata.superheroes.service;

import com.mindata.superheroes.dto.SuperHeroRequestDto;
import com.mindata.superheroes.exception.InvalidParameterException;
import com.mindata.superheroes.exception.NotFoundException;
import com.mindata.superheroes.model.SuperHero;
import com.mindata.superheroes.repository.SuperHeroRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SuperHeroService {

    private final SuperHeroRepository superHeroRepository;

    public SuperHeroService(SuperHeroRepository superHeroRepository) {
        this.superHeroRepository = superHeroRepository;
    }

    public List<SuperHero> getAll() {
        return superHeroRepository.findAll();
    }

    @Cacheable("superheroCache")
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

    public SuperHero create(SuperHeroRequestDto newSuperHeroDto) {
        validateSuperHero(newSuperHeroDto);
        if (superHeroRepository.existsByName(newSuperHeroDto.getName())) {
            throw new InvalidParameterException("Superhero with the same name already exists.");
        }
        SuperHero newSuperHero = SuperHeroRequestDto.toSuperHero(newSuperHeroDto);
        newSuperHero.setCreatedAt(LocalDateTime.now());
        superHeroRepository.save(newSuperHero);
        return newSuperHero;
    }

    @CacheEvict(value = "superheroCache", allEntries = true)
    public SuperHero update(SuperHeroRequestDto updatedSuperHeroDto, Long id) {
        validateSuperHero(updatedSuperHeroDto);
        if (superHeroRepository.existsByNameAndIdNot(updatedSuperHeroDto.getName(), id)) {
            throw new InvalidParameterException("Another superhero with the same name already exists.");
        }
        SuperHero existingHero = getById(id);

        existingHero.setName(updatedSuperHeroDto.getName());
        existingHero.setDescription(updatedSuperHeroDto.getDescription());
        existingHero.setSuperPowers(updatedSuperHeroDto.getSuperPowers());
        existingHero.setVulnerabilities(updatedSuperHeroDto.getVulnerabilities());
        existingHero.setUpdatedAt(LocalDateTime.now());
        superHeroRepository.save(existingHero);
        return existingHero;
    }

    @CacheEvict(value = "superheroCache", key = "#id")
    public void delete(Long id) {
        SuperHero heroToDelete = getById(id);
        superHeroRepository.delete(heroToDelete);
    }

    private void validateSuperHero(SuperHeroRequestDto superHero) {
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
        if (!hasUniqueElements(superHero.getSuperPowers())) {
            throw new InvalidParameterException("The list of superhero superpowers cannot contain duplicate elements.");
        }
        if (!hasUniqueElements(superHero.getVulnerabilities())) {
            throw new InvalidParameterException("The list of superhero vulnerabilities cannot contain duplicate elements.");
        }

    }

    private boolean hasUniqueElements(List<String> list) {
        Set<String> set = new HashSet<>(list);
        return set.size() == list.size();
    }

}
