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
import java.util.stream.Collectors;

@Controller
@RequestMapping("/credit-cards")
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


    @GetMapping("/customer/{id}/account/{idAccount}/transaction/{transaction}/{quantity}")
    public ResponseEntity<?> transaction(@PathVariable Long id, @PathVariable Long idAccount, @PathVariable Long transaction, @PathVariable Long quantity) {

        // transaction -> 1 = pay, 2 = consume

        Customer customer = creditCardService.findOneCustomerById(id);
        if (customer == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        List<CreditCard> creditCards = creditCardService.findCreditCardsByIdCustomer(customer.getId());

        if (creditCards == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        List<CreditCard> creditCardOwners = creditCards.stream().filter((creditCard) -> Objects.equals(creditCard.getId(), idAccount)).collect(Collectors.toList());


        if (creditCardOwners.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        CreditCard card = creditCardOwners.get(0);

        if (transaction == 1){
            if (quantity <= card.getCredit().getAmountUsed()){
                card.pay(quantity);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        } else if (transaction == 2){
            if (card.getCredit().getAmountUsed() + quantity <= card.getCredit().getLimitAmount()){
                card.consume(quantity);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        creditCardService.create(card);

        return ResponseEntity.ok(card);
    }

}
