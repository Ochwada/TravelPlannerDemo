package com.ochwada.travel_planner.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * *******************************************************
 * Package: com.ochwada.travel_planner.config
 * File: RestTemplateConfig.java
 * Author: Ochwada
 * Date: Tuesday, 15.Jul.2025, 12:34 PM
 * Description: {@code RestTemplateConfig} is a configuration class that defines application-wide Spring beans related
 * * to REST communication.
 * - This class provides a single {@link RestTemplate} bean, which can be injected into other components (e.g.,
 * {@link WeatherClient}) to perform HTTP requests to external APIs.
 * Objective:
 * *******************************************************
 */

/**
 * Annotated with {@link Configuration} to indicate that it contains Spring bean definitions.
 */
@Configuration
public class RestTemplateConfig {

    /**
     * Defines a {@link RestTemplate} bean for performing HTTP requests.
     * *
     * The {@code RestTemplate} is used throughout the application to call external RESTful APIs such as OpenWeatherMap.
     *
     * @return a new instance of {@link RestTemplate}
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
