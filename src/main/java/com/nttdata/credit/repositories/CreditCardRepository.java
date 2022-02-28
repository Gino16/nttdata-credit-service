package com.nttdata.credit.repositories;

import com.nttdata.credit.entities.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

    @Query("SELECT cc from CreditCard cc WHERE cc.idCustomer = :id")
    public List<CreditCard> findCreditCardsByIdCustomer(Long id);
}
