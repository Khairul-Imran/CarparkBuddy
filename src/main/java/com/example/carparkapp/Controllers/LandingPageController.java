package com.example.carparkapp.Controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.carparkapp.Models.HDBCarParkInfo;
import com.example.carparkapp.Models.UserRegistration;
import com.example.carparkapp.Services.CarParkFavouriteService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping(path = "/")
public class LandingPageController {

    @Autowired
    private CarParkFavouriteService carParkFavouriteService;
    
    @GetMapping
    public String getLandingPage(Model model) {
        UserRegistration userRegistration = new UserRegistration();

        model.addAttribute("userRegistration", userRegistration);

        return "landingpage";
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String loginRequest(@Valid @ModelAttribute("userRegistration") UserRegistration userRegistrationForm, BindingResult result, Model model, HttpSession httpSession) {

        if (result.hasErrors()) {
            return "landingpage"; // Stays at the same page.
        }

        Map<String, HDBCarParkInfo> favourites = carParkFavouriteService.getFavourites(userRegistrationForm.getUsername());
        String username = userRegistrationForm.getUsername();

        httpSession.setAttribute("username", username);
        model.addAttribute("favourites", favourites);
        model.addAttribute("username", username);

        return "home";
    }



}
