# 🚅 Travel Planner / City Guide application

### 🌍 Description: 
A **Spring Boot-based RESTful API** for planning travel itineraries and viewing weather forecasts.
Provides endpoints for destination suggestions, itinerary management, and real-time weather integration.
Managed with Maven for dependency management, build automation, and deployment.

### Features
1. City Management – Create, update, and delete city . 
2. Weather Integration – View live weather forecasts via the OpenWeatherMap API. 
3. MongoDB Backend – Flexible, document-based storage using Spring Data MongoDB. 
4. Maven-Managed – Easily build and deploy via Maven.

### 🛠 Tech Stack
- Java 21+ 
- Spring Boot 
- Spring Web (REST)
- Spring Data MongoDB 
- MongoDB Atlas 
- OpenWeatherMap API 
- Maven

### 📁 Project Structure
```yaml
travel_planner/
├── .idea/                               # IntelliJ IDEA project settings
├── .mvn/                                # Maven wrapper support
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.ochwada.travel_planner/
│   │   │       ├── client/              # External API clients (e.g. Weather API)
│   │   │       │   └── WeatherClient.java
│   │   │       ├── config/              # Configuration classes (e.g. RestTemplateConfig)
│   │   │       │   └── RestTemplateConfig.java
│   │   │       ├── controller/          # REST Controllers
│   │   │       │   └── CityController.java
│   │   │       ├── model/               # Domain models / DTOs
│   │   │       │   └── City.java
│   │   │       ├── repository/          # MongoDB repositories
│   │   │       │   └── CityRepository.java
│   │   │       ├── service/             # Business/service logic
│   │   │       │   └── CityService.java
│   │   │       └── TravelPlannerApplication.java  # Spring Boot main entry point
│   │   └── resources/
│   │       ├── static/                  # Web resources (optional)
│   │       ├── templates/               # Thymeleaf templates (unused if REST-only)
│   │       └── application.properties   # Main configuration file
│   └── test/
│       └── java/
│           └── com.ochwada.travel_planner/
│               └── TravelPlannerApplicationTests.java  # Unit tests
├── .env                                 # Environment variable declarations
├── .gitattributes
├── .gitignore
├── mvnw                                 # Maven wrapper script (Unix)
├── mvnw.cmd                             # Maven wrapper script (Windows)
├── pom.xml                              # Project dependency configuration
├── README.md                            # Project documentation
└── target/                              # Compiled bytecode and build artifacts

```

###  Configuration
Create a .env file in the root directory to store your environment variables securely:

```.env 
# MongoDB connection string (use MongoDB Atlas or local instance)
MONGODB_URI=mongodb+srv://<username>:<password>@<cluster-url>/travel_planner?retryWrites=true&w=majority

# OpenWeatherMap API key
OPENWEATHER_API=your_openweathermap_api_key
```

##### RestTemplate Configuration
The application includes a custom configuration class for HTTP requests to external services:

``` java
/**
 * Annotated with {@link Configuration} to indicate that it contains Spring bean definitions.
 */
@Configuration
public class RestTemplateConfig {

    /**
     * Defines a {@link RestTemplate} bean for performing HTTP requests.
     *
     * The {@code RestTemplate} is used throughout the application to call external RESTful APIs such as OpenWeatherMap.
     *
     * @return a new instance of {@link RestTemplate}
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

This ensures that a single `RestTemplate` bean is available for dependency injection across the application, 
particularly in classes like `WeatherClient`.

### 📡 RESTful API Endpoints – `/api/cities`

All endpoints below are prefixed with `/api/cities` and return JSON responses.

```
| Method | Endpoint           | Description                                |
| ------ | ------------------ | ------------------------------------------ |
| GET    | `/api/cities`      | Retrieve all saved cities                  |
| GET    | `/api/cities/{id}` | Retrieve a specific city by its ID         |
| POST   | `/api/cities`      | Save a new city with enriched weather data |
| DELETE | `/api/cities/{id}` | Delete a city by its ID                    |
```

---
# IN CODE
### 🔷 `CityController.java`
```java
@GetMapping("/{id}")
public ResponseEntity<City> getCityById(@PathVariable String id) {
    Optional<City> city = service.getCityById(id);
    if (city.isPresent()) {
        return ResponseEntity.ok(city.get()); // HTTP 200 + JSON body
    } else {
        return ResponseEntity.notFound().build(); // HTTP 404, no body
    }
}
```
| Line                                | Meaning                              |
| ----------------------------------- | ------------------------------------ |
| `ResponseEntity<City>`              | HTTP response with `City` body       |
| `ResponseEntity.ok(city.get())`     | Returns 200 OK with the city as JSON |
| `ResponseEntity.notFound().build()` | Returns 404 Not Found with no body   |

`ResponseEntity<T>` represents the full HTTP response, including:

1. Status code (e.g. 200, 404)
2. Headers (e.g. Content-Type, Location)
3. Body (e.g. your `City` object)

