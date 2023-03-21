package com.yavaarnosi.customer;

import com.yavaarnosi.exception.DeleteResourceNotFoundException;
import com.yavaarnosi.exception.DuplicateResourceException;
import com.yavaarnosi.exception.RequestValidationException;
import com.yavaarnosi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerService(@Qualifier("jdbc") CustomerDAO customerDAO){

        this.customerDAO = customerDAO;

    }

    public List<Customer> getAllCustomers(){

        return customerDAO.selectAllCustomers();

    }

    public Customer getCustomer(Integer id){

        return customerDAO.selectCustomerById(id)
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
                    customerRegistrationRequest.age()

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

        Customer customer = getCustomer(customerId);

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
