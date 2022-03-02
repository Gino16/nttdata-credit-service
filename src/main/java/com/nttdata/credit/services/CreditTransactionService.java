package com.nttdata.credit.services;

import com.nttdata.credit.entities.CreditTransaction;

import java.util.List;

public interface CreditTransactionService {
    public CreditTransaction save(CreditTransaction creditTransaction);

    public List<CreditTransaction> findAll();

    public CreditTransaction findOneById(Long id);

    public List<CreditTransaction> findAllByCreditId(Long creditId);
}
