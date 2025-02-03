package com.fatihbozik.creditmodule.service;

import com.fatihbozik.creditmodule.dto.Customer;
import com.fatihbozik.creditmodule.model.CustomerEntity;

public interface CustomerService {
    Customer getCustomerById(Integer customerId);

    CustomerEntity getCustomerEntityById(Integer customerId);
}
