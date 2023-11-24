package server.java.root.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.model.city.POI;
import root.model.city.POIRepository;
import root.model.customer.Customer;
import root.model.customer.CustomerRepository;
import root.model.flight.FlightRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final POIRepository poiRepository;
    private final FlightRepository flightRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, POIRepository poiRepository, FlightRepository flightRepository) {
        this.customerRepository = customerRepository;
        this.poiRepository = poiRepository;
        this.flightRepository = flightRepository;
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer addNewCustomer(Customer customer) {
        Optional<Customer> customerOptional = customerRepository.findStudentByEmail(customer.getEmail());
        if (customerOptional.isPresent()){
            return null;
        }
        return customerRepository.save(customer);
    }

    public Customer authenticate(Customer customer) {
        Optional<Customer> customerOptional = customerRepository.findStudentByEmail(customer.getEmail());
        if (customerOptional.isPresent()&&customerOptional.get().getPassword().equals(customer.getPassword())){
            return customerOptional.get();
        }
        return null;
    }

    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Transactional
    public void updateCustomer(Long customerId, String email, String password) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new IllegalArgumentException("student with id does not exist"));

        if (email!=null && email.length() >0 && !Objects.equals(customer.getEmail(), email)){
            Optional<Customer> studentOptional = customerRepository.findStudentByEmail(email);
            if (studentOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }
            customer.setEmail(email);
        }

        if (password!=null && password.length()>0 && !Objects.equals(customer.getPassword(), password)){
            customer.setPassword(password);
        }
    }

    @Transactional
    public Customer addPOI(Long user_id, Long poi_id) {
        customerRepository.findById(user_id).get().addPOI(poiRepository.findById(poi_id).get());
        return customerRepository.findById(user_id).get();
    }

    public Set<POI> getPOI(Long user_id) {
        return customerRepository.findById(user_id).get().getFavoritePOI();
    }

    @Transactional
    public Customer addFlight(Long user_id, Long flight_id) {
        customerRepository.findById(user_id).get().addFlight(flightRepository.findById(flight_id).get());
        return customerRepository.findById(user_id).get();
    }
}
