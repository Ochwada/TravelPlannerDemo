package com.ochwada.travel_planner.service;


import com.ochwada.travel_planner.client.WeatherClient;
import com.ochwada.travel_planner.model.City;
import com.ochwada.travel_planner.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * *******************************************************
 * Package: com.ochwada.travel_planner.service
 * File: CityService.java
 * Author: Ochwada
 * Date: Monday, 14.Jul.2025, 11:35 AM
 * Description: {@code CityService} handles business logic related to city data and weather integration.
 * -- This service communicates with the {@link CityRepository} to manage city records and with the {@link WeatherClient}
 * to fetch real-time weather information. It acts as a bridge between the controller layer and the data/API layers of the application.
 * *
 * - Annotated with {@link Service} to indicate that this class is a Spring-managed component
 * responsible for application-level logic.
 * Objective:
 * *******************************************************
 */

@Service
public class CityService {
    /**
     * Repository interface for accessing and managing city data in MongoDB.
     * Provides CRUD operations and custom queries using Spring Data MongoDB.
     */
    private final CityRepository repository;

    /**
     * Client used to retrieve real-time weather data from an external API (e.g., OpenWeatherMap).
     * Injected as a dependency to enrich city objects with current weather information.
     */
    private final WeatherClient weatherClient;

    /**
     * Constructs a {@code CityService} with the required dependencies.
     *
     * @param repository    the {@link CityRepository} used to interact with MongoDB for city data
     * @param weatherClient the {@link WeatherClient} used to fetch weather information from external APIs
     */
    @Autowired
    public CityService(CityRepository repository, WeatherClient weatherClient) {
        this.repository = repository; // Data Access Object.
        this.weatherClient = weatherClient; // http client.
    }

    /**
     * Saves a {@link City} object to the database after enriching it with real-time weather data.
     * This method performs the following steps:
     * 1. Calls the {@link WeatherClient} to fetch current weather information for the given city name.
     * 2. Updates the {@code city} object with temperature and weather description retrieved from the API.
     * 3. Inserts the enriched city document into the MongoDB collection via {@link CityRepository}.
     *
     * @param city the {@link City} object to be saved; must contain at least a valid name
     * @return the saved {@link City} object with weather fields populated
     */
    public City saveCity(City city) {
        WeatherClient.WeatherData weatherData = weatherClient.getWeatherForCity(city.getName());
        city.setWeatherDescription(weatherData.getDescription());
        city.setTemperature(weatherData.getTemperature());

        return repository.insert(city);
    }

    /**
     * Retrieves all cities stored in the database.
     * This method delegates to {@link CityRepository#findAll()} to fetch a complete list of city documents from the
     * MongoDB collection.
     *
     * @return a list of all {@link City} objects currently stored in the database
     */
    public List<City> getAllCities() {
        return repository.findAll();
    }

    /**
     * Retrieves a city from the database by its unique identifier.
     * *
     * This method uses {@link CityRepository#findById(Object)} to query the MongoDB collection.
     * If a city with the given ID exists, it is returned inside an {@link Optional}; otherwise,
     * an empty {@code Optional} is returned.
     *
     * @param id the unique identifier of the city to retrieve
     * @return an {@link Optional} containing the {@link City} if found, or empty if not found
     */
    public Optional<City> getCityById(String id) {
        return repository.findById(id);
    }

    /**
     * Deletes a city from the database by its unique identifier.
     * *
     * This method calls {@link CityRepository#deleteById(Object)} to remove the city document with the specified ID from
     * the MongoDB collection. If no city with the given ID exists, no action is taken.
     *
     * @param id the unique identifier of the city to be deleted
     */
    public void deleteCity(String id) {
        repository.deleteById(id);
    }

}
