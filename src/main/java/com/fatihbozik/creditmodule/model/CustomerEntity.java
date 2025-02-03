package com.fatihbozik.creditmodule.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.style.ToStringCreator;

import java.math.BigDecimal;

/**
 * Simple JavaBean domain object representing a customer.
 */
@Getter
@Setter
@Entity
@Table(name = "customers")
public class CustomerEntity extends BaseEntity {

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "surname")
    private String surname;

    @NotNull
    @Column(name = "credit_limit")
    private BigDecimal creditLimit;

    @NotNull
    @Column(name = "used_credit_limit")
    private BigDecimal usedCreditLimit;

    @OneToOne
    @JoinColumn(name = "username", referencedColumnName = "username", unique = true, nullable = true)
    private UserEntity user;

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("id", this.getId())
                .append("new", this.isNew())
                .append("name", this.getName())
                .append("surname", this.getSurname())
                .append("creditLimit", this.getCreditLimit())
                .append("usedCreditLimit", this.getUsedCreditLimit())
                .append("username", this.getUser() == null ? null : this.getUser().getUsername())
                .toString();
    }
}
