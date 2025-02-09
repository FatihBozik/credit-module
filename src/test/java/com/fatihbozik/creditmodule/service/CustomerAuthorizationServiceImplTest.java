package com.fatihbozik.creditmodule.service;

import com.fatihbozik.creditmodule.model.CustomerEntity;
import com.fatihbozik.creditmodule.model.LoanEntity;
import com.fatihbozik.creditmodule.model.UserEntity;
import com.fatihbozik.creditmodule.repository.LoanRepository;
import com.fatihbozik.creditmodule.util.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerAuthorizationServiceImplTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private CustomerAuthorizationServiceImpl customerAuthorizationService;

    @Mock
    private UserEntity currentUser;

    @Mock
    private CustomerEntity customer;

    @BeforeEach
    void setUp() {
        SecurityUtils.setCurrentUser(currentUser);
    }

    @Test
    void throwIfCustomerIdMismatch_shouldThrowAccessDeniedException_whenCustomerIdMismatch() {
        when(currentUser.hasAdmin()).thenReturn(false);
        when(currentUser.getCustomer()).thenReturn(customer);
        when(customer.getId()).thenReturn(1);

        assertThrows(AccessDeniedException.class, () -> customerAuthorizationService.throwIfCustomerIdMismatch(2));
    }

    @Test
    void throwIfCustomerIdMismatch_shouldNotThrowException_whenCustomerIdMatches() {
        when(currentUser.hasAdmin()).thenReturn(false);
        when(currentUser.getCustomer()).thenReturn(customer);
        when(customer.getId()).thenReturn(1);

        customerAuthorizationService.throwIfCustomerIdMismatch(1);
    }

    @Test
    void throwIfLoanNotBelongToCustomer_shouldThrowAccessDeniedException_whenLoanNotBelongToCustomer() {
        LoanEntity loan = mock(LoanEntity.class);
        CustomerEntity loanCustomer = mock(CustomerEntity.class);
        when(loanRepository.findById(1)).thenReturn(Optional.of(loan));
        when(loan.getCustomer()).thenReturn(loanCustomer);
        when(currentUser.hasAdmin()).thenReturn(false);
        when(currentUser.getCustomer()).thenReturn(customer);
        when(customer.getId()).thenReturn(1);
        when(loanCustomer.getId()).thenReturn(2);

        assertThrows(AccessDeniedException.class, () -> customerAuthorizationService.throwIfLoanNotBelongToCustomer(1));
    }

    @Test
    void throwIfLoanNotBelongToCustomer_shouldNotThrowException_whenLoanBelongsToCustomer() {
        LoanEntity loan = mock(LoanEntity.class);
        when(loanRepository.findById(1)).thenReturn(Optional.of(loan));
        when(loan.getCustomer()).thenReturn(customer);
        when(currentUser.hasAdmin()).thenReturn(false);
        when(currentUser.getCustomer()).thenReturn(customer);
        when(customer.getId()).thenReturn(1);
        when(loan.getCustomer().getId()).thenReturn(1);

        customerAuthorizationService.throwIfLoanNotBelongToCustomer(1);
    }

    @Test
    void throwIfLoanNotBelongToCustomer_shouldThrowEntityNotFoundException_whenLoanNotFound() {
        when(loanRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> customerAuthorizationService.throwIfLoanNotBelongToCustomer(1));
    }
}
