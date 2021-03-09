package com.telegram.bot.repository;

import com.telegram.bot.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Integer> {
    boolean existsByName(String name);
    void deleteByName(String name);
    List<String> findAllNames();
    City findByName(String name);
}
