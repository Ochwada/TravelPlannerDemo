package com.ochwada.travel_planner.client;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.*;
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

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

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


    /**
     * Constructs a {@code WeatherClient} with the given {@link RestTemplate} and {@link ObjectMapper}.
     * <p>
     * This client is responsible for interacting with the OpenWeather API to retrieve weather data.
     * The {@code RestTemplate} is used to perform HTTP requests, while the {@code ObjectMapper}
     * is used to parse JSON responses.
     * </p>
     *
     * @param restTemplate the HTTP client used to send requests to the OpenWeather API
     * @param objectMapper the JSON parser used to map API responses to Java objects
     */
    @Autowired
    public WeatherClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }


    /**
     * Fetches current weather information for a given city using the OpenWeather API.
     *
     * <p>Makes a synchronous HTTP GET request to the API using {@link RestTemplate},
     * then parses the JSON response using {@link com.fasterxml.jackson.databind.ObjectMapper}
     * to extract the weather description and temperature.
     *
     * @param cityName the name of the city to query (e.g., "Berlin")
     * @return a {@link WeatherData} object containing the weather description and temperature in Celsius
     * @throws RuntimeException if the JSON response cannot be parsed
     */
    public WeatherData getWeatherForCity(String cityName) {
        // Construct the full URL with query parameters
        String url = String.format("%s?q=%s&appid=%s&units=metric", apiUrl, cityName, apiKey);


        try {
            //JsonNode json = mapper.readTree(response.getBody());
            /**
             * Makes an HTTP GET request to the given URL and retrieves the response body as a String.
             * “Hey Spring, please GET this URL and convert the response body to a String.”
             * Uses RestTemplate's getForObject() method to perform a synchronous call. Automatically handles connection,
             * request execution, and response parsing.
             *
             * @param url the complete URL to call (including query parameters)
             * @return the response body as a plain String
             */
            String response = restTemplate.getForObject(url, String.class);

            /**
             * Parses the JSON response String into a Jackson JsonNode tree. Uses ObjectMapper's readTree() method to
             * convert the raw JSON text into a navigable tree structure, enabling easy access to fields without defining
             * a full Java class.
             *
             * @param response the JSON response body as a String
             * @return a JsonNode representing the root of the JSON tree
             */
            JsonNode root = objectMapper.readTree(response);

            /**
             * Extracts the weather description from the JSON tree.
             * Navigates to the "weather" array in the JSON, selects the first object, and retrieves the value of its
             * "description" field as text.
             * Example JSON path: weather[0].description
             *
             * @param root the root JsonNode of the parsed JSON response
             * @return the weather description text
             */
            String description = root.path("weather").get(0).path("description").asText();

            /**
             * Extracts the temperature value from the JSON tree.
             * Navigates to the "main" object in the JSON and retrieves the "temp" field as a double value representing
             * the temperature in Celsius.
             * Example JSON path: main.temp
             *
             * @param root the root JsonNode of the parsed JSON response
             * @return the temperature value in Celsius
             */
            double temperature = root.path("main").path("temp").asDouble();

            return new WeatherData(description, temperature);

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch weather data: " + e.getMessage());
        }
    }

    // =======================================WeatherData POJO ==================================================

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
