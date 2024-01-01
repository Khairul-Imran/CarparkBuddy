package com.example.carparkapp.Models;

import java.io.Serializable;

// import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HDBCarParkInfo implements Serializable {

    private String lotsAvailable;
    private String carParkNumber;
    private String carParkName;
    private String updateDateTime; // Individual carpark update time.
    private String googleMapsLink;
}
