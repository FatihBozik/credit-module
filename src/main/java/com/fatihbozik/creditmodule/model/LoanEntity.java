package com.fatihbozik.creditmodule.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Simple JavaBean domain object representing a loan.
 */
@Getter
@Setter
@Entity
@Table(name = "loans")
public class LoanEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @NotNull
    @Column(name = "loan_amount")
    private BigDecimal loanAmount;

    @NotNull
    @Column(name = "number_of_installment")
    private Integer numberOfInstallment;

    @NotNull
    @Column(name = "create_date", columnDefinition = "DATE")
    private LocalDate createDate;

    @NotNull
    @Column(name = "is_paid")
    private Boolean paid = false;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
    private List<LoanInstallmentEntity> installments;
}
