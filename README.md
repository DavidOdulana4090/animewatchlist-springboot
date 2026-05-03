# Anime Watchlist API

A Spring Boot REST API for managing personal anime watchlists with user authentication and JWT
token-based security using this as a learning project

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-green)](https://spring.io/projects/spring-boot) [![Java](https://img.shields.io/badge/Java-21-orange)](https://openjdk.org/)

## Getting Started

### Prerequisites
- JDK 21 or higher
- Maven 3.6+
- PostgreSQL or similar database (H2 for development)

### Environment Variables

Configure the following in your `application.properties`:

```properties
spring.datasource.url=${DBURL}
spring.datasource.username=${DBUSERNAME}
spring.datasource.password=${DBPASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/DavidOdulana4090/animewatchlist-springboot.git
   cd animewatchlist-springboot
   ```

2. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The API will be available at `http://localhost:8080`

## Features

- **User Authentication**: Secure registration and login with email verification
- **JWT Token Security**: Token-based authentication for protected endpoints
- **Watchlist Management**: Add, update, and delete anime from your personal watchlist
- **Progress Tracking**: Track viewing progress for each anime entry
- **Rating & Favorites**: Rate anime and mark favorites
- **Genre Support**: Organize anime by genres
- **Password Management**: Forgot password and password reset functionality

## API Endpoints

### Authentication Endpoints

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| POST | `/register` | Register a new user | `{ "email": "user@example.com", "password": "password123" }` |
| POST | `/login` | Login and receive JWT token | `{ "email": "user@example.com", "password": "password123" }` |
| POST | `/logout` | Logout user | `{ "email": "user@example.com" }` |
| PUT | `/forgot-password` | Request password reset | Query param: `email=user@example.com` NEEDFIX
| PUT | `/update-password` | Update password | `{ "email": "user@example.com", "password": "newPassword123" }` |
| GET | `/users/me` | Get current user profile (requires Bearer token) | - |
| GET | `/token/validate` | Validate JWT token (requires Bearer token) | - |

### Anime Endpoints

| Method | Endpoint | Description | Request Body | Auth Required |
|--------|----------|-------------|--------------|---|
| GET | `/anime/list/{uuid}` | Get all anime in user's watchlist | - | Yes |
| POST | `/anime/add/{uuid}` | Add anime to watchlist | `{ "title": "Anime Title", "status": "Watching", "progress": 5, "genres": ["Action"], "rating": 8.5, "isFavourite": false }` | Yes |
| PUT | `/anime/update/{id}` | Update anime entry | `{ "title": "...", "status": "...", "progress": 0, "genres": [...], "rating": 0, "favourite": false }` | Yes |
| DELETE | `/anime/delete/{id}` | Delete anime from watchlist | - | Yes |

### Response Examples

**Login Response (ProfileDTO):**
```json
{
  "email": "user@example.com",
  "username": "user",
  "createdAt": "2026-01-15",
  "uuid": "550e8400-e29b-41d4-a716-446655440000",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Anime Response:**
```json
{
  "id": 1,
  "title": "Attack on Titan",
  "status": "Watching",
  "progress": 25,
  "genres": ["Action", "Fantasy"],
  "rating": 8.5,
  "favourite": true
}
```

**Anime List Response:**
```json
[
  {
    "id": 1,
    "title": "Attack on Titan",
    "status": "Watching",
    "progress": 25,
    "genres": ["Action", "Fantasy"],
    "rating": 8.5,
    "favourite": true,
    "userId": "550e8400-e29b-41d4-a716-446655440000"
  }
]
```

## Tech Stack

- **Spring Boot 3.0** - REST API and dependency injection framework
- **Spring Security** - Authentication and authorization
- **JWT (JSON Web Tokens)** - Stateless token-based authentication
- **Spring Data JPA** - Data persistence and ORM
- **Hibernate** - Object-relational mapping
- **Maven** - Build automation and dependency management
- **PostgreSQL / H2** - Relational database
- **Lombok** - Reduce boilerplate code with annotations

## Project Structure

```
src/main/java/com/david/animewatchlistspringboot/
├── controller/
│   ├── AnimeController.java        # Anime CRUD operations
│   └── UserController.java         # Authentication & user management
├── entity/
│   ├── Anime.java                  # Anime model
│   └── User.java                   # User model
├── repository/
│   ├── AnimeRepository.java        # Anime data access
│   └── UserRepository.java         # User data access
├── service/
│   └── JwtService.java             # JWT token management
├── config/
│   └── SecurityConfig.java         # Spring Security configuration
├── DTO/
│   ├── AnimeDTO.java               # Anime data transfer object
│   ├── CreateAccountDTO.java       # Registration request DTO
│   ├── LoginDTO.java               # Login request DTO
│   └── ProfileDTO.java             # User profile DTO
└── AnimewatchlistSpringbootApplication.java  # Entry point
```

## Usage Guide

### Example: Register and Login

Register a new user:
```bash
curl -X POST http://localhost:8080/register \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"password123"}'
```

Login:
```bash
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"password123"}'
```

Response:
```json
{
  "email": "user@example.com",
  "username": "user",
  "createdAt": "2026-01-15",
  "uuid": "550e8400-e29b-41d4-a716-446655440000",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Example: Add Anime to Watchlist

```bash
curl -X POST http://localhost:8080/anime/add/550e8400-e29b-41d4-a716-446655440000 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{
    "title":"Attack on Titan",
    "status":"Watching",
    "progress":5,
    "genres":["Action","Fantasy"],
    "rating":8.5,
    "isFavourite":true
  }'
```

### Example: Get User's Watchlist

```bash
curl -X GET "http://localhost:8080/anime/list/550e8400-e29b-41d4-a716-446655440000" \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### Example: Update Anime Entry

```bash
curl -X PUT http://localhost:8080/anime/update/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{
    "id":1,
    "title":"Attack on Titan",
    "status":"Completed",
    "progress":75,
    "genres":["Action","Fantasy"],
    "rating":8.8,
    "favourite":true
  }'
```

## Frontend Integration

To connect this API with a frontend application:

1. Set your API base URL to `http://localhost:8080`
2. Include JWT token in Authorization header for protected endpoints:
   ```
   Authorization: Bearer <token_from_login>
   ```
3. Handle token validation using `/token/validate` endpoint
4. Refresh user data with `/users/me` endpoint

See this project's frontend: [animewatchlist](https://github.com/DavidOdulana4090/animewatchlist)


