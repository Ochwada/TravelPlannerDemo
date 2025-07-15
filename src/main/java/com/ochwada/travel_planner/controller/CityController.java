package com.ochwada.travel_planner.controller;


import com.ochwada.travel_planner.model.City;
import com.ochwada.travel_planner.service.CityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * *******************************************************
 * Package: com.ochwada.travel_planner.controller
 * File: CityController.java
 * Author: Ochwada
 * Date: Tuesday, 15.Jul.2025, 11:24 AM
 * Description: REST Controller for mapping HTTP's Requests' URL's with Java methods.
 * - Annotated with {@link RestController} to indicate that it is a Spring REST controller and that return values of
 * handler methods will be automatically serialized to JSON.
 * - All endpoints are accessible under the base URI path {@code /api/cities}.
 * -  {@code CityController} handles HTTP requests related to travel planning.
 * Objective:
 * *******************************************************
 */

@RestController
@RequestMapping("/api/cities")
public class CityController {

    /**
     * The service layer component responsible for city-related operations.
     */
    private final CityService service;

    /**
     * Constructs a new {@code CityController} with the specified {@link CityService} dependency.
     * The service is injected by Spring via constructor injection, enabling the controller to delegate business logic.
     *
     * @param service the {@link CityService} instance to be used by this controller
     */
    @Autowired
    public CityController(CityService service) {
        this.service = service;
    }

    /**
     * Adds a new {@link City} to the database and enriches it with real-time weather data.
     * *
     * This endpoint accepts a JSON payload representing a city, validates it using {@code @Valid}, and delegates the
     * save operation to the {@link CityService}. The service layer also fetches current weather data before persisting
     * the city to MongoDB.
     * *
     * Annotated with {@link PostMapping} to handle HTTP POST requests to {@code /api/cities}. The {@code @Valid} annotation
     * tells Spring to check that all constraints defined in the {@link City} object (e.g., {@code @NotBlank}, {@code @Size})
     * are satisfied before calling this method.
     *
     * @param city the {@link City} object sent in the request body, validated before processing
     * @return the saved {@link City} object, including weather description and temperature
     */
    @PostMapping
    public City addCity(@Valid @RequestBody City city) {
        return service.saveCity(city);
    }

    /**
     * Retrieves all {@link City} records from the database.
     * *
     * This endpoint handles HTTP GET requests to {@code /api/cities} and returns a list of all cities currently stored.
     * It delegates the retrieval operation to the {@link CityService}, which interacts with the underlying MongoDB database.
     * *
     * Annotated with {@link GetMapping} to map GET requests for city listings.
     * </p>
     *
     * @return a list of all stored {@link City} objects
     */
    @GetMapping
    public List<City> getAllCities() {
        return service.getAllCities();
    }

    /**
     * Retrieves a city by its unique identifier.
     * *
     * If the city exists in the database, this method returns an HTTP 200 OK response with the {@link City} object in
     * the body. If no city with the given ID is found, it returns an HTTP 404 Not Found response.
     *
     * @param id the unique identifier of the city to retrieve
     * @return a {@link ResponseEntity} containing the city if found, or a 404 status if not
     */
    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(@PathVariable String id) {
        Optional<City> city = service.getCityById(id);
        if (city.isPresent()) {
            return ResponseEntity.ok(city.get()); // HTTP 200 + JSON body
        } else {
            return ResponseEntity.notFound().build(); // HTTP 404, no body
        }
    }

    /**
     * Deletes a city by its unique identifier.
     * *
     * This endpoint handles HTTP DELETE requests to {@code /api/cities/{id}}. It delegates the deletion operation to
     * the {@link CityService}. If the city exists, it is removed from the database. Whether or not the city existed,
     * a response with HTTP status {@code 204 No Content} is returned to indicate that the operation completed successfully.
     * *
     * Annotated with {@link DeleteMapping} to map DELETE requests, and returns a {@link ResponseEntity}with no body.
     *
     * @param id the unique identifier of the city to be deleted
     * @return a {@link ResponseEntity} with HTTP status 204 if the deletion succeeds
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable String id) {
        service.deleteCity(id);
        return ResponseEntity.noContent().build();
    }
}
