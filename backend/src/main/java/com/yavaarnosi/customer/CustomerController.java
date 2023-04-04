package com.yavaarnosi.customer;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){

        this.customerService = customerService;

    }

    @CrossOrigin(origins = "*")
    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("{customerid}")
    public Customer getCustomers(@PathVariable("customerid") Integer customerId) {

        return customerService.getCustomer(customerId);

    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public void registerCustomer(@RequestBody CustomerRegistrationRequest request){

        customerService.addCustomer(request);

    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("{customerid}")
    public void deleteCustomer(@PathVariable("customerid") Integer customerId){

        customerService.deleteCustomerById(customerId);

    }

    @CrossOrigin(origins = "*")
    @PutMapping("{customerid}")
    public void updateCustomer(@RequestBody CustomerUpdateRequest request, @PathVariable("customerid") Integer customerId){

        customerService.updateCustomer(request, customerId);

    }

}
