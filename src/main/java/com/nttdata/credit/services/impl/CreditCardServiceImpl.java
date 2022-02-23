package com.nttdata.credit.services.impl;

import com.nttdata.credit.clients.CustomerClient;
import com.nttdata.credit.entities.CreditCard;
import com.nttdata.credit.entities.CreditCardType;
import com.nttdata.credit.entities.Customer;
import com.nttdata.credit.repositories.CreditCardRepository;
import com.nttdata.credit.services.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private CustomerClient customerClient;

    @Override
    public List<CreditCard> findAll() {
        return creditCardRepository.findAll();
    }

    @Override
    public CreditCard findOneById(Long id) {
        return creditCardRepository.findById(id).orElse(null);
    }

    @Override
    public CreditCard create(CreditCard creditCard) {
        Customer response = customerClient.findOneById(creditCard.getIdCustomer());
        System.out.println(response);
        if (response == null) {
            return null;
        }
        return creditCardRepository.save(creditCard);
    }

    @Override
    public CreditCard edit(Long id, CreditCard creditCard) {
        CreditCard newCreditCard = this.findOneById(id);
        if (newCreditCard == null) {
            return null;
        }
        newCreditCard.setNumber(creditCard.getNumber());
        return creditCardRepository.save(newCreditCard);
    }

    @Override
    public void delete(Long id) {
        creditCardRepository.deleteById(id);
    }

    public List<CreditCardType> findAllCreditCardTypes() {
        return creditCardRepository.findAllCreditCardTypes();
    }
}
