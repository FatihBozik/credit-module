package com.fatihbozik.creditmodule.service;

import com.fatihbozik.creditmodule.dto.PaymentResponse;

import java.math.BigDecimal;

public interface PaymentService {
    PaymentResponse payLoan(Integer loanId, BigDecimal amount);
}
