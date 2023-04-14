package com.yavaarnosi.customer;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CustomerDAO {

    List<Customer> selectAllCustomers();
    Optional<Customer> selectCustomerById(Integer id);
    void insertCustomer(Customer customer);
    void updateCustomer(Customer update);

    void deleteCustomerById(Integer id);
    boolean existsCustomerWithEmail(String email);
    boolean existsCustomerWithId(Integer id);
    Optional<Customer> selectUserByEmail(String email);



}
