package com.fatihbozik.creditmodule.service;

public interface CustomerAuthorizationService {
    void throwIfCustomerIdMismatch(Integer customerId);

    void throwIfLoanNotBelongToCustomer(Integer loanId);
}
