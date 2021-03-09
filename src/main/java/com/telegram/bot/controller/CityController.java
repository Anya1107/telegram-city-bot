package com.telegram.bot.controller;

import com.telegram.bot.service.CityServiceImpl;
import com.telegram.bot.service.exception.InvalidNameException;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ComponentScan(basePackages = "by.telegram.bot")
@RequestMapping(path = "/city")
public class CityController {
    private final CityServiceImpl cityService;

    public CityController(CityServiceImpl cityService) {
        this.cityService = cityService;
    }

    /**
     * Method for creating new city with description or not by POST method.
     * @param name is a name for new city.
     * @param sight is a sight for new city.
     * @return the ResponseEntity object with a specific status.
     * */
    @PostMapping(path = "/save")
    public ResponseEntity<?> save(@RequestParam String name, @RequestParam String sight){
        cityService.saveCity(name, sight);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * Method for delete city by DELETE method.
     * @param name is a name for looking for a city.
     * @return the ResponseEntity object with a specific status.
     * */
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam String name){
        if(name.length() < 1){
            throw new InvalidNameException("Invalid name", name, "delete");
        }
        if(cityService.existCityByName(name)){
            cityService.deleteByName(name);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /**
     * Method for updating city sight by PUT method.
     * @param name is a name for looking for a city.
     * @param newSight is a name of new sight.
     * @return the ResponseEntity object with a specific status.
     * */
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestParam String name, @RequestParam String newSight){
        if(name.length() < 1){
            throw new InvalidNameException("Invalid name", name, "update");
        }
        if(newSight.length() < 1){
            throw new InvalidNameException("Invalid value", newSight, "update");
        }
        cityService.updateCity(name, newSight);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}