# WoodCraft Workshop

> **Status: Work in Progress** — This project is currently under active development.

A web application for browsing and ordering handcrafted wooden furniture (chairs, tables, and indoor staircases), built with Java and Spring Boot.

## Tech Stack
- **Backend:** Java, Spring Boot, Spring Security, Spring Data JPA
- **Frontend:** Thymeleaf, HTML5, CSS3, Bootstrap 5, JavaScript
- **Database:** MySQL

## Current Features
- Product catalog with categories (Chairs, Tables, Stairs)
- User authentication and role-based access (`ROLE_CLIENT`, `ROLE_ADMIN`)
- Order placement for registered users
- Admin dashboard for adding and deleting products with image upload

## Screenshots
![Home Page](screenshots/home.png)
![Catalog & Specifications](screenshots/catalog.png)

## How to Run Locally
1. Start MySQL (e.g., via XAMPP) on port `3306`.
2. Configure database credentials in `src/main/resources/application.properties`.
3. Run `AplicatieWebApplication.java` and open `http://localhost:8080` in your browser.
