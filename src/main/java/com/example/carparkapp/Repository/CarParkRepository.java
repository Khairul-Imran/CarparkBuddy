package com.example.carparkapp.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.example.carparkapp.Models.HDBCarParkInfo;

@Repository
public class CarParkRepository {

    private String hashReference = "HDBCarParks";

    @Autowired @Qualifier("hdbcache")
    private RedisTemplate<String, HDBCarParkInfo> redisTemplate;

    // For storing the carpark data at initial startup of the application.
    public void saveHDBCarParksToRedis(List<HDBCarParkInfo> listOfHDBCarParkInfos) {
        HashOperations<String, String, HDBCarParkInfo> hashOperations = redisTemplate.opsForHash();

        for (HDBCarParkInfo carParkInfo : listOfHDBCarParkInfos) {
            hashOperations.put(hashReference, carParkInfo.getCarParkName().toString(), carParkInfo);
        }
        hashOperations.delete(hashReference, ""); // Deletes the empty hash fields.
    }

    // For retrieving all carpark data in a Map.
    public Map<String, HDBCarParkInfo> getHDBCarParkDataFromRedis() {
        HashOperations<String, String, HDBCarParkInfo> hashOperations = redisTemplate.opsForHash();
        return hashOperations.entries(hashReference);
    }

    // For retrieving a single carpark data.
    public HDBCarParkInfo getSingleHDBCarParkDataFromRedis(String carParkName) {
        HashOperations<String, String, HDBCarParkInfo> hashOperations = redisTemplate.opsForHash();

        return hashOperations.get(hashReference, carParkName);
    }

    // *************Methods for RestController*************
    // For retrieving all carpark data in a list instead.
    public List<HDBCarParkInfo> getFullHDBCarParkDataFromRedisInList() {
        HashOperations<String, String, HDBCarParkInfo> hashOperations = redisTemplate.opsForHash();

        Map<String, HDBCarParkInfo> tempCarParkMap = hashOperations.entries(hashReference);
        return new ArrayList<>(tempCarParkMap.values());
    }

    // For retrieving favourites in a list instead. Based on username.
    public List<HDBCarParkInfo> getFavHDBCarParkDataFromRedisInList(String username) {
        HashOperations<String, String, HDBCarParkInfo> hashOperations = redisTemplate.opsForHash();

        Map<String, HDBCarParkInfo> tempCarParkMap = hashOperations.entries(username);
        return new ArrayList<>(tempCarParkMap.values());
    }


    // *************Methods for favourites*************
    public boolean hasFavourites(String username) {
        return redisTemplate.hasKey(username);
    }

    public Map<String, HDBCarParkInfo> getFavourites(String username) {
        HashOperations<String, String, HDBCarParkInfo> hashOperations = redisTemplate.opsForHash();
        return hashOperations.entries(username);
    }

    public void deleteFavourite(String username, HDBCarParkInfo favourite) {
        HashOperations<String, String, HDBCarParkInfo> hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(username, favourite.getCarParkName().toString());
    }

    public void addFavourite(String username, HDBCarParkInfo favourite) {
        HashOperations<String, String, HDBCarParkInfo> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(username, favourite.getCarParkName().toString(), favourite);
    }

    // Adding favourite based on address name, NOT Carpark Object. Taking directly from Redis DB.
    public void addFavouriteBasedOnCarpParkName(String username, String carparkName) {
        HashOperations<String, String, HDBCarParkInfo> hashOperations = redisTemplate.opsForHash();
        
        Set<String> allRedisKeys = getAllKeys(); // Gives me all the keys.

        // Finding the relevant redisKey
        for (String usernameInsideRedisKeys : allRedisKeys) {
            if (usernameInsideRedisKeys.equals(username)) {
                hashOperations.put(usernameInsideRedisKeys, carparkName, getSingleHDBCarParkDataFromRedis(carparkName));
            }
        }
    }

    public Set<String> getAllKeys() {
        return redisTemplate.keys("*");
    }
}
