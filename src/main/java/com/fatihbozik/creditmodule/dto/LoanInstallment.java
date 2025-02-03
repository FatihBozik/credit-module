package com.fatihbozik.creditmodule.dto;

import com.fatihbozik.creditmodule.model.LoanInstallmentEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.BooleanUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LoanInstallment {
    private BigDecimal amount;
    private BigDecimal paidAmount;
    private LocalDate dueDate;
    private LocalDate paymentDate;
    private boolean paid;

    public LoanInstallment(LoanInstallmentEntity entity) {
        this.amount = entity.getAmount();
        this.paidAmount = entity.getPaidAmount();
        this.dueDate = entity.getDueDate();
        this.paymentDate = entity.getPaymentDate();
        this.paid = BooleanUtils.isTrue(entity.getPaid());
    }
}
