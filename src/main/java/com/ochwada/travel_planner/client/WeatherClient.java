package com.ochwada.travel_planner.client;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * *******************************************************
 * Package: com.ochwada.travel_planner.client
 * File: WeatherClient.java
 * Author: Ochwada
 * Date: Monday, 14.Jul.2025, 11:38 AM
 * Description: Client to fetch weather data from OpenWeather API
 * Objective:
 * *******************************************************
 */

/**
 * {@code WeatherClient} is a Spring-managed component that provides access to weather data
 * from the OpenWeather API.
 *
 * <p>Annotated with {@link org.springframework.stereotype.Component}, this class is automatically
 * detected and instantiated by Spring's component scanning mechanism. It becomes eligible for
 * dependency injection wherever needed in the application.
 *
 * <p>The API key and base URL for the OpenWeather service are injected from the application's
 * configuration (e.g., {@code application.properties} or environment variables) using the
 * {@link org.springframework.beans.factory.annotation.Value} annotation.
 */
@Component // Tells Spring Boot to manage this class as a bean for dependency injection
public class WeatherClient {

    /**
     * API key for authenticating requests to the OpenWeather API.
     * Injected from the property {@code openweather.api.key}.
     */
    @Value("${openweather.api.key}")
    private String apiKey;

    /**
     * Base URL for the OpenWeather API endpoint.
     * Injected from the property {@code openweather.api.url}.
     */
    @Value("${openweather.api.url}")
    private String apiUrl;

    /**
     * {@code RestTemplate} is a synchronous HTTP client used to perform RESTful API calls.
     *
     * <p>This instance is used by {@code WeatherClient} to send requests to the OpenWeather API
     * and retrieve weather data as JSON responses.
     *
     * <p>Note: While {@code RestTemplate} is still supported, Spring recommends using
     * {@code WebClient} (from Spring WebFlux) for non-blocking, reactive applications.
     */
    private  final RestTemplate restTemplate = new RestTemplate();
}
