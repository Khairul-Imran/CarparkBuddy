package com.example.carparkapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.carparkapp.Models.CarParkData;
import com.example.carparkapp.Services.CarParkService;
import com.example.carparkapp.Services.GoogleMapsService;

@SpringBootApplication
@EnableScheduling
public class CarparkappApplication implements CommandLineRunner{

	@Autowired
	CarParkService carParkService;

	@Autowired
	GoogleMapsService googleMapsService;

	public static void main(String[] args) {
		SpringApplication.run(CarparkappApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// Was testing the csv file. 
		// For reading the csv data.
		// String fileName = "HDBCarparkInformation.csv";

		// List<CarParkCode> listOfCarParkDetails = carParkService.getCarParkDetails(fileName);

		// for (CarParkCode carParkCode : listOfCarParkDetails) {
		// 	System.out.printf("Carpark Code: %s - Address: %s\n", carParkCode.car_park_no(), carParkCode.address());
		// }

		// Testing storing inside hashmap.
		// HashMap<String, String> carParkAddressMap = new HashMap<>();
		// carParkAddressMap = carParkService.setCarParkAddressesInHashMap();

		// System.out.println("Contents of the HashMap:");
        // for (Map.Entry<String, String> entry : carParkAddressMap.entrySet()) {
        //     System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        // }

		// Retrieves all hdb carpark data upon startup.
		CarParkData carParkData = carParkService.getCarParkData();

		// String test = googleMapsService.getGoogleMapsLink("BLK 131 EDGEDALE PLAINS");
		// System.out.println(test);
		// System.out.printf("Timestamp: %s \n HDB CarPark Info: %s\n", carParkData.getTimeStamp(), carParkData.getHdbCarParkData());

	}

}
