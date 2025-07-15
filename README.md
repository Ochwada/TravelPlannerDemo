# 🚅 Travel Planner / City Guide application

### 🌍 Description: 
A **Spring Boot-based RESTful API** for planning travel itineraries and viewing weather forecasts.
Provides endpoints for destination suggestions, itinerary management, and real-time weather integration.
Managed with Maven for dependency management, build automation, and deployment.

### Features
-  Itinerary Management – Create, update, and delete personalized trip itineraries. 
- Weather Integration – View live weather forecasts via the OpenWeatherMap API.
- MongoDB Backend – Flexible, document-based storage using Spring Data MongoDB.
- ⚙️ Maven-Managed – Easily build and deploy via Maven.

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
├── .idea/                          # IntelliJ IDEA settings
├── .mvn/                           # Maven wrapper files
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.ochwada.travel_planner/
│   │   │       ├── client/        # External API clients (e.g., WeatherClient)
│   │   │       │   └── WeatherClient.java
│   │   │       ├── model/         # Data models (e.g., City)
│   │   │       │   └── City.java
│   │   │       ├── repository/    # MongoDB repositories
│   │   │       │   └── CityRepository.java
│   │   │       ├── service/       # Business logic
│   │   │       │   └── CityService.java
│   │   │       └── TravelPlannerApplication.java  # Spring Boot main app
│   │   └── resources/
│   │       ├── static/            # Static web content (if needed)
│   │       ├── templates/         # Thymeleaf templates (if using MVC)
│   │       └── application.properties  # App configuration
│   └── test/
│       └── java/
│           └── com.ochwada.travel_planner/
│               └── TravelPlannerApplicationTests.java  # Unit tests
├── .env                            # Environment variables
├── .gitattributes
├── .gitignore
├── mvnw                            # Maven wrapper script (Unix)
├── mvnw.cmd                        # Maven wrapper script (Windows)
├── pom.xml                         # Maven project config
├── README.md                       # Project documentation
└── target/                         # Compiled bytecode (generated)

```

###  Configuration
Create a .env file in the root directory to store your environment variables securely:

```.env 
# MongoDB connection string (use MongoDB Atlas or local instance)
MONGODB_URI=mongodb+srv://<username>:<password>@<cluster-url>/travel_planner?retryWrites=true&w=majority

# OpenWeatherMap API key
OPENWEATHER_API=your_openweathermap_api_key

```