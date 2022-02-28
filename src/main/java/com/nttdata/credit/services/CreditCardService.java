package com.nttdata.credit.services;

import com.nttdata.credit.entities.CreditCard;
import com.nttdata.credit.entities.Customer;

import java.util.List;

public interface CreditCardService {

    public Customer findOneCustomerById(Long id);

    public List<CreditCard> findAll();

    public CreditCard findOneById(Long id);

    public CreditCard create(CreditCard creditCard);

    public CreditCard edit(Long id, CreditCard creditCard);

    public void delete(Long id);

    public List<CreditCard> findCreditCardsByIdCustomer(Long id);
}
