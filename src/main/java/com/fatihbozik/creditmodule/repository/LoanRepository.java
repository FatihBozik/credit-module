package com.fatihbozik.creditmodule.repository;

import com.fatihbozik.creditmodule.dto.LoanListQuery;
import com.fatihbozik.creditmodule.model.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Integer> {

    @Query("select distinct l "
           + "from LoanEntity l where "
           + "(:#{#q.customerId} is null or l.customer.id = :#{#q.customerId}) and "
           + "(:#{#q.numberOfInstallment} is null or l.numberOfInstallment = :#{#q.numberOfInstallment}) and "
           + "(:#{#q.paid} is null or l.paid = :#{#q.paid})")
    List<LoanEntity> listLoans(@Param("q") LoanListQuery loanListQuery);
}
