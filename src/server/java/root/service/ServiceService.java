package server.java.root.service;

import root.model.flight.Service;
import root.model.flight.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;


public class ServiceService {

    private final ServiceRepository serviceRepository;

    @Autowired
    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    public void saveService(Service s){
        serviceRepository.save(s);
    }
    // TODO: update

}

