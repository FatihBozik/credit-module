package com.fatihbozik.creditmodule.service;

import com.fatihbozik.creditmodule.dto.Loan;
import com.fatihbozik.creditmodule.dto.LoanCreateRequest;
import com.fatihbozik.creditmodule.dto.LoanInstallment;
import com.fatihbozik.creditmodule.dto.LoanListQuery;
import com.fatihbozik.creditmodule.model.CustomerEntity;
import com.fatihbozik.creditmodule.model.LoanEntity;
import com.fatihbozik.creditmodule.model.LoanInstallmentEntity;
import com.fatihbozik.creditmodule.repository.CustomerRepository;
import com.fatihbozik.creditmodule.repository.LoanInstallmentRepository;
import com.fatihbozik.creditmodule.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private LoanInstallmentRepository installmentRepository;

    @InjectMocks
    private LoanServiceImpl loanService;

    private CustomerEntity customerEntity;
    private LoanCreateRequest loanCreateRequest;

    @BeforeEach
    void setUp() {
        customerEntity = new CustomerEntity();
        customerEntity.setId(1);
        customerEntity.setCreditLimit(BigDecimal.valueOf(10000));
        customerEntity.setUsedCreditLimit(BigDecimal.valueOf(2000));

        loanCreateRequest = new LoanCreateRequest();
        loanCreateRequest.setCustomerId(1);
        loanCreateRequest.setAmount(BigDecimal.valueOf(3000));
        loanCreateRequest.setInterestRate(BigDecimal.valueOf(0.05));
        loanCreateRequest.setNumberOfInstallments(12);
    }

    @Test
    void createLoan_shouldCreateLoan_whenCustomerHasEnoughLimit() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customerEntity));
        when(loanRepository.save(any(LoanEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(installmentRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        Loan result = loanService.createLoan(loanCreateRequest);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(3000), result.getLoanAmount());
        verify(customerRepository).save(customerEntity);
    }

    @Test
    void createLoan_shouldThrowException_whenCustomerDoesNotHaveEnoughLimit() {
        customerEntity.setUsedCreditLimit(BigDecimal.valueOf(8000));
        when(customerRepository.findById(1)).thenReturn(Optional.of(customerEntity));

        assertThrows(IllegalArgumentException.class, () -> loanService.createLoan(loanCreateRequest));
    }

    @Test
    void listLoans_shouldReturnListOfLoans() {
        LoanListQuery loanListQuery = new LoanListQuery();
        LoanEntity loanEntity = new LoanEntity();
        loanEntity.setId(1);
        loanEntity.setCustomer(new CustomerEntity());
        loanEntity.setLoanAmount(BigDecimal.TEN);
        loanEntity.setInstallments(List.of(new LoanInstallmentEntity()));
        when(loanRepository.listLoans(loanListQuery)).thenReturn(List.of(loanEntity));

        List<Loan> result = loanService.listLoans(loanListQuery);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
    }

    @Test
    void listInstallments_shouldReturnListOfInstallments() {
        LoanInstallmentEntity installmentEntity = new LoanInstallmentEntity();
        installmentEntity.setId(1);
        when(installmentRepository.findByLoanIdOrderByDueDateAsc(1)).thenReturn(List.of(installmentEntity));

        List<LoanInstallment> result = loanService.listInstallments(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
    }
}
