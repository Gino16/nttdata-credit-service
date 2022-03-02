package com.nttdata.credit.repositories;

import com.nttdata.credit.entities.CreditTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CreditTransactionRepository extends JpaRepository<CreditTransaction, Long> {

    @Query("SELECT ct FROM CreditTransaction ct WHERE ct.credit.id = :id")
    public List<CreditTransaction> findAllByCredit(Long id);
}
