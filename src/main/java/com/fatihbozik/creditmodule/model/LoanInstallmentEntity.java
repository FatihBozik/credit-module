package com.fatihbozik.creditmodule.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Simple JavaBean domain object representing a loan installment.
 */
@Getter
@Setter
@Entity
@Table(name = "loan_installments")
public class LoanInstallmentEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "loan_id")
    private LoanEntity loan;

    @NotNull
    @Column(name = "amount")
    private BigDecimal amount;

    @NotNull
    @Column(name = "paid_amount")
    private BigDecimal paidAmount = BigDecimal.ZERO;

    @NotNull
    @Column(name = "due_date", columnDefinition = "DATE")
    private LocalDate dueDate;

    @NotNull
    @Column(name = "payment_date", columnDefinition = "DATE")
    private LocalDate paymentDate;

    @NotNull
    @Column(name = "is_paid")
    private Boolean paid = false;
}
