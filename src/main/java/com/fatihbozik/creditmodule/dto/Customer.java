package com.fatihbozik.creditmodule.dto;

import com.fatihbozik.creditmodule.model.CustomerEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Customer {
    private String name;
    private String surname;
    private BigDecimal creditLimit;
    private BigDecimal usedCreditLimit;

    public Customer(CustomerEntity entity) {
        this.name = entity.getName();
        this.surname = entity.getSurname();
        this.creditLimit = entity.getCreditLimit();
        this.usedCreditLimit = entity.getUsedCreditLimit();
    }
}
