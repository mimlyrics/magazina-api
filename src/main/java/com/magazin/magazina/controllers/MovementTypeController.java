package com.magazin.magazina.controllers;

import com.magazin.magazina.models.MovementType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
@CrossOrigin(
        origins = "https://magazina.onrender.com", // Frontend URL
        allowedHeaders = "*", // Allow all headers
        methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS}, // Allowed methods
        allowCredentials = "true" // Allow credentials like cookies
)
@RestController
@RequestMapping("/api/v1/movement-types")
public class MovementTypeController {

    @GetMapping
    public List<String> getMovementTypes() {
        // Return all the movement types as a list of strings
        return Arrays.asList(MovementType.values())
                .stream()
                .map(MovementType::name)  // Convert enum to string
                .toList();
    }

}
