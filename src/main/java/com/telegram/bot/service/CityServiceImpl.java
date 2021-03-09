package com.telegram.bot.service;

import com.telegram.bot.entity.City;
import com.telegram.bot.entity.Sight;
import com.telegram.bot.repository.CityRepository;
import com.telegram.bot.service.exception.IsExistException;
import com.telegram.bot.service.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CityServiceImpl implements CityService{
    private CityRepository cityRepository;


    /**
     * Method that creates new city with some description and saves it to the database.
     * @param name is a name for new city.
     * @param sight is a  for new city.
     * @throws IsExistException if the city is exist.
     * */
    @Override
    public void saveCity(String name, String sight) {
        if(cityRepository.existsByName(name)){
            throw new IsExistException("City is exist!", name, "save");
        }
        Sight sight1 = new Sight(sight);
        cityRepository.save(new City(name, sight1));
    }


    /**
     * Method that finds and deletes city from database by input name.
     * @param name a name for looking for a necessary city.
     * @throws NotFoundException if the city with current name is not found.

     * */
    @Override
    public void deleteByName(String name) {
        if(cityRepository.existsByName(name)){
            cityRepository.deleteByName(name);
            return;
        }
        throw new NotFoundException("City with name not found!", name, "delete");
    }


    /**
     * Method that gets list of names of all active cities.
     * @return list of names of all active cities as Strings.
     * */
    @Override
    public List<String> getAllNames() {
        return cityRepository.findAllNames();
    }


    /**
     * Method that finds and updates city by input name, and saves changes to the database.
     * @param name is a name for looking for a necessary city.
     * @param sight is a name of sight which you wont to add in current city.
     * @throws NotFoundException if the city with current name is not found.
     * */
    @Override
    public void updateCity(String name, String sight) {
        if(cityRepository.existsByName(name)){
            City city = cityRepository.findByName(name);
            Sight sights = city.getSight();
            sights.setName(sight);
        } else {
            throw new NotFoundException("City with name not found!", name, "update");
        }
    }


    /**
     * Method that finds and gets sight by cities name.
     * @param name a name for looking for a necessary city.
     * @throws NotFoundException if the city with name is not found.
     * @return city sight.
     * */
    @Override
    public String getSightByCityName(String name) {
        if(cityRepository.existsByName(name)){
            City city = cityRepository.findByName(name);
            return city.getSight().getName();
        } else {
            throw new NotFoundException("City with name not found!", name, "getSightByCityName");
        }
    }



    /**
     * Method that checks if the required city is in the database or not, by the input name
     * @param name a name for looking for a necessary city.
     * @throws IllegalArgumentException if the name is null.
     * @return true if such city in the database, or false if not.
     * */
    @Override
    public boolean existCityByName(String name) {
        if(name == null){
            throw new IllegalArgumentException("city name is null");
        }
        return cityRepository.existsByName(name);
    }
}
