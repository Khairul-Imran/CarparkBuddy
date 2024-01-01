package com.example.carparkapp.Services;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.carparkapp.Models.HDBCarParkInfo;

import me.xdrop.fuzzywuzzy.FuzzySearch;

@Service
public class CarParkFilterService {

    public Map<String, HDBCarParkInfo> filterHDBCarParkByAddress(Map<String, HDBCarParkInfo> hdbCarParkMap, String searchQuery) {
        int acceptableIndex = 90; // To adjust.

        Map<String, HDBCarParkInfo> filteredHDBCarParkMap = hdbCarParkMap.entrySet().stream()
                .filter(carparkName -> {
                    int comparisonIndex = FuzzySearch.weightedRatio(carparkName.getKey().toLowerCase(), searchQuery);
                    return comparisonIndex >= acceptableIndex;
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return filteredHDBCarParkMap;
    }

}
