package com.example.carparkapp.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MallAndURACarPark {

    // To be deleted.

    private String carParkId;
    private String area;
    private String development; // Actual name of the carpark.
    private String location; // Not sure if I want to keep this.
    private Integer availableLots;
    private String lotType;
    private String agency; // Only LTA or URA
    private Boolean favourited; // Will set to false by default.
    
}
