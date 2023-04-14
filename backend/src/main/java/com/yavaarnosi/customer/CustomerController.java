package com.yavaarnosi.customer;

import com.yavaarnosi.jwt.JWTUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final JWTUtil jwtUtil;

    public CustomerController(CustomerService customerService, JWTUtil jwtUtil){

        this.customerService = customerService;
        this.jwtUtil = jwtUtil;

    }

    @GetMapping
    public List<CustomerDTO> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("{customerid}")
    public CustomerDTO getCustomers(@PathVariable("customerid") Integer customerId) {

        return customerService.getCustomer(customerId);

    }

    @PostMapping
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRegistrationRequest request){

        customerService.addCustomer(request);
        String jwtToken = jwtUtil.issueToken(request.email(), "ROLE_USER");

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .build();

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
