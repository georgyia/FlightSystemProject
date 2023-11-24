package server.java.root.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.model.city.City;
import root.model.city.CityRepository;
import root.model.customer.Customer;
import root.model.customer.CustomerRepository;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getCities() {
        return cityRepository.findAll();
    }
}
