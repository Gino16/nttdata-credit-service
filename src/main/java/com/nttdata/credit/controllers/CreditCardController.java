package com.nttdata.credit.controllers;

import com.nttdata.credit.entities.CreditCard;
import com.nttdata.credit.entities.Customer;
import com.nttdata.credit.services.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/creditcards")
public class CreditCardController {
    @Autowired
    private CreditCardService creditCardService;

    @GetMapping("/")
    public ResponseEntity<List<CreditCard>> findAll() {
        return new ResponseEntity<>(creditCardService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditCard> findOneById(@PathVariable Long id) {
        CreditCard creditCard = creditCardService.findOneById(id);
        if (creditCard == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(creditCard, HttpStatus.OK);

    }

    @PostMapping("/")
    public ResponseEntity<CreditCard> create(@RequestBody CreditCard creditCard) {

        Customer customer = creditCardService.findOneCustomerById(creditCard.getIdCustomer());

        if (customer == null) {
            System.err.println("NO EXISTE LA ENTIDAD USUARIO");
            return ResponseEntity.badRequest().build();
        }

        if (Objects.equals(customer.getCustomerType().getName(), "Personal")) {
            List<CreditCard> creditCardList = creditCardService.findCreditCardsByIdCustomer(creditCard.getIdCustomer());

            if (!creditCardList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
        creditCard.setCreditCardType(customer.getCustomerType().getName());

        CreditCard newCreditCard1 = creditCardService.create(creditCard);
        return new ResponseEntity<>(newCreditCard1, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreditCard> edit(@PathVariable Long id, @RequestBody CreditCard credit) {
        CreditCard newCreditCard = creditCardService.edit(id, credit);
        if (newCreditCard == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newCreditCard, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        creditCardService.delete(id);
    }

}
