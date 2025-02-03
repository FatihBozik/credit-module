package com.fatihbozik.creditmodule.rest.controller;

import com.fatihbozik.creditmodule.dto.*;
import com.fatihbozik.creditmodule.service.CustomerAuthorizationService;
import com.fatihbozik.creditmodule.service.LoanService;
import com.fatihbozik.creditmodule.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
    private final LoanService loanService;
    private final PaymentService paymentService;
    private final CustomerAuthorizationService customerAuthorizationService;

    public LoanController(LoanService loanService,
                          PaymentService paymentService,
                          CustomerAuthorizationService customerAuthorizationService) {
        this.loanService = loanService;
        this.paymentService = paymentService;
        this.customerAuthorizationService = customerAuthorizationService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole(@roles.ADMIN, @roles.CUSTOMER)")
    public Loan createLoan(@RequestBody @Valid LoanCreateRequest createRequest) {
        customerAuthorizationService.throwIfCustomerIdMismatch(createRequest.getCustomerId());
        return loanService.createLoan(createRequest);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole(@roles.ADMIN, @roles.CUSTOMER)")
    public List<Loan> listLoans(LoanListQuery loanListQuery) {
        customerAuthorizationService.throwIfCustomerIdMismatch(loanListQuery.getCustomerId());
        return loanService.listLoans(loanListQuery);
    }

    @GetMapping("/{loanId}/installments")
    @PreAuthorize("hasAnyRole(@roles.ADMIN, @roles.CUSTOMER)")
    public List<LoanInstallment> listInstallments(@PathVariable Integer loanId) {
        customerAuthorizationService.throwIfLoanNotBelongToCustomer(loanId);
        return loanService.listInstallments(loanId);
    }

    @PostMapping("/{loanId}/pay")
    @PreAuthorize("hasAnyRole(@roles.ADMIN, @roles.CUSTOMER)")
    public PaymentResponse payLoan(@PathVariable Integer loanId,
                                   @RequestParam BigDecimal amount) {
        customerAuthorizationService.throwIfLoanNotBelongToCustomer(loanId);
        return paymentService.payLoan(loanId, amount);
    }
}
