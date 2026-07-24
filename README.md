# 🏨 Cozy Stay – Hotel Reservation System

A Spring Boot backend application for managing hotel operations — customers, rooms, and reservations — built as an in-house training project.

## 📋 About

Cozy Stay is a hotel reservation management system that handles the core operations of a hotel: registering customers, managing room inventory, and booking/canceling reservations. It automatically calculates total price based on room rate and stay duration, and manages room status transitions (Available → Booked → Available) as reservations are made or cancelled.

## ✨ Features

- **Customer Management** – Add, update, view, and delete customer records
- **Room Management** – Track room number, type, price per night, capacity, and status
- **Reservation Management** – Book rooms for a customer with check-in/check-out dates
- **Automatic Price Calculation** – Total price computed from price-per-night × number of nights
- **Automatic Room Status Updates** – Rooms are marked `BOOKED` on reservation and `AVAILABLE` again on cancellation/checkout
- **Web UI** – A Thymeleaf-based frontend for interacting with the system through the browser
- **REST API** – Full set of endpoints for programmatic access

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.1.0 |
| Data Access | Spring Data JPA / Hibernate |
| Database | MySQL |
| Build Tool | Maven |
| Boilerplate | Lombok |
| Frontend | Thymeleaf, HTML, CSS, JavaScript |

## 🗂️ Project Structure

```
src/main/java/org/cozy/HotelReservationSystem/
├── model/          # JPA entities: Customer, Room, Reservation
├── repository/     # Spring Data JPA repositories
├── service/        # Business logic layer
├── controller/     # REST controllers + web controller
└── HotelResevationSystemApplication.java
src/main/resources/
├── application.properties.example   # Template for DB config
└── templates/                       # Thymeleaf HTML views
```

## ⚙️ Getting Started

### Prerequisites

- Java 21 (JDK)
- Maven 3.9+
- MySQL 8+ (running locally or remotely)

### 1. Clone the repository

```bash
git clone https://github.com/alok4041/HotelReservationSystem.git
cd HotelReservationSystem
```

### 2. Configure the database

Copy the example config and fill in your own MySQL credentials:

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

Edit `application.properties` with your local settings:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hotel_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

> Create the database first: `CREATE DATABASE hotel_db;`

### 3. Build and run

```bash
mvn clean install
mvn spring-boot:run
```

The application will start on **http://localhost:8080**

## 🔌 API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/customers` | List all customers |
| POST | `/api/customers` | Create a customer |
| GET | `/api/customers/{id}` | Get customer by ID |
| PUT | `/api/customers/{id}` | Update customer |
| DELETE | `/api/customers/{id}` | Delete customer |
| GET | `/api/rooms` | List all rooms |
| POST | `/api/rooms` | Add a room |
| PUT | `/api/rooms/{id}` | Update room details |
| GET | `/api/reservations` | List all reservations |
| POST | `/api/reservations` | Create a reservation |
| PUT | `/api/reservations/{id}/cancel` | Cancel a reservation |

*(Adjust the table above to match your actual controller mappings.)*

## 🖥️ Web Interface

Besides the REST API, the app ships with a simple Thymeleaf-based web UI for browsing rooms and making reservations directly from the browser, available at `/`.

## 🔒 Security Note

Database credentials are kept out of version control. `application.properties` is git-ignored — use `application.properties.example` as a template and never commit real credentials.

## 📄 License

This project was built for educational/training purposes as part of an in-house training program.
