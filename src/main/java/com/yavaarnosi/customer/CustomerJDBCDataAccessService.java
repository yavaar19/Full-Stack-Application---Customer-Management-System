package com.yavaarnosi.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDAO{

    private final JdbcTemplate jdbcTemplate;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> selectAllCustomers() {

        var sql = """
                SELECT id, name, email, age
                FROM customer
                """;

        jdbcTemplate.query(sql, (rs, rowNum) -> {

            Customer customer = new Customer(

                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("age")

                    );

            return customer;

        });

    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void insertCustomer(Customer customer) {

        var sql = """
                INSERT INTO customer(name, email, age)
                VALUES (?,?,?)
                """;

        jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getAge());

    }

    @Override
    public void updateCustomer(Customer update) {

    }

    @Override
    public void deleteCustomerById(Integer id) {

    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return false;
    }

    @Override
    public boolean existsPersonWithId(Integer id) {
        return false;
    }
}
