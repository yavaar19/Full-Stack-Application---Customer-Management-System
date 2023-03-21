package com.yavaarnosi.customer;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

class CustomerRowMapperTest {

    @Mock
    private CustomerDAO customerDAO;
    private CustomerRowMapper underTest;

    @Test
    void mapRow() throws SQLException {
        // Given
        CustomerRowMapper customerRowMapper = new CustomerRowMapper();

        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("Jamila");
        when(resultSet.getString("email")).thenReturn("jamila@example.com");
        when(resultSet.getInt("age")).thenReturn(19);


        // When
        Customer actualCustomer = customerRowMapper.mapRow(resultSet, 1);

        // Then
        Customer expectedCustomer = new Customer(

                1, "Jamila", "jamila@example.com", 19

        );

        assertThat(actualCustomer).isEqualTo(expectedCustomer);

    }
}