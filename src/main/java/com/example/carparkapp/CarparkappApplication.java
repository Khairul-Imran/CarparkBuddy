package com.example.carparkapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.carparkapp.Models.CarParkData;
import com.example.carparkapp.Services.CarParkService;
import com.example.carparkapp.Services.GoogleMapsService;

@SpringBootApplication
@EnableScheduling
public class CarparkappApplication implements CommandLineRunner{

	@Autowired
	CarParkService carParkService;

	@Autowired
	GoogleMapsService googleMapsService;

	public static void main(String[] args) {
		SpringApplication.run(CarparkappApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		CarParkData carParkData = carParkService.getCarParkData();

	}

}
