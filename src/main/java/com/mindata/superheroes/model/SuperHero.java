package com.mindata.superheroes.model;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.JoinColumn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Super_hero")
public class SuperHero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;
    private String description;

    @ElementCollection
    @CollectionTable(name = "super_hero_super_powers", joinColumns = @JoinColumn(name = "super_Hero_id"),uniqueConstraints = @UniqueConstraint(columnNames = {"super_Hero_id", "super_Powers"}))
    private List<String> superPowers;

    @ElementCollection
    @CollectionTable(name = "super_hero_vulnerabilities", joinColumns = @JoinColumn(name = "super_Hero_id"),uniqueConstraints = @UniqueConstraint(columnNames = {"super_Hero_id", "vulnerabilities"}))
    private List<String> vulnerabilities;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
