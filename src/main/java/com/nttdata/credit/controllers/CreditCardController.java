package com.nttdata.credit.controllers;

import com.nttdata.credit.entities.Credit;
import com.nttdata.credit.entities.CreditCard;
import com.nttdata.credit.entities.CreditCardType;
import com.nttdata.credit.services.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/creditcards")
public class CreditCardController {
    @Autowired
    private CreditCardService creditCardService;

    @GetMapping("/")
    public ResponseEntity<List<CreditCard>> findAll(){
        return new ResponseEntity<>(creditCardService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditCard> findOneById(@PathVariable Long id){
        CreditCard creditCard = creditCardService.findOneById(id);
        if(creditCard == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(creditCard, HttpStatus.OK);

    }

    @PostMapping("/")
    public ResponseEntity<CreditCard> create(@RequestBody CreditCard creditCard){
        CreditCard newCreditCard1 = creditCardService.create(creditCard);
        if (newCreditCard1 == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(creditCardService.create(creditCard), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreditCard> edit(@PathVariable Long id, @RequestBody CreditCard credit){
        CreditCard newCreditCard = creditCardService.edit(id, credit);
        if (newCreditCard == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newCreditCard, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        creditCardService.delete(id);
    }

    @GetMapping("/types")
    public ResponseEntity<List<CreditCardType>> findAllCreditCardTypes(){
        return new ResponseEntity<>(creditCardService.findAllCreditCardTypes(), HttpStatus.OK);
    }
}
