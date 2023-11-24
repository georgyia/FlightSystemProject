package server.java.root.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.model.city.City;
import root.model.flight.Flight;
import root.model.flight.FlightRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {//

    private final FlightRepository flightRepository;

    @Autowired
    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> getFlights() {
        return flightRepository.findAll();
    }

    public List<Flight> getSelectedFlights(LocalDate departureDate, City from, City to) {

        List<Flight> flights = getFlights();

        if (departureDate != null) {
            flights = getFlights().stream().filter(f -> departureDate.isEqual(f.getStartTime().toLocalDate())).collect(Collectors.toList());
        }

        if (from != null) {
            flights = getFlights().stream().filter(f -> from.equals(f.getFrom().getCity())).collect(Collectors.toList());
        }

        if (to != null) {
            flights = getFlights().stream().filter(f -> to.equals(f.getTo().getCity())).collect(Collectors.toList());
        }

        return flights;
    }
}
