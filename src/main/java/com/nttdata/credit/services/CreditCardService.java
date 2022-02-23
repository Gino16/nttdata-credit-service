package com.nttdata.credit.services;

import com.nttdata.credit.entities.CreditCard;
import com.nttdata.credit.entities.CreditCardType;

import java.util.List;

public interface CreditCardService {
    public List<CreditCard> findAll();

    public CreditCard findOneById(Long id);

    public CreditCard create(CreditCard creditCard);

    public CreditCard edit(Long id, CreditCard creditCard);

    public void delete(Long id);

    public List<CreditCardType> findAllCreditCardTypes();
}
