package server.java.root.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import root.model.city.POI;
import root.model.customer.Customer;
import server.java.root.service.CustomerService;
import server.java.root.service.POIService;

import java.util.List;

@RestController
@RequestMapping("poi")
public class POIResources {
    private final POIService poiService;

    @Autowired
    public POIResources(POIService poiService) {
        this.poiService = poiService;
    }

    @GetMapping
    public ResponseEntity<List<POI>> getPOI() {
        return ResponseEntity.ok(poiService.getPOIs());
    }

}
