package com.nttdata.credit.services.impl;

import com.nttdata.credit.clients.CustomerClient;
import com.nttdata.credit.entities.CreditCard;
import com.nttdata.credit.entities.Customer;
import com.nttdata.credit.repositories.CreditCardRepository;
import com.nttdata.credit.services.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Customer findOneCustomerById(Long id){
        return  customerClient.findOneById(id);
    }

    @Override
    public CreditCard findOneById(Long id) {
        CreditCard creditCard = creditCardRepository.findById(id).orElse(null);
        if (creditCard == null){
            return  null;
        }
        Customer customer = customerClient.findOneById(creditCard.getIdCustomer());
        creditCard.setCustomer(customer);
        return creditCard;
    }

    @Override
    public CreditCard create(CreditCard creditCard) {
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


    @Override
    public List<CreditCard> findCreditCardsByIdCustomer(Long id) {
        return creditCardRepository.findCreditCardsByIdCustomer(id);
    }
}
