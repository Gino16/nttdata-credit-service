package com.nttdata.credit.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "credits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "limit_amount")
    private Double limitAmount;

    @Column(name = "limit_used")
    private Double amountUsed;

    @Column(name = "amount_paid")
    private Double amountPaid;

    @Column(name = "current_balance")
    private Double currentBalance;

    public void updateBalance(){
        this.currentBalance = this.limitAmount - this.amountUsed;
    }

}
