# StayEase

## Problem Statement
Develop and deploy a RESTful API service using Spring Boot to streamline the room booking process for a hotel management aggregator application. MySQL will be used to persist the data.

## Key Features
This is a simplified version of an online room booking system with the following assumptions:
- The application has a single type of room and all bookings are for two guests.
- Any hotel manager can update any hotel details.
- Another service handles check-in and check-out functionalities.
- The service must implement authentication and authorization using JWT tokens for session management.
- The service has three roles: CUSTOMER, HOTEL MANAGER, and ADMIN.
- The service has public and private API endpoints.

## Assumptions
- The application has a single type of room and all bookings are for two guests.
- Any hotel manager can update any hotel details without tracking who manages which hotel.
- Another service handles check-in and check-out functionalities.

## Roles and Access
- **CUSTOMER**: Can book rooms.
- **HOTEL MANAGER**: Can update hotel details and cancel bookings.
- **ADMIN**: Can create and delete hotels.

## API Endpoints
- **Public Endpoints**: Accessible by anyone (e.g., Registration, Login).
- **Private Endpoints**: Accessible by authenticated users (e.g., Book a room).

## Features
### User Registration and Login
- Users can register with their email, password, first name, last name, and role (default to CUSTOMER if not specified).
- Passwords are encrypted using BCrypt.
- A JWT token is generated upon successful registration or login.

### Hotel Management
- Manage hotel details including name, location, description, and number of available rooms.
- Number of available rooms indicates if a booking can be made.
- Browse all available hotels (Public endpoint).
- Only the administrator can create and delete hotels.
- Hotel managers can update hotel details.

### Booking Management
- Customers can book rooms.
- A single room can be booked per request.
- Only hotel managers can cancel bookings.

### Additional Requirements
- Use logs to log information and errors.
- Handle common errors gracefully and return appropriate HTTP codes (e.g., 404, User not found).
- Include basic unit tests with MockMvc and Mockito (minimum 3 tests).
- Publish code to a public GitHub repository with meaningful, incremental commit messages.
- Generate a JAR file and provide instructions on how to run it.
- (Optional) Create and add a public Postman Collection.

## Endpoints
- **POST /hotels/{hotelId}/book**: Make a booking.
- **DELETE /bookings/{bookingId}**: Cancel a booking.
- Other endpoints can be designed based on requirements.

### Postman Collection
- **Collection File:** [Download API Postman Collection](./docs/StayEase.postman_collection.json)

To import the collection into Postman:
1. **Download** the JSON file from the link above.
2. **Open Postman** and go to **File > Import**.
3. **Select** the downloaded JSON file and import it.

## Getting Started

### Prerequisites
- Java 11 or later
- MySQL
- Gradle

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/shivambhanu/StayEase.git
   cd StayEase
