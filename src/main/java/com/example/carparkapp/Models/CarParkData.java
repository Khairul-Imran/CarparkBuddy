package com.example.carparkapp.Models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarParkData {

    private List<HDBCarParkInfo> hdbCarParkData;
    private String timeStamp; // Timestamp of when data was retrieved from the api.

}
