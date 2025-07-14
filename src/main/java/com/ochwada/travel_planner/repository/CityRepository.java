package com.ochwada.travel_planner.repository;


import com.ochwada.travel_planner.model.City;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * *******************************************************
 * Package: com.ochwada.travel_planner.repository
 * File: CityRepository.java
 * Author: Ochwada
 * Date: Monday, 14.Jul.2025, 11:33 AM
 * Description: Repository interface for the City document. Spring Data MongoDB automatically implements
 *  * the interface at runtime, providing built-in CRUD methods
 * Objective:
 * *******************************************************
 */

public interface CityRepository extends MongoRepository<City, String> {

    // MongoRepository provides out-of-the-box CRUD methods

}
