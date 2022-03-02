package com.nttdata.credit.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "credit_cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    private String creditCardType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private Credit credit;

    private Long idCustomer;

    @Transient
    private Customer customer;

    public void pay(Double quantity){
        Double amountPaid = this.credit.getAmountPaid();
        Double amountUsed = this.credit.getAmountUsed();
        amountPaid += quantity;
        amountUsed -= quantity;

        this.credit.setAmountUsed(amountUsed);
        this.credit.setAmountPaid(amountPaid);
        this.credit.updateBalance();
    }

    public void consume(Double quantity){
        Double amountUsed = this.credit.getAmountUsed();
        amountUsed += quantity;
        this.credit.setAmountUsed(amountUsed);
        this.credit.updateBalance();
    }
}
