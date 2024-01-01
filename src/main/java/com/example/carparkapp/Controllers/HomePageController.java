package com.example.carparkapp.Controllers;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.carparkapp.Models.HDBCarParkInfo;
import com.example.carparkapp.Services.CarParkFavouriteService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomePageController {

    @Autowired
    private CarParkFavouriteService carParkFavouriteService;

    @GetMapping("/homepage")
    public ModelAndView getHomePage(HttpSession httpSession) {
        ModelAndView mav = new ModelAndView("home");
        String username = (String) httpSession.getAttribute("username");
        
        Map<String, HDBCarParkInfo> favourites = carParkFavouriteService.getFavourites(username);
        Map<String, HDBCarParkInfo> favouritesSorted = favourites.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        
        mav.addObject("username", username);
        mav.addObject("favourites", favouritesSorted);

        return mav;
    }
    
}
