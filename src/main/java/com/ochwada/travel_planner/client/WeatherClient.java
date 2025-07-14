package com.ochwada.travel_planner.client;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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
     */
    private final RestTemplate restTemplate;

    /**
     * Constructs a {@code WeatherClient} with a {@link RestTemplate} instance.
     * The {@code RestTemplate} is injected by Spring via constructor injection.
     *
     * @param restTemplate the HTTP client used for sending requests to the OpenWeather API
     */
    @Autowired
    public WeatherClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Fetches current weather information for a given city using the OpenWeather API.
     *
     * @param cityName the name of the city to query
     * @return a {@link WeatherData} object containing temperature and description (currently returns {@code null})
     */
    public WeatherData getWeatherForCity(String cityName) {
        String url = String.format("%s?q=%s&appid=%s&units=metric", apiUrl, cityName, apiKey);
        /**
         * Sends an HTTP GET request to the specified {@code url} and expects a response body of type {@code String}.
         *
         * <p>This call uses {@link RestTemplate#getForEntity(String, Class)} which:
         * <ul>
         *   <li>Makes a synchronous HTTP GET request</li>
         *   <li>Returns a {@link ResponseEntity} that wraps the full HTTP response, including status code, headers, and body</li>
         *   <li>Maps the response body into a {@code String} (in this case, the raw JSON from the weather API)</li>
         * </ul>
         *
         * @param url the full API URL including query parameters
         * @return {@code ResponseEntity<String>} containing the raw JSON response from OpenWeather API
         * ***
         * It performs a GET request to the provided URL; It expects the response body to be a String.
         * It returns a ResponseEntity that gives you access to:
         *         1. the raw JSON (response.getBody())
         *         2. the status code (response.getStatusCode())
         *         3. any HTTP headers (response.getHeaders())
         */

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        /**
         * getForEntity: makes GET request
         * @returns ResponseEntity with body as String
         * Spring handles connection and response parsing
         * */
        return null;
    }

    /**
     * Simple POJO to hold weather data results returned from the API.
     * Includes description (e.g. "clear sky") and temperature in Celsius.
     */
    public static class WeatherData {
        private String description;
        private double temperature;

        /**
         * Constructs a {@code WeatherData} object with the given weather description and temperature.
         *
         * @param description the textual weather description
         * @param temperature the temperature in Celsius
         */
        public WeatherData(String description, double temperature) {
            this.description = description;
            this.temperature = temperature;
        }

        public String getDescription() {
            return description;
        }

        public double getTemperature() {
            return temperature;
        }

    }
}
