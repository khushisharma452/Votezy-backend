# Votezy - Online Voting System

## Overview

Votezy is a secure Online Voting System developed using Spring Boot and MySQL. The application enables administrators to manage elections, candidates, and voters while providing a transparent and efficient voting process.

The project is designed using RESTful APIs and follows a layered architecture with Controllers, Services, Repositories, and Entity models.

---

## Features

### Voter Management

* Register new voters
* View voter details
* Update voter information
* Delete voter records

### Candidate Management

* Add candidates
* Update candidate information
* Delete candidates
* View all candidates

### Election Management

* Create elections
* Manage multiple elections
* View election details

### Voting System

* Cast votes securely
* One voter can vote only once per election
* Vote tracking and management

### Result Management

* Declare election results
* Calculate total votes
* Identify winning candidates

---

## Technology Stack

* Java 17
* Spring Boot
* Spring Data JPA
* Hibernate
* MySQL
* Maven
* Lombok
* REST APIs

---

## Project Structure

```text
src
├── controller
├── service
├── repository
├── entity
├── dto
├── exception
└── config
```

---

## API Modules

* Voter APIs
* Candidate APIs
* Election APIs
* Voting APIs
* Election Result APIs

---

## Database

MySQL is used for persistent data storage.

Main Entities:

* Voter
* Candidate
* Election
* Vote
* ElectionResult

---

## Installation

### Clone Repository

```bash
git clone https://github.com/your-username/Votezy-backend.git
```

### Configure Database

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/votezy_db
spring.datasource.username=root
spring.datasource.password=your_password
```

### Run Application

```bash
mvn spring-boot:run
```

Application runs on:

```text
http://localhost:8080
```

---

## Future Enhancements

* JWT Authentication
* Role Based Access Control
* Email Verification
* Election Scheduling
* Frontend Integration
* Cloud Deployment

---

## Author

Khushi Sharma

Backend Developer | Java & Spring Boot Enthusiast
