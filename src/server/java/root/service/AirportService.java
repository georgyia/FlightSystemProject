package server.java.root.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.model.city.Airport;
import root.model.city.AirportRepository;


import java.util.List;

@Service
public class AirportService {
    private final AirportRepository airportRepository;

    @Autowired
    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public List<Airport> getAirports() {
        return airportRepository.findAll();
    }
}
