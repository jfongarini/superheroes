package com.mindata.superheroes.dto;

import com.mindata.superheroes.model.SuperHero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuperHeroRequestDto {

    private String name;
    private String description;
    private List<String> superPowers;
    private List<String> vulnerabilities;

    public static SuperHero toSuperHero(SuperHeroRequestDto superHeroDto) {
        return SuperHero.builder()
                .name(superHeroDto.getName())
                .description(superHeroDto.getDescription())
                .superPowers(superHeroDto.getSuperPowers())
                .vulnerabilities(superHeroDto.getVulnerabilities())
                .build();
    }
}
