package com.fatihbozik.creditmodule.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoanListQuery {
    private Integer customerId;
    private Integer numberOfInstallment;
    private Boolean paid;
}
