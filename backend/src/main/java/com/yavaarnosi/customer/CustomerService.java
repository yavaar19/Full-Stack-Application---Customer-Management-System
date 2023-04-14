package com.yavaarnosi.customer;

import com.yavaarnosi.exception.DeleteResourceNotFoundException;
import com.yavaarnosi.exception.DuplicateResourceException;
import com.yavaarnosi.exception.RequestValidationException;
import com.yavaarnosi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerDAO customerDAO;
    private final CustomerDTOMapper customerDTOMapper;
    private final PasswordEncoder passwordEncoder;


    public CustomerService(@Qualifier("jdbc") CustomerDAO customerDAO, CustomerDTOMapper customerDTOMapper, PasswordEncoder passwordEncoder){

        this.customerDAO = customerDAO;
        this.customerDTOMapper = customerDTOMapper;
        this.passwordEncoder = passwordEncoder;

    }

    public List<CustomerDTO> getAllCustomers(){

        return customerDAO.selectAllCustomers().stream().map(customerDTOMapper).collect(Collectors.toList());

    }

    public CustomerDTO getCustomer(Integer id){

        return customerDAO.selectCustomerById(id)
                .map(customerDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer with id [%s] not found".formatted(id)
                ));

    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest){

        String email = customerRegistrationRequest.email();

        if (customerDAO.existsCustomerWithEmail(email)){

            throw new DuplicateResourceException("Email already taken!");

        }else {

            Customer customer = new Customer(

                    customerRegistrationRequest.name(),
                    customerRegistrationRequest.email(),
                    passwordEncoder.encode(customerRegistrationRequest.password()),
                    customerRegistrationRequest.age(),
                    customerRegistrationRequest.gender()

            );

            customerDAO.insertCustomer(customer);

        }

    }

    public void deleteCustomerById(Integer id) {

        if (customerDAO.existsCustomerWithId(id)) {

            customerDAO.deleteCustomerById(id);

        } else {

            throw new DeleteResourceNotFoundException("Customer with given ID not found!");

        }

    }

    public void updateCustomer(CustomerUpdateRequest updateRequest, Integer customerId){

        Customer customer = customerDAO.selectCustomerById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer with id [%s] not found".formatted(customerId)
                ));

        boolean changes = false;

        if (updateRequest.name() !=null && !updateRequest.name().equals(customer.getName())){

            customer.setName(updateRequest.name());
            changes = true;

        }

        if (updateRequest.age() !=null && !updateRequest.age().equals(customer.getAge())){

            customer.setAge(updateRequest.age());
            changes = true;

        }

        if (updateRequest.email() !=null && !updateRequest.email().equals(customer.getEmail())){

            if (customerDAO.existsCustomerWithEmail(updateRequest.email())){

                throw new DuplicateResourceException(

                    "Email already taken!"

                );

            }

            customer.setEmail(updateRequest.email());
            changes = true;

        }

        if(!changes){

            throw new RequestValidationException(

                    "No data changes found!"

            );

        }

        customerDAO.updateCustomer(customer);

    }

}
