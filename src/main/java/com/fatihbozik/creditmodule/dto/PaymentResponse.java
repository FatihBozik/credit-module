package com.fatihbozik.creditmodule.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class PaymentResponse {
    private int installmentsPaid;
    private BigDecimal totalAmountSpent;
    private boolean loanPaid;
}
