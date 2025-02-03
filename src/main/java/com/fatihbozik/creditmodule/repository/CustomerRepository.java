package com.fatihbozik.creditmodule.repository;

import com.fatihbozik.creditmodule.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {
    // Optionally add queries by name etc.
}
