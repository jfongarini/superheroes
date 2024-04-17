package com.mindata.superheroes.repository;

import com.mindata.superheroes.model.SuperHero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuperHeroRepository extends JpaRepository<SuperHero, Long> {
    @Query("SELECT sh FROM SuperHero sh WHERE LOWER(sh.name) LIKE %:name%")
    List<SuperHero> findAllByNameIgnoreCaseContaining(String name);
}
