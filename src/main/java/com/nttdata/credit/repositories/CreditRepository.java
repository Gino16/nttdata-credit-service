package com.nttdata.credit.repositories;

import com.nttdata.credit.entities.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditRepository extends JpaRepository<Credit, Long> {
}
