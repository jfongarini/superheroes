package com.mindata.superheroes.dto;

import com.mindata.superheroes.model.SuperHero;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class SuperHeroDto {

    private Long id;
    private String name;
    private String description;
    private List<String> superPowers;
    private List<String> vulnerabilities;

    public static List<SuperHeroDto> toDtos(List<SuperHero> superHeroes) {
        return superHeroes.stream()
                .map(SuperHeroDto::toDto)
                .collect(Collectors.toList());
    }

    public static SuperHeroDto toDto(SuperHero superHero) {
        return SuperHeroDto.builder()
                .id(superHero.getId())
                .name(superHero.getName())
                .description(superHero.getDescription())
                .superPowers(superHero.getSuperPowers())
                .vulnerabilities(superHero.getVulnerabilities())
                .build();
    }

    public static SuperHero toSuperHero(SuperHeroDto superHeroDto) {
       return SuperHero.builder()
               .id(superHeroDto.getId())
               .name(superHeroDto.getName())
               .description(superHeroDto.getDescription())
               .superPowers(superHeroDto.getSuperPowers())
               .vulnerabilities(superHeroDto.getVulnerabilities())
               .build();
    }
}
