package server.java.root.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import root.model.city.Airport;
import server.java.root.service.AirportService;

import java.util.List;

@RestController
@RequestMapping("airport")
public class AirportResources {
    private final AirportService airportService;

    @Autowired
    public AirportResources(AirportService airportService) {
        this.airportService = airportService;
    }


    @GetMapping
    public ResponseEntity<List<Airport>> getAirports() {
        return ResponseEntity.ok(airportService.getAirports());
    }

}

