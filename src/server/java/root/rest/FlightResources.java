package server.java.root.rest;

import org.springframework.http.ResponseEntity;
import root.model.city.City;
import root.model.customer.Customer;
import root.model.flight.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.java.root.service.FlightService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("flight")
public class FlightResources {
    private final FlightService flightService;

    @Autowired
    public FlightResources(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public ResponseEntity<List<Flight>> getFlights() {
        return ResponseEntity.ok(flightService.getFlights());
    }

    @GetMapping("/select")
    public List<Flight> getSelectedFlights(@RequestParam LocalDate departureDate, @RequestParam City from, @RequestParam City to) {
        return flightService.getSelectedFlights(departureDate, from, to);
    }

}
