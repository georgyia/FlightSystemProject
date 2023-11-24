package server.java.root.rest;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import root.model.customer.Customer;
import server.java.root.service.CustomerService;

import java.util.List;


@RestController
@RequestMapping("customer")
public class CustomerResources {
    private final CustomerService customerService;

    @Autowired
    public CustomerResources(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok(customerService.getCustomers());
    }

    @PostMapping("/signUp")
    public ResponseEntity<Customer> signUp(@RequestBody Customer customer)
    {
        Customer returnCustomer = customerService.addNewCustomer(customer);
        System.out.println(returnCustomer);
        if (returnCustomer==null) return ResponseEntity.badRequest().build();
        else return ResponseEntity.ok(returnCustomer);
    }

    @PostMapping("/signIn")
    public ResponseEntity<Customer> signIn(@RequestBody Customer customer)
    {
        Customer returnCustomer = customerService.authenticate(customer);
        System.out.println(returnCustomer);
        if (returnCustomer==null) return ResponseEntity.badRequest().build();
        else return ResponseEntity.ok(returnCustomer);
    }

    @DeleteMapping(path = "{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Long customerId){
        customerService.deleteCustomer(customerId);
    }

    @PutMapping(path = "{customerId}")
    public void updateCustomer(
            @PathVariable("customerId") Long customerId,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password) {
        customerService.updateCustomer(customerId, email, password);
    }

    @PutMapping(path = "addPOI")
    public ResponseEntity<Customer> addPOI(
            @RequestParam Long user_id,
            @RequestParam Long poi_id) {
        Customer returnCustomer = customerService.addPOI(user_id, poi_id);
        if (returnCustomer==null) return ResponseEntity.badRequest().build();
        else return ResponseEntity.ok(returnCustomer);
    }

    @PutMapping(path = "addFlight")
    public ResponseEntity<Customer> addFlight(
            @RequestParam Long user_id,
            @RequestParam Long flight_id) {
        Customer returnCustomer = customerService.addFlight(user_id, flight_id);
        if (returnCustomer==null) return ResponseEntity.badRequest().build();
        else return ResponseEntity.ok(returnCustomer);
    }
}
