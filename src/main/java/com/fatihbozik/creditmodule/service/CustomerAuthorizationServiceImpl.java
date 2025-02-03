package com.fatihbozik.creditmodule.service;

import com.fatihbozik.creditmodule.model.CustomerEntity;
import com.fatihbozik.creditmodule.model.LoanEntity;
import com.fatihbozik.creditmodule.model.UserEntity;
import com.fatihbozik.creditmodule.repository.LoanRepository;
import com.fatihbozik.creditmodule.util.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerAuthorizationServiceImpl implements CustomerAuthorizationService {
    private final LoanRepository loanRepository;

    @Override
    public void throwIfCustomerIdMismatch(Integer customerId) {
        if (customerId == null) {
            return;
        }
        final UserEntity currentUser = SecurityUtils.getCurrentUser();
        if (currentUser.hasAdmin()) {
            return;
        }
        final CustomerEntity customer = currentUser.getCustomer();
        if (customer == null || !customerId.equals(customer.getId())) {
            log.error("Customer ID mismatch. Expected: {}, Actual: {}", customerId, currentUser.getCustomer().getId());
            throw new AccessDeniedException("Customer ID mismatch");
        }
    }

    @Override
    public void throwIfLoanNotBelongToCustomer(Integer loanId) {
        if (loanId == null) {
            return;
        }
        final UserEntity currentUser = SecurityUtils.getCurrentUser();
        if (currentUser.hasAdmin()) {
            return;
        }

        LoanEntity loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new EntityNotFoundException("Loan not found."));
        CustomerEntity customer = currentUser.getCustomer();
        if (customer == null || !customer.getId().equals(loan.getCustomer().getId())) {
            throw new AccessDeniedException("Access denied: Loan does not belong to the customer.");
        }
    }
}
