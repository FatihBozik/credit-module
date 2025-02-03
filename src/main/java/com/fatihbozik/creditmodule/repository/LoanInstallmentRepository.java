package com.fatihbozik.creditmodule.repository;

import com.fatihbozik.creditmodule.model.LoanInstallmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanInstallmentRepository extends JpaRepository<LoanInstallmentEntity, Integer> {

    List<LoanInstallmentEntity> findByLoanIdOrderByDueDateAsc(Integer loanId);
}
