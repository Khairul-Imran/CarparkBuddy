package com.example.carparkapp.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.carparkapp.Services.CarParkJsonService;

import jakarta.json.JsonArray;

@RestController
public class CarParkRestController {
    
    @Autowired
    private CarParkJsonService carParkJsonService;

    @GetMapping(path = "/favourites/{username}")
    public ResponseEntity<String> getFavourites(@PathVariable String username) {
        JsonArray jsonFavsCarParkArray = carParkJsonService.favHDBCarParkToJson(username);
        String jsonString = jsonFavsCarParkArray.toString();

        return ResponseEntity.ok(jsonString);
    }

    @GetMapping(path = "/allHDBCarparks")
    public ResponseEntity<String> getAllCarparks() {
        
        JsonArray jsonCarParkArray = carParkJsonService.HDBCarParkToJson();
        String jsonString = jsonCarParkArray.toString();

        return ResponseEntity.ok(jsonString);
    }
}