package com.yavaarnosi.customer;

import com.yavaarnosi.exception.DeleteResourceNotFoundException;
import com.yavaarnosi.exception.DuplicateResourceException;
import com.yavaarnosi.exception.RequestValidationException;
import com.yavaarnosi.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerDAO customerDAO;
    private CustomerService underTest;
    @BeforeEach
    void setUp() {

        underTest = new CustomerService(customerDAO);

    }

    @Test
    void getAllCustomers() {
        // When
        underTest.getAllCustomers();
        // Then
        verify(customerDAO).selectAllCustomers();

    }

    @Test
    void canGetCustomer() {
        // Given
        int id = 10;

        Customer customer = new Customer(

                id, "alex", "alex@example.com", 19

        );

        Mockito.when(customerDAO.selectCustomerById(id)).thenReturn(Optional.of(customer));

        // When
        Customer actual = underTest.getCustomer(id);

        // Then
        assertThat(actual).isEqualTo(customer);

    }

    @Test
    void willThrowWhenGetCustomerReturnEmptyOptional() {
        // Given
        int id = 10;

        Mockito.when(customerDAO.selectCustomerById(id)).thenReturn(Optional.empty());

        // When
        // Then
        assertThatThrownBy(() -> underTest.getCustomer(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Customer with id [%s] not found".formatted(id));

    }

    @Test
    void addCustomer() {
        // Given
        String email = "alex@example.com";

        when(customerDAO.existsCustomerWithEmail(email)).thenReturn(false);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "alex", email, 19

        );

        // When
        underTest.addCustomer(request);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDAO).insertCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getId()).isNull();
        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(request.age());

    }

    @Test
    void willThrowWhenEmailExistsWhileAddingACustomer() {
        // Given
        String email = "alex@example.com";

        when(customerDAO.existsCustomerWithEmail(email)).thenReturn(true);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "Alex", email, 19

        );

        // When
        assertThatThrownBy(() -> underTest.addCustomer(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Email already taken!");

        // Then
        verify(customerDAO, never()).insertCustomer(any());

    }

    @Test
    void deleteCustomerById() {
        // Given
        int id = 10;

        when(customerDAO.existsCustomerWithId(id)).thenReturn(true);

        // When
        underTest.deleteCustomerById(id);

        // Then
        verify(customerDAO).deleteCustomerById(id);
    }

    @Test
    void willThrowDeleteCustomerByIdNotExist() {
        // Given
        int id = 10;

        when(customerDAO.existsCustomerWithId(id)).thenReturn(false);

        // When
        assertThatThrownBy(() -> underTest.deleteCustomerById(id))
                .isInstanceOf(DeleteResourceNotFoundException.class)
                        .hasMessage("Customer with given ID not found!");

        // Then
        verify(customerDAO, never()).deleteCustomerById(id);

    }

    @Test
    void canUpdateAllCustomerProperties() {
        // Given
        int id = 10;

        Customer customer = new Customer(

                id, "Alex", "alex@example.com", 19

        );

        when(customerDAO.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail = "alexandro@example.com";

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(

                "Alexandro", newEmail, 23

        );

        when(customerDAO.existsCustomerWithEmail(newEmail)).thenReturn(false);

        // When
        underTest.updateCustomer(updateRequest, id);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);

        verify(customerDAO).updateCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(updateRequest.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.age());

    }

    @Test
    void canUpdateOnlyCustomerName() {
        // Given
        int id = 10;

        Customer customer = new Customer(

                id, "alex", "alex@example.com", 19

        );

        Mockito.when(customerDAO.selectCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                "Alexandro", null, null);

        // When
        underTest.updateCustomer(updateRequest, id);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);

        verify(customerDAO).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());

    }

    @Test
    void canUpdateOnlyCustomerEmail() {
        // Given
        int id = 10;

        Customer customer = new Customer(

                id, "alex", "alex@example.com", 19

        );

        Mockito.when(customerDAO.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail = "alexandro@example.com";

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                null, newEmail, null);

        when(customerDAO.existsCustomerWithEmail(newEmail)).thenReturn(false);

        // When
        underTest.updateCustomer(updateRequest, id);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);

        verify(customerDAO).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
        assertThat(capturedCustomer.getEmail()).isEqualTo(updateRequest.email());

    }

    @Test
    void canUpdateOnlyCustomerAge() {
        // Given
        int id = 10;

        Customer customer = new Customer(

                id, "alex", "alex@example.com", 19

        );

        when(customerDAO.selectCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                null, null, 33);

        // When
        underTest.updateCustomer(updateRequest, id);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);

        verify(customerDAO).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.age());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());

    }

    @Test
    void willThrowWhenTryingToUpdateCustomerEmailWhenAlreadyTaken() {
        // Given
        int id = 10;

        Customer customer = new Customer(

                id, "alex", "alex@example.com", 19

        );

        Mockito.when(customerDAO.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail = "alexandro@example.com";

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                null, newEmail, null);

        when(customerDAO.existsCustomerWithEmail(newEmail)).thenReturn(true);

        // When
        assertThatThrownBy(() -> underTest.updateCustomer(updateRequest, id))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Email already taken!");

        // Then

        verify(customerDAO, never()).updateCustomer(any());

    }

    @Test
    void willThrowWhenCustomerUpdateHasNoChanges() {
        // Given
        int id = 10;

        Customer customer = new Customer(

                id, "Alex", "alex@example.com", 19

        );

        when(customerDAO.selectCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(

                customer.getName(), customer.getEmail(), customer.getAge()

        );

        // When
        assertThatThrownBy(() -> underTest.updateCustomer(updateRequest, id))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("No data changes found!");

        // Then
        verify(customerDAO, never()).updateCustomer(any());

    }

}