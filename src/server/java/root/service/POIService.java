package server.java.root.service;


import org.springframework.stereotype.Service;
import root.model.city.POI;
import root.model.city.POIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import root.model.customer.Customer;
import root.model.customer.CustomerRepository;


import java.util.List;

@Service
public class POIService {

    private final POIRepository poiRepository;

    @Autowired
    public POIService(POIRepository poiRepository) {
        this.poiRepository = poiRepository;
    }

    public List<POI> getPOIs() {
        return poiRepository.findAll();
    }

}
