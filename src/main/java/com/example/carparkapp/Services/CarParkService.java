package com.example.carparkapp.Services;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.carparkapp.Utils;
import com.example.carparkapp.Models.CarParkData;
import com.example.carparkapp.Models.HDBCarParkInfo;
import com.example.carparkapp.Repository.CarParkRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class CarParkService {

    @Autowired
    private CarParkRepository carParkRepository;

    @Autowired
    private GoogleMapsService googleMapsService;

    String URL_HDB_CARPARKS = "https://api.data.gov.sg/v1/transport/carpark-availability";
    String CSV_FILE = "HDBCarparkInformation.csv";

    // For extracting the codes and addresses from csv.
    public HashMap<String, String> setCarParkAddressesInHashMap() {
        HashMap<String, String> carParkAddressMap = new HashMap<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(CSV_FILE))) {
            List<String[]> csvLines = csvReader.readAll();
            csvLines.remove(0);

            for (String[] csvLine : csvLines) {
                String key = csvLine[0].trim(); // CarPark code.
                String value = csvLine[1].trim(); // CarPark address.

                carParkAddressMap.put(key, value);
            }

        } catch (IOException| CsvException e) {
            e.printStackTrace();
        }

        return carParkAddressMap;
    }

    // Sets the carpark address for the object.
    public String setCarParkObjectName(String carParkCode) {
        String carParkName = "";
        HashMap<String, String> carParkAddressMap = setCarParkAddressesInHashMap();
        if (carParkAddressMap.containsKey(carParkCode)) {
            carParkName = carParkAddressMap.get(carParkCode);
        }

        return carParkName;
    }

    public CarParkData getCarParkData() {
        List<HDBCarParkInfo> listOfHDBCarParkInfos = new ArrayList<>();
        CarParkData carParkData = new CarParkData();

        // Stuff you want to get.
        String payload;
        String timestamp = "";
        JsonArray carparkDataArray = null;

        if (listOfHDBCarParkInfos.isEmpty()) {

            String url = UriComponentsBuilder
                .fromUriString(URL_HDB_CARPARKS)
                .toUriString();

            RequestEntity<Void> request = RequestEntity
                .get(url)
                .build();
            
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = null;

            try {
                response = restTemplate.exchange(request, String.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            payload = response.getBody();
            JsonReader reader = Json.createReader(new StringReader(payload));
            // Structure -> { items: [ { timestamp, carpark_data: [ {} {} {} ] } ] }
            JsonObject outerJsonObject = reader.readObject();
            JsonArray items = outerJsonObject.getJsonArray("items");
            JsonObject objectInsideItems = items.getJsonObject(0);

            timestamp = objectInsideItems.getString("timestamp"); // Getting the timestamp. 
            carparkDataArray = objectInsideItems.getJsonArray("carpark_data"); // Stream through this array.

        }


        listOfHDBCarParkInfos = carparkDataArray.stream()
                .map(j -> j.asJsonObject())
                .map(o -> {
                    String carParkCode = o.getString("carpark_number", "N.A");
                    String carParkDateTime = Utils.formatDate(o.getString("update_datetime", "N.A"));
                    JsonArray carParkInfoArray = o.getJsonArray("carpark_info");
                    JsonObject carParkInfoObject = carParkInfoArray.getJsonObject(0);
                    String lotsAvailable = carParkInfoObject.getString("lots_available", "N.A");
                    String carParkName = setCarParkObjectName(carParkCode);
                    String googleMapsLink = googleMapsService.getGoogleMapsLink(carParkName);
                    return new HDBCarParkInfo(lotsAvailable, carParkCode, carParkName, carParkDateTime, googleMapsLink);
                })
                .toList();
        
        carParkData.setHdbCarParkData(listOfHDBCarParkInfos);
        carParkData.setTimeStamp(timestamp);
        carParkRepository.saveHDBCarParksToRedis(carParkData.getHdbCarParkData()); // Saves / updates the data into redis.

        return carParkData;
    }


}
