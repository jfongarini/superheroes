package com.mindata.superheroes.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class SuperHero {

    private Long id;
    private String name;
    private String description;
    private List<String> superPowers;
    private List<String> vulnerabilities;
    private LocalDateTime cratedAt;
    private LocalDateTime updatedAt;

}
