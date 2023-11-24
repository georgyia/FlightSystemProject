package server.java.root.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import root.model.city.City;
import root.model.customer.Customer;
import server.java.root.service.CityService;
import server.java.root.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("city")
public class CityResources {
    private final CityService cityService;

    @Autowired
    public CityResources(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public ResponseEntity<List<City>> getCities() {
        return ResponseEntity.ok(cityService.getCities());
    }

}
