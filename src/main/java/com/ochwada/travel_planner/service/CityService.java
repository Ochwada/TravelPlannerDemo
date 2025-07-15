package com.ochwada.travel_planner.service;


import com.ochwada.travel_planner.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * *******************************************************
 * Package: com.ochwada.travel_planner.service
 * File: CityService.java
 * Author: Ochwada
 * Date: Monday, 14.Jul.2025, 11:35 AM
 * Description: {@code CityService} handles the business logic related to city data management.
 * It acts as a bridge between the controller layer and the repository layer, providing methods to perform operations such
 * as retrieving, saving, and processing city information.
 * - Annotated with {@link Service} to indicate that it's a service-layer component eligible for Spring's component scanning
 * and dependency injection
 * Objective:
 * *******************************************************
 */


@Service
public class CityService {
    private final CityRepository repository;

    /**
     * Constructs a {@code CityService} with a {@link CityRepository} dependency.
     *
     * @param repository the repository used to perform CRUD operations on city data
     */
    @Autowired
    public CityService(CityRepository repository) {
        this.repository = repository;
    }
}
