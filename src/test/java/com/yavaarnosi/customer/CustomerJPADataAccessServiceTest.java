package com.yavaarnosi.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class CustomerJPADataAccessServiceTest {

    private CustomerJPADataAccessService underTest;
    private AutoCloseable autoCloseable;
    @Mock
    private CustomerRepository customerRepository;
    @BeforeEach
    void setUp() {

        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPADataAccessService(customerRepository);

    }

    @AfterEach
    void tearDown() throws Exception {

        autoCloseable.close();

    }

    @Test
    void selectAllCustomers() {

        // When
        underTest.selectAllCustomers();
        // Then
        verify(customerRepository)
                .findAll();

    }

    @Test
    void selectCustomerById() {
        // Given
        int id = 1;
        // When
        underTest.selectCustomerById(id);
        // Then
        verify(customerRepository)
                .findById(id);

    }

    @Test
    void insertCustomer() {
        // Given

        // When

        // Then
    }

    @Test
    void updateCustomer() {
        // Given

        // When

        // Then
    }

    @Test
    void deleteCustomerById() {
        // Given

        // When

        // Then
    }

    @Test
    void existsCustomerWithEmail() {
        // Given

        // When

        // Then
    }

    @Test
    void existsCustomerWithId() {
        // Given

        // When

        // Then
    }
}