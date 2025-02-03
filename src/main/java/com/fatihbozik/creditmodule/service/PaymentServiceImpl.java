package com.fatihbozik.creditmodule.service;

import com.fatihbozik.creditmodule.dto.PaymentResponse;
import com.fatihbozik.creditmodule.model.LoanEntity;
import com.fatihbozik.creditmodule.model.LoanInstallmentEntity;
import com.fatihbozik.creditmodule.repository.LoanInstallmentRepository;
import com.fatihbozik.creditmodule.repository.LoanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.fatihbozik.creditmodule.util.Constants.DISCOUNT_PENALTY_RATE;
import static com.fatihbozik.creditmodule.util.Constants.INSTALLMENT_PAYMENT_WINDOW_MONTHS;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final LoanRepository loanRepository;
    private final LoanInstallmentRepository installmentRepository;

    public PaymentServiceImpl(LoanRepository loanRepository, LoanInstallmentRepository installmentRepository) {
        this.loanRepository = loanRepository;
        this.installmentRepository = installmentRepository;
    }

    @Transactional
    public PaymentResponse payLoan(Integer loanId, BigDecimal paymentAmount) {
        LoanEntity loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));

        List<LoanInstallmentEntity> installments = installmentRepository.findByLoanIdOrderByDueDateAsc(loanId);
        int installmentsPaid = 0;
        BigDecimal totalSpent = BigDecimal.ZERO;

        LocalDate today = LocalDate.now();
        // Only pay installments due within the next 3 calendar months.
        LocalDate maxDueDate = today.plusMonths(INSTALLMENT_PAYMENT_WINDOW_MONTHS)
                .withDayOfMonth(1)
                .plusMonths(1)
                .minusDays(1);

        for (LoanInstallmentEntity installment : installments) {
            if (installment.getPaid() || installment.getDueDate().isAfter(maxDueDate)) {
                continue;
            }
            // Determine the effective installment amount with bonus/penalty logic
            BigDecimal effectiveAmount = installment.getAmount();

            long daysDiff = ChronoUnit.DAYS.between(today, installment.getDueDate());
            if (daysDiff > 0) {
                // Payment before due date => discount
                BigDecimal discount = installment.getAmount()
                        .multiply(DISCOUNT_PENALTY_RATE)
                        .multiply(BigDecimal.valueOf(daysDiff));
                effectiveAmount = effectiveAmount.subtract(discount);
            } else if (daysDiff < 0) {
                // Payment after due date => penalty
                long daysLate = Math.abs(daysDiff);
                BigDecimal penalty = installment.getAmount()
                        .multiply(DISCOUNT_PENALTY_RATE)
                        .multiply(BigDecimal.valueOf(daysLate));
                effectiveAmount = effectiveAmount.add(penalty);
            }
            // Only pay if we have enough money to cover this installment wholly.
            if (paymentAmount.compareTo(effectiveAmount) >= 0) {
                paymentAmount = paymentAmount.subtract(effectiveAmount);
                totalSpent = totalSpent.add(effectiveAmount);
                installment.setPaidAmount(effectiveAmount);
                installment.setPaymentDate(today);
                installment.setPaid(true);
                installmentRepository.save(installment);
                installmentsPaid++;
            } else {
                // Not enough to pay the next installment fully.
                break;
            }
        }

        // Check if all installments are paid.
        boolean loanPaid = installments.stream().allMatch(LoanInstallmentEntity::getPaid);
        if (loanPaid) {
            loan.setPaid(true);
            loanRepository.save(loan);
        }

        PaymentResponse response = new PaymentResponse();
        response.setInstallmentsPaid(installmentsPaid);
        response.setTotalAmountSpent(totalSpent);
        response.setLoanPaid(loanPaid);
        return response;
    }
}
