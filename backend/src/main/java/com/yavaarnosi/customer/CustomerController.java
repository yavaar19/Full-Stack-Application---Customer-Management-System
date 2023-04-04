package com.yavaarnosi.customer;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){

        this.customerService = customerService;

    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("{customerid}")
    public Customer getCustomers(@PathVariable("customerid") Integer customerId) {

        return customerService.getCustomer(customerId);

    }

    @PostMapping
    public void registerCustomer(@RequestBody CustomerRegistrationRequest request){

        customerService.addCustomer(request);

    }

    @DeleteMapping("{customerid}")
    public void deleteCustomer(@PathVariable("customerid") Integer customerId){

        customerService.deleteCustomerById(customerId);

    }

    @PutMapping("{customerid}")
    public void updateCustomer(@RequestBody CustomerUpdateRequest request, @PathVariable("customerid") Integer customerId){

        customerService.updateCustomer(request, customerId);

    }

}
