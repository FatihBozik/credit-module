package com.fatihbozik.creditmodule.service;

import com.fatihbozik.creditmodule.dto.PaymentResponse;
import com.fatihbozik.creditmodule.model.LoanEntity;
import com.fatihbozik.creditmodule.model.LoanInstallmentEntity;
import com.fatihbozik.creditmodule.repository.LoanInstallmentRepository;
import com.fatihbozik.creditmodule.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static com.fatihbozik.creditmodule.util.Constants.DISCOUNT_PENALTY_RATE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private LoanInstallmentRepository installmentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private LoanEntity loan;
    private LoanInstallmentEntity installment1;
    private LoanInstallmentEntity installment2;

    @BeforeEach
    void setUp() {
        loan = new LoanEntity();
        loan.setId(1);
        loan.setPaid(false);

        installment1 = new LoanInstallmentEntity();
        installment1.setId(1);
        installment1.setLoan(loan);
        installment1.setAmount(BigDecimal.valueOf(1000));
        installment1.setDueDate(LocalDate.now().plusMonths(1));
        installment1.setPaid(false);

        installment2 = new LoanInstallmentEntity();
        installment2.setId(2);
        installment2.setLoan(loan);
        installment2.setAmount(BigDecimal.valueOf(1000));
        installment2.setDueDate(LocalDate.now().plusMonths(2));
        installment2.setPaid(false);
    }

    @Test
    void payLoan_shouldPayInstallments_whenPaymentAmountIsSufficient() {
        when(loanRepository.findById(1)).thenReturn(Optional.of(loan));
        when(installmentRepository.findByLoanIdOrderByDueDateAsc(1)).thenReturn(List.of(installment1, installment2));

        // Calculate the expected effective amounts
        BigDecimal discount1 = installment1.getAmount()
                .multiply(DISCOUNT_PENALTY_RATE)
                .multiply(BigDecimal.valueOf(ChronoUnit.DAYS.between(LocalDate.now(), installment1.getDueDate())));
        BigDecimal effectiveAmount1 = installment1.getAmount().subtract(discount1);

        BigDecimal discount2 = installment2.getAmount()
                .multiply(DISCOUNT_PENALTY_RATE)
                .multiply(BigDecimal.valueOf(ChronoUnit.DAYS.between(LocalDate.now(), installment2.getDueDate())));
        BigDecimal effectiveAmount2 = installment2.getAmount().subtract(discount2);

        BigDecimal totalExpectedAmount = effectiveAmount1.add(effectiveAmount2);

        PaymentResponse response = paymentService.payLoan(1, BigDecimal.valueOf(2000));

        assertNotNull(response);
        assertEquals(2, response.getInstallmentsPaid());
        assertEquals(totalExpectedAmount, response.getTotalAmountSpent());
        assertTrue(response.isLoanPaid()); // Update the assertion to expect true
        verify(installmentRepository, times(2)).save(any(LoanInstallmentEntity.class));
    }

    @Test
    void payLoan_shouldNotPayInstallments_whenPaymentAmountIsInsufficient() {
        when(loanRepository.findById(1)).thenReturn(Optional.of(loan));
        when(installmentRepository.findByLoanIdOrderByDueDateAsc(1)).thenReturn(List.of(installment1, installment2));

        PaymentResponse response = paymentService.payLoan(1, BigDecimal.valueOf(500));

        assertNotNull(response);
        assertEquals(0, response.getInstallmentsPaid());
        assertEquals(BigDecimal.valueOf(0), response.getTotalAmountSpent());
        assertFalse(response.isLoanPaid());
        verify(installmentRepository, never()).save(any(LoanInstallmentEntity.class));
    }

    @Test
    void payLoan_shouldThrowException_whenLoanNotFound() {
        when(loanRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> paymentService.payLoan(1, BigDecimal.valueOf(2000)));
    }

    @Test
    void payLoan_shouldMarkLoanAsPaid_whenAllInstallmentsArePaid() {
        installment1.setPaid(true);
        when(loanRepository.findById(1)).thenReturn(Optional.of(loan));
        when(installmentRepository.findByLoanIdOrderByDueDateAsc(1)).thenReturn(List.of(installment1, installment2));

        // Calculate the expected effective amount for the second installment
        BigDecimal discount2 = installment2.getAmount()
                .multiply(DISCOUNT_PENALTY_RATE)
                .multiply(BigDecimal.valueOf(ChronoUnit.DAYS.between(LocalDate.now(), installment2.getDueDate())));
        BigDecimal effectiveAmount2 = installment2.getAmount().subtract(discount2);

        PaymentResponse response = paymentService.payLoan(1, BigDecimal.valueOf(1000));

        assertNotNull(response);
        assertEquals(1, response.getInstallmentsPaid());
        assertEquals(effectiveAmount2, response.getTotalAmountSpent());
        assertTrue(response.isLoanPaid());
        verify(loanRepository).save(loan);
    }
}
