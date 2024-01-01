package com.example.carparkapp.Services;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;

@Service
public class GoogleMapsService {

    private String GOOGLE_MAPS_URL = "https://www.google.com/maps?q=";

    // Using address name.
    public String getGoogleMapsLink(String carParkName) {
        String urlComponent = null;

        try {
            urlComponent = URLEncoder.encode(carParkName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String fullURL = GOOGLE_MAPS_URL + urlComponent;

        return fullURL;
    }

}
