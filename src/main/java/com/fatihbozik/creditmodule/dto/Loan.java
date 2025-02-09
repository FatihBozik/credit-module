package com.fatihbozik.creditmodule.dto;

import com.fatihbozik.creditmodule.model.LoanEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Loan {
    private Integer id;
    private Customer customer;
    private BigDecimal loanAmount;
    private Integer numberOfInstallment;
    private LocalDate createDate;
    private boolean paid = false;
    private List<LoanInstallment> installments;

    public Loan(LoanEntity entity) {
        this.id = entity.getId();
        this.customer = new Customer(entity.getCustomer());
        this.loanAmount = entity.getLoanAmount();
        this.numberOfInstallment = entity.getNumberOfInstallment();
        this.createDate = entity.getCreateDate();
        this.paid = BooleanUtils.isTrue(entity.getPaid());
        this.installments = getInstallments(entity);
    }

    private List<LoanInstallment> getInstallments(LoanEntity entity) {
        return CollectionUtils.isEmpty(entity.getInstallments()) ? new ArrayList<>()
                : entity.getInstallments().stream().map(LoanInstallment::new).toList();
    }
}
