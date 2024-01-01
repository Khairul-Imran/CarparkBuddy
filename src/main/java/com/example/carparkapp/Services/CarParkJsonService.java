package com.example.carparkapp.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.carparkapp.Models.HDBCarParkInfo;
import com.example.carparkapp.Repository.CarParkRepository;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;


@Service
public class CarParkJsonService {

    @Autowired 
    private CarParkRepository carParkRepository;
    
    // For RestControllers
    public JsonArray HDBCarParkToJson() {
        List<HDBCarParkInfo> carParkList = carParkRepository.getFullHDBCarParkDataFromRedisInList();

        JsonArrayBuilder jsonCarParkArrayBuilder = Json.createArrayBuilder();

        for (HDBCarParkInfo carPark : carParkList) {
            JsonObject carParkJson = Json.createObjectBuilder()
                .add("lotsAvailable", carPark.getLotsAvailable())
                .add("carParkNumber", carPark.getCarParkNumber())
                .add("carParkName", carPark.getCarParkName())
                .add("updateDateTime", carPark.getUpdateDateTime())
                .add("googleMapsLink", carPark.getGoogleMapsLink())
                .build();

            jsonCarParkArrayBuilder.add(carParkJson);
        }
        JsonArray jsonCarParkArray = jsonCarParkArrayBuilder.build();

        return jsonCarParkArray;
    }

    public JsonArray favHDBCarParkToJson(String username) {
        List<HDBCarParkInfo> carParkList = carParkRepository.getFavHDBCarParkDataFromRedisInList(username);

        JsonArrayBuilder jsonCarParkArrayBuilder = Json.createArrayBuilder();

        for (HDBCarParkInfo carPark : carParkList) {
            JsonObject carParkJson = Json.createObjectBuilder()
                .add("lotsAvailable", carPark.getLotsAvailable())
                .add("carParkNumber", carPark.getCarParkNumber())
                .add("carParkName", carPark.getCarParkName())
                .add("updateDateTime", carPark.getUpdateDateTime())
                .add("googleMapsLink", carPark.getGoogleMapsLink())
                .build();

            jsonCarParkArrayBuilder.add(carParkJson);
        }
        JsonArray jsonCarParkArray = jsonCarParkArrayBuilder.build();

        return jsonCarParkArray;
    }
}
