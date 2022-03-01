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
    private Long limitAmount;

    @Column(name = "limit_used")
    private Long amountUsed;

    @Column(name = "amount_paid")
    private Long amountPaid;

    private Long balance;

    public void updateBalance(){
        this.balance = this.limitAmount - this.amountUsed;
    }

}
