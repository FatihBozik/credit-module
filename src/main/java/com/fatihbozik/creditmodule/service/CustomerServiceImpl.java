package com.fatihbozik.creditmodule.service;

import com.fatihbozik.creditmodule.dto.Customer;
import com.fatihbozik.creditmodule.model.CustomerEntity;
import com.fatihbozik.creditmodule.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public CustomerEntity getCustomerEntityById(Integer customerId) {
        return getCustomerEntity(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }

    @Override
    public Customer getCustomerById(Integer customerId) {
        return getCustomerEntity(customerId)
                .map(Customer::new)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }

    private Optional<CustomerEntity> getCustomerEntity(Integer customerId) {
        return customerRepository.findById(customerId);
    }
}
