package com.example.carparkapp.Controllers;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.carparkapp.Models.HDBCarParkInfo;
import com.example.carparkapp.Repository.CarParkRepository;
import com.example.carparkapp.Services.CarParkFilterService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CarParksPageController {

    @Autowired
    private CarParkRepository carParkRepository;

    @Autowired
    private CarParkFilterService carParkFilterService;
    
    @GetMapping("/hdb-carparks")
    public ModelAndView getHDBCarParksPage(HttpSession httpSession) {
        ModelAndView mav = new ModelAndView("hdb-carparks");
        String username = (String) httpSession.getAttribute("username");

        Map<String, HDBCarParkInfo> hdbCarParkMap = carParkRepository.getHDBCarParkDataFromRedis();
        Map<String, HDBCarParkInfo> sortedHDBCarParkMap = hdbCarParkMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        mav.addObject("username", username);
        mav.addObject("hdbCarParkMap", sortedHDBCarParkMap);

        return mav;
    }

    @GetMapping("/hdb-filter")
    public ModelAndView filterHDBCarParksPage(@RequestParam(name = "query") String searchQuery, HttpSession httpSession) {
        ModelAndView mav = new ModelAndView("hdb-carparks-filtered");
        String username = (String) httpSession.getAttribute("username");

        Map<String, HDBCarParkInfo> hdbCarParkMap = carParkRepository.getHDBCarParkDataFromRedis();
        Map<String, HDBCarParkInfo> sortedHDBCarParkMap = hdbCarParkMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        
        Map<String, HDBCarParkInfo> filteredHDBCarParkMap = carParkFilterService.filterHDBCarParkByAddress(sortedHDBCarParkMap, searchQuery);

        mav.addObject("query", searchQuery);
        mav.addObject("username", username);
        mav.addObject("hdbCarParkMap", filteredHDBCarParkMap);

        return mav;
    }
}
