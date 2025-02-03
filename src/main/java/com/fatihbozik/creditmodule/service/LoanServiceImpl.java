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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {
    private final CustomerRepository customerRepository;
    private final LoanRepository loanRepository;
    private final LoanInstallmentRepository installmentRepository;

    public LoanServiceImpl(CustomerRepository customerRepository,
                           LoanRepository loanRepository,
                           LoanInstallmentRepository installmentRepository) {
        this.customerRepository = customerRepository;
        this.loanRepository = loanRepository;
        this.installmentRepository = installmentRepository;
    }

    @Override
    @Transactional
    public Loan createLoan(LoanCreateRequest request) {
        CustomerEntity customerEntity = getCustomerEntity(request);
        BigDecimal totalLoanAmount = calculateTotalLoanAmount(request.getAmount(), request.getInterestRate());
        checkIfCustomerHasEnoughLimit(customerEntity, totalLoanAmount);

        LoanEntity loan = createLoan(request, customerEntity);
        loan = loanRepository.save(loan);

        BigDecimal installmentAmount = calculateInstallmentAmount(totalLoanAmount, request.getNumberOfInstallments());
        List<LoanInstallmentEntity> installments = createInstallments(request.getNumberOfInstallments(), loan, installmentAmount);
        installmentRepository.saveAll(installments);

        customerEntity.setUsedCreditLimit(customerEntity.getUsedCreditLimit().add(request.getAmount()));
        customerRepository.save(customerEntity);

        return new Loan(loan);
    }

    @Transactional(readOnly = true)
    public List<Loan> listLoans(LoanListQuery loanListQuery) {
        return loanRepository.listLoans(loanListQuery)
                .stream()
                .map(Loan::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<LoanInstallment> listInstallments(Integer loanId) {
        return installmentRepository.findByLoanIdOrderByDueDateAsc(loanId)
                .stream()
                .map(LoanInstallment::new)
                .collect(Collectors.toList());
    }

    private BigDecimal calculateTotalLoanAmount(BigDecimal amount, BigDecimal interestRate) {
        return amount.multiply(BigDecimal.ONE.add(interestRate));
    }

    private BigDecimal calculateInstallmentAmount(BigDecimal totalLoanAmount, Integer numberOfInstallments) {
        return totalLoanAmount.divide(BigDecimal.valueOf(numberOfInstallments), 2, RoundingMode.HALF_UP);
    }

    private LoanEntity createLoan(LoanCreateRequest request, CustomerEntity customerEntity) {
        LoanEntity loan = new LoanEntity();
        loan.setCustomer(customerEntity);
        loan.setLoanAmount(request.getAmount());
        loan.setNumberOfInstallment(request.getNumberOfInstallments());
        loan.setCreateDate(LocalDate.now());
        loan.setPaid(false);
        return loan;
    }

    private List<LoanInstallmentEntity> createInstallments(Integer numberOfInstallments, LoanEntity loan, BigDecimal installmentAmount) {
        LocalDate firstDueDate = LocalDate.now().plusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
        List<LoanInstallmentEntity> installments = new ArrayList<>();
        for (int i = 0; i < numberOfInstallments; i++) {
            LoanInstallmentEntity installment = createLoanInstallment(loan, installmentAmount, firstDueDate, i);
            installments.add(installment);
        }
        return installments;
    }

    private LoanInstallmentEntity createLoanInstallment(LoanEntity loan, BigDecimal installmentAmount,
                                                        LocalDate firstDueDate, int i) {
        LoanInstallmentEntity installment = new LoanInstallmentEntity();
        installment.setLoan(loan);
        installment.setAmount(installmentAmount);
        installment.setPaidAmount(BigDecimal.ZERO);
        installment.setDueDate(firstDueDate.plusMonths(i));
        installment.setPaid(false);
        return installment;
    }

    private CustomerEntity getCustomerEntity(LoanCreateRequest request) {
        return customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }

    private void checkIfCustomerHasEnoughLimit(CustomerEntity customerEntity, BigDecimal totalLoanAmount) {
        BigDecimal creditLimit = customerEntity.getCreditLimit();
        BigDecimal usedCreditLimit = customerEntity.getUsedCreditLimit();
        BigDecimal availableCredit = creditLimit.subtract(usedCreditLimit);
        if (availableCredit.compareTo(totalLoanAmount) < 0) {
            throw new IllegalArgumentException("Insufficient credit limit");
        }
    }
}
