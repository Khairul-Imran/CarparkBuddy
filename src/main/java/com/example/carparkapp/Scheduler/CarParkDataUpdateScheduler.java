package com.example.carparkapp.Scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.carparkapp.Models.CarParkData;
import com.example.carparkapp.Services.CarParkFavouriteService;
import com.example.carparkapp.Services.CarParkService;

@Component
public class CarParkDataUpdateScheduler {

    @Autowired
    private CarParkService carParkService;

    @Autowired
    private CarParkFavouriteService carParkFavouriteService;

    String CSV_FILE = "HDBCarparkInformation.csv";

    // Updates the HDB CarPark Data every minute.
    @Scheduled(fixedRate = 60000)
    public void updateHDBCarParks() {

        CarParkData carParkData = carParkService.getCarParkData();
        System.out.printf("Your CarPark Data has been updated at: %s\n", carParkData.getTimeStamp());
    }

    @Scheduled(fixedRate = 60500)
    public void updateFavouriteHDBCarParks() {

        carParkFavouriteService.updateFavourites();
        System.out.println("Your user's favourites have been updated.");
    }

}
