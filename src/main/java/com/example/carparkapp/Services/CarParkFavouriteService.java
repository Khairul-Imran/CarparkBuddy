package com.example.carparkapp.Services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.carparkapp.Models.HDBCarParkInfo;
import com.example.carparkapp.Repository.CarParkRepository;

@Service
public class CarParkFavouriteService {

    @Autowired
    private CarParkRepository carParkRepository;

    public Map<String, HDBCarParkInfo> getFavourites(String username) {
        if (carParkRepository.hasFavourites(username)) {
            return carParkRepository.getFavourites(username);
        }

        return new HashMap<>(); // If no favourites exist for the user.
    }

    public void saveFavourite(String username, HDBCarParkInfo favourite) {
        carParkRepository.addFavourite(username, favourite);
    }

    // Only allowing to delete one at a time.
    public void deleteFavourite(String username, HDBCarParkInfo favourite) {
        carParkRepository.deleteFavourite(username, favourite);
    }

    public HDBCarParkInfo getHDBCarParkByName(String carParkName) {
        // Returns all carparks
        Map<String, HDBCarParkInfo> hdbCarParkMap = carParkRepository.getHDBCarParkDataFromRedis();
        HDBCarParkInfo hdbCarPark = hdbCarParkMap.get(carParkName);

        return hdbCarPark;
    }

    // Linked to scheduled task.
    public void updateFavourites() {
        Set<String> allRedisKeys = carParkRepository.getAllKeys(); // Each key is a user. 

        // Going through each user inside redis.
        for (String username : allRedisKeys) {
            if (!username.equals("HDBCarParks")) {
                System.out.println("Replacing carparks for the following user: " + username);

                Map<String, HDBCarParkInfo> userFavouritesToUpdate = carParkRepository.getFavourites(username); // Gives me all the user's favs.

                Collection<HDBCarParkInfo> carparksToUpdate = userFavouritesToUpdate.values();
                for (HDBCarParkInfo hdbCarParkInfo : carparksToUpdate) {
                    if (!carparksToUpdate.isEmpty()) {
                        deleteFavourite(username, hdbCarParkInfo);
                        
                        carParkRepository.addFavouriteBasedOnCarpParkName(username, hdbCarParkInfo.getCarParkName());
                    }
                }
            }
        }
    }
}