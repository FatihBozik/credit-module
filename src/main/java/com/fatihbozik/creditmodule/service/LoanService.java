package com.fatihbozik.creditmodule.service;

import com.fatihbozik.creditmodule.dto.Loan;
import com.fatihbozik.creditmodule.dto.LoanCreateRequest;
import com.fatihbozik.creditmodule.dto.LoanInstallment;
import com.fatihbozik.creditmodule.dto.LoanListQuery;

import java.util.List;

public interface LoanService {
    Loan createLoan(LoanCreateRequest createRequest);

    List<Loan> listLoans(LoanListQuery loanListQuery);

    List<LoanInstallment> listInstallments(Integer loanId);
}
