package server.java.root.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("service")
public class ServiceResources {

//    private final CustomerService studentService;
//
//    @Autowired
//    public CustomerResources(CustomerService studentService) {
//        this.studentService = studentService;
//    }
//
//    @GetMapping
//    public List<Customer> getCustomers() {
//        return studentService.getCustomers();
//    }
//
//    @PostMapping
//    public void signUp(@RequestBody Customer customer)
//    {
//        studentService.addNewCustomer(customer);
//    }
//
//    @DeleteMapping(path = "{customerId}")
//    public void deleteCustomer(@PathVariable("customerId") Long customerId){
//        studentService.deleteCustomer(customerId);
//    }
//
//    @PutMapping(path = "{customerId}")
//    public void updateCustomer(
//            @PathVariable("customerId") Long customerId,
//            @RequestParam(required = false) String email,
//            @RequestParam(required = false) String password) {
//        studentService.updateCustomer(customerId, email, password);
//    }
}