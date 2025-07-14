package com.ochwada.travel_planner.model;


import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * *******************************************************
 * Package: com.ochwada.travel_planner.model
 * File: City.java
 * Author: Ochwada
 * Date: Monday, 14.Jul.2025, 11:01 AM
 * Description: Represents a city the user wants to visit including the fetched weather info.
 * Objective:
 * *******************************************************
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "cities")
public class City {
    /** Unique MongoDM Identifier*/
    @Id
    private String id;

    /** Name of the city. Must not be black*/
    @NotBlank(message = "City name is required")
    private String name;

    /** Name of the country. Optional but good to have*/
    private String country;

    /** Current weather description fetched from OpenWeather.*/
    private String weatherDescription;

    /** Current temperature in degree Celsius fetched from OpenWeather*/
    private double temperature;
}
