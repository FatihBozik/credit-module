package com.fatihbozik.creditmodule.service;

import com.fatihbozik.creditmodule.dto.Customer;
import com.fatihbozik.creditmodule.model.CustomerEntity;
import com.fatihbozik.creditmodule.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private CustomerEntity customerEntity;

    @BeforeEach
    void setUp() {
        customerEntity = new CustomerEntity();
        customerEntity.setId(1);
        customerEntity.setName("John Doe");
    }

    @Test
    void getCustomerEntityById_shouldReturnCustomerEntity_whenCustomerExists() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customerEntity));

        CustomerEntity result = customerService.getCustomerEntityById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("John Doe", result.getName());
    }

    @Test
    void getCustomerEntityById_shouldThrowException_whenCustomerDoesNotExist() {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> customerService.getCustomerEntityById(1));
    }

    @Test
    void getCustomerById_shouldReturnCustomer_whenCustomerExists() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customerEntity));

        Customer result = customerService.getCustomerById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("John Doe", result.getName());
    }

    @Test
    void getCustomerById_shouldThrowException_whenCustomerDoesNotExist() {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> customerService.getCustomerById(1));
    }
}
