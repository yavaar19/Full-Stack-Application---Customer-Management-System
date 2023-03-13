package com.yavaarnosi.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerListDataAccessService implements CustomerDAO{
    private static List<Customer> customers;

    static {

        customers = new ArrayList<>();
        Customer alex = new Customer(1, "Alex", "alex@gmail.com", 21);
        Customer jamila = new Customer(2, "Jamila", "jamila@gmail.com", 19);

        customers.add(alex);
        customers.add(jamila);
    }

    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {

        return customers.stream().filter(c -> c.getId().equals((id))).findFirst();

    }

    @Override
    public void insertCustomer(Customer customer) {

        customers.add(customer);

    }

    @Override
    public void updateCustomer(Customer update) {

        customers.add(update);

    }

    @Override
    public void deleteCustomerById(Integer id) {

        customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .ifPresent(customers::remove);

    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        return customers.stream()
                .anyMatch(c -> c.getEmail().equals(email));
    }

    @Override
    public boolean existsCustomerWithId(Integer id) {

        return customers.stream().anyMatch(c -> c.getId().equals(id));

    }
}
