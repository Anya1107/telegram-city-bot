package com.telegram.bot.service;

import java.util.List;

public interface CityService {
    void saveCity(String name, String sight);
    void deleteByName(String name);
    boolean existCityByName(String name);
    List<String> getAllNames();
    void updateCity(String name, String sight);
    String getSightByCityName(String name);
}
