package com.example.carparkapp.Controllers;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.carparkapp.Models.HDBCarParkInfo;
import com.example.carparkapp.Repository.CarParkRepository;
import com.example.carparkapp.Services.CarParkFavouriteService;
import com.example.carparkapp.Services.CarParkFilterService;

import jakarta.servlet.http.HttpSession;

@Controller
public class FavouritesController {
    
    @Autowired 
    private CarParkFavouriteService carParkFavouriteService;

    @Autowired
    private CarParkRepository carParkRepository;

    @Autowired
    private CarParkFilterService carParkFilterService;

    @PostMapping("/addFavourite")
    public String addFavourite(@RequestParam("carParkName") String carParkName, HttpSession httpSession, Model model) {
        String username = (String) httpSession.getAttribute("username");
        HDBCarParkInfo hdbCarParkInfo = carParkFavouriteService.getHDBCarParkByName(carParkName);

        carParkFavouriteService.saveFavourite(username, hdbCarParkInfo);

        return "redirect:/hdb-carparks";
    }

    @PostMapping("/filter-addFavourite")
    public ModelAndView addFavouriteInFilterPage(@RequestParam("carParkName") String carParkName, @RequestParam(name = "query") String searchQuery, HttpSession httpSession) {
        ModelAndView mav = new ModelAndView("hdb-carparks-filtered");
        String username = (String) httpSession.getAttribute("username");

        HDBCarParkInfo hdbCarParkInfo = carParkFavouriteService.getHDBCarParkByName(carParkName);
        carParkFavouriteService.saveFavourite(username, hdbCarParkInfo);

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

    @PostMapping("/removeFavourite")
    public String removeFavourite(@RequestParam("carParkName") String carParkName, HttpSession httpSession, Model model) {
        String username = (String) httpSession.getAttribute("username");
        HDBCarParkInfo hdbCarParkInfo = carParkFavouriteService.getHDBCarParkByName(carParkName);
        
        carParkFavouriteService.deleteFavourite(username, hdbCarParkInfo);

        return "redirect:/homepage";
    }
}
