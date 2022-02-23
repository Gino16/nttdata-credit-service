package com.nttdata.credit.repositories;

import com.nttdata.credit.entities.CreditCard;
import com.nttdata.credit.entities.CreditCardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

    @Query("SELECT cct from CreditCardType cct")
    public List<CreditCardType> findAllCreditCardTypes();
}
