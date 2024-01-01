package com.example.carparkapp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.example.carparkapp.Models.HDBCarParkInfo;

import jakarta.json.Json;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;


public class Utils {

    public static String formatDate(String date) {
        LocalDateTime dateString = LocalDateTime.parse(date);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        String finalDate = dateString.format(dateTimeFormatter);

        return finalDate;
    }

    // Ended up not using this. Used the Json Service instead.
    // public static JsonObject HDBCarParkInfoToJsonObject(Map<String, HDBCarParkInfo> favourites) {
    //     JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        
    //     JsonBuilderFactory factory = Json.createBuilderFactory(favourites);

    //     for (Map.Entry<String, HDBCarParkInfo> entry : favourites.entrySet()) {
    //         String address = entry.getKey();
    //         HDBCarParkInfo carParkInfo = entry.getValue();

    //         JsonObject builderForCarPark = factory.createObjectBuilder()
    //             .add("carpark information", factory.createObjectBuilder()
    //                 .add("lotsAvailable", carParkInfo.getLotsAvailable())
    //                 .add("carParkNumber", carParkInfo.getCarParkNumber())
    //                 .add("carParkName", carParkInfo.getCarParkName())
    //                 .add("updateDateTime", carParkInfo.getUpdateDateTime())
    //             )
    //             .build();
             
    //         jsonObjectBuilder.add(address, builderForCarPark);
    //     }
    //     JsonObject jsonObject = jsonObjectBuilder.build();

    //     return jsonObject;
    // }
    
}
