package com.example.carparkapp.Scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.carparkapp.Models.CarParkData;
import com.example.carparkapp.Repository.CarParkRepository;
import com.example.carparkapp.Services.CarParkFavouriteService;
import com.example.carparkapp.Services.CarParkService;

@Component
public class CarParkDataUpdateScheduler {

    @Autowired
    private CarParkService carParkService;

    @Autowired
    private CarParkRepository carParkRepository;

    @Autowired
    private CarParkFavouriteService carParkFavouriteService;

    String CSV_FILE = "HDBCarparkInformation.csv";

    // Updates the HDB CarPark Data every minute.
    @Scheduled(fixedRate = 60000)
    public void updateHDBCarParks() {
        // List<CarParkCode> listOfCarParkDetails = carParkService.getCarParkDetails(CSV_FILE); // Gets the full details from csv.
        // List<String> listOfCarParkNames = carParkService.getListOfCarParkNames(listOfCarParkDetails);// Gets just the names.

        // carParkRepository.deleteHDBCarParks(listOfCarParkNames);
        CarParkData carParkData = carParkService.getCarParkData();
        System.out.printf("Your CarPark Data has been updated at: %s\n", carParkData.getTimeStamp());
    }

    @Scheduled(fixedRate = 60500)
    public void updateFavouriteHDBCarParks() {

        carParkFavouriteService.updateFavourites();
        System.out.println("Your user's favourites have been updated.");

    }

}
