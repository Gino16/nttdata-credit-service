package com.nttdata.credit.controllers;

import com.nttdata.credit.entities.CreditCard;
import com.nttdata.credit.entities.CreditTransaction;
import com.nttdata.credit.entities.Customer;
import com.nttdata.credit.services.CreditCardService;
import com.nttdata.credit.services.CreditTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/credit-cards")
public class CreditCardController {
    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private CreditTransactionService creditTransactionService;

    // List all Credit Cards
    @GetMapping("/")
    public ResponseEntity<List<CreditCard>> findAll() {
        return new ResponseEntity<>(creditCardService.findAll(), HttpStatus.OK);
    }

    // Find One Credit Card By ID
    @GetMapping("/{id}")
    public ResponseEntity<CreditCard> findOneById(@PathVariable Long id) {
        CreditCard creditCard = creditCardService.findOneById(id);
        if (creditCard == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(creditCard, HttpStatus.OK);

    }

    // Create a new Credit Card
    @PostMapping("/")
    public ResponseEntity<CreditCard> create(@RequestBody CreditCard creditCard) {

        Customer customer = creditCardService.findOneCustomerById(creditCard.getIdCustomer());

        if (customer == null) {
            return ResponseEntity.badRequest().build();
        }

        // Validate if a Credit Card is going to be assigning to a Personal Customer
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

    // Update data of a Credit Card
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

    // Make a transaction and save History
    // transaction -> 1 = pay, 2 = consume
    @GetMapping("/customer/{id}/id-card/{idCreditCard}/transaction/{transaction}/quantity/{quantity}")
    public ResponseEntity<?> transaction(@PathVariable Long id, @PathVariable Long idCreditCard, @PathVariable Long transaction, @PathVariable Double quantity) {

        // Validate if customer exists
        Customer customer = creditCardService.findOneCustomerById(id);
        if (customer == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // Get All Credit Cards of customer
        List<CreditCard> creditCards = creditCardService.findCreditCardsByIdCustomer(customer.getId());

        // Validate if exist credit cards of customer
        if (creditCards == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // Get the specific Credit Card to make the transaction
        List<CreditCard> creditCardOwners = creditCards.stream().filter((creditCard) -> Objects.equals(creditCard.getId(), idCreditCard)).collect(Collectors.toList());

        // Validate if exist that credit card
        if (creditCardOwners.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // Getting the credit card
        CreditCard card = creditCardOwners.get(0);

        // Verify if its payment
        if (transaction == 1) {
            if (quantity <= card.getCredit().getAmountUsed()) {
                card.pay(quantity);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            // Verify if its consume
        } else if (transaction == 2) {
            if (card.getCredit().getAmountUsed() + quantity <= card.getCredit().getLimitAmount()) {
                card.consume(quantity);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // Save History
        CreditTransaction creditTransaction = new CreditTransaction();
        creditTransaction.setConcept(transaction == 1 ? "Payment" : "Consume");
        creditTransaction.setAmount(quantity);
        creditTransaction.setDate(new Date());
        creditTransaction.setCredit(card.getCredit());


        creditCardService.create(card);
        creditTransactionService.save(creditTransaction);

        return ResponseEntity.ok(card);
    }

    // Get History of a credit card
    @GetMapping("/history/credit-card/{idDebitCard}")
    public ResponseEntity<List<CreditTransaction>> history(@PathVariable Long idDebitCard) {
        CreditCard creditCard = this.creditCardService.findOneById(idDebitCard);
        if (creditCard == null) {
            return ResponseEntity.badRequest().build();
        }

        List<CreditTransaction> creditTransactions = this.creditTransactionService.findAllByCreditId(creditCard.getCredit().getId());

        return ResponseEntity.ok(creditTransactions);
    }

}
