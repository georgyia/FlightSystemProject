# FlightSystemProject

This project is a university assignment for a flight system application that includes server, client, and common modules. It is designed to manage customers, flights, cities, points of interest (POIs), and airports. Despite not being 100% complete, the project received a high grade of over 95%.
Project Structure

The project is divided into several packages and classes, each with its own responsibility.

## Server Module

- @StartServer.java: This is the entry point of the server module.
- @ConfigAll.java: This class is responsible for the initial configuration of the application.
- @AirportService.java, @StaffService.java, @CityService.java, @POIService.java, @FlightService.java, @ServiceService.java, @CustomerService.java: These are service classes that handle business logic related to their respective domains.
- @CityResources.java, @AirportResources.java, @StaffResources.java, @FlightResources.java, @CustomerResources.java, @POIResources.java, @ServiceResources.java: These are REST controllers that expose APIs related to their respective domains.

## Client Module

- @FlightSystemClientApplication.java: This is the entry point of the client module. It manages the application scenes.
- @StaffController.java, @CustomerController.java, @POIController.java, @CityController.java, @AirportController.java, @FlightController.java, @ServiceController.java: These are controller classes that handle user interactions related to their respective domains.
- @StartScene.java, @FlightScene.java, @POIScene.java, @HomeScene.java: These classes represent different scenes in the application's user interface.

## Common Module

- @JSONResult.java: This class is used for handling JSON results.
- @POI.java, @City.java, @Airport.java, @PlaneSeat.java, @Service.java, @Staff.java, @Flight.java, @Customer.java: These are model classes that represent different entities in the application.
- @POIRepository.java, @CityRepository.java, @AirportRepository.java, @FlightRepository.java, @ServiceRepository.java, @PlaneSeatRepository.java, @CustomerRepository.java: These are repository interfaces for accessing data related to their respective domains.

## Test Module

- @CustomerTest.java: This class contains tests for the Customer class.

## Running the Project

To run the project, you need to start both the server and client modules. The server module can be started by running the @StartServer.java class, and the client module can be started by running the @FlightSystemClientApplication.java class.

## Academic Achievement

This project was submitted as a university assignment and received a high grade of over 95%, demonstrating a high level of understanding and application of programming concepts, despite not being fully completed.
