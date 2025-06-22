# Booker Rest-assured | Automated API Testing Project

This project automates tests for [Restful-booker](https://restful-booker.herokuapp.com/apidoc/index.html) using Java, JUnit 5 and Rest-assured.

## Structure

- **src/test/java/TestAuth.java**  
    Handles authentication and generates the access token required for protected API operations.

- **src/test/java/TestBooking.java**  
    Contains tests for creating, updating and deleting bookings, using parameterized data from CSV files.

- **src/test/resources/csv/**  
    Contains CSV files with test data for bookings.

- **src/test/resources/json/userAuth.json**  
    Contains the authentication request body.

## How it works

1. **Authentication**  
    The `TestAuth` class generates an authentication token, which is required for update and delete operations.

2. **Booking Creation:**  
   The `testCreateBooking` test reads data from a CSV file and sends `POST /booking` requests, validating the response.

3. **Booking Update:**  
   The `testUpdateBooking` test uses the generated token, reads data from a CSV file, and sends `PUT /booking/{id}` requests, validating the response.

4. **Booking Deletion:**  
   The `testDeleteBooking` test deletes the created booking and validates the success status.

**Note:**  
The `POST /booking` endpoint returns booking data inside a `booking` object, while the `PUT /booking/{id}` endpoint returns the updated data directly in the response body. The tests handle these differences in their assertions.

## How to run

1. Configure the `userAuth.json` file with valid username and password.
2. Run the tests using Maven:

   ```
   mvn clean test
   ```

## Dependencies

- Maven
- JUnit 5
- RestAssured
- Gson

---

Project developed for learning purposes in API test automation. 
