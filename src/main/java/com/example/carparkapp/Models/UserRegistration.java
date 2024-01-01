package com.example.carparkapp.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegistration {

    @NotEmpty(message = "Username is mandatory.")
    @Size(min = 4, max = 15, message = "Name of at least 3 characters is required.")
    private String username;
}
