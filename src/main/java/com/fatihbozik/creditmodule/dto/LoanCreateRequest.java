package com.fatihbozik.creditmodule.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

import static com.fatihbozik.creditmodule.util.Constants.ALLOWED_INSTALLMENTS;

@Getter
@Setter
@ToString
public class LoanCreateRequest {
    @NotNull(message = "Number of installments is required.")
    private Integer numberOfInstallments;

    @NotNull(message = "Interest rate is required.")
    @DecimalMin(value = "0.1", message = "Interest rate must be at least 0.1")
    @DecimalMax(value = "0.5", message = "Interest rate must be at most 0.5")
    private BigDecimal interestRate;

    @NotNull(message = "Customer id is required.")
    private Integer customerId;

    @NotNull(message = "Amount is required.")
    private BigDecimal amount;

    @AssertTrue(message = "Number of installments must be one of: 6, 9, 12, 24")
    public boolean isNumberOfInstallmentsValid() {
        return numberOfInstallments != null && ALLOWED_INSTALLMENTS.contains(numberOfInstallments);
    }
}
