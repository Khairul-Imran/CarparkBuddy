package com.example.carparkapp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static String formatDate(String date) {
        LocalDateTime dateString = LocalDateTime.parse(date);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        String finalDate = dateString.format(dateTimeFormatter);

        return finalDate;
    }
    
}
