package com.nttdata.credit.services.impl;

import com.nttdata.credit.entities.CreditTransaction;
import com.nttdata.credit.repositories.CreditTransactionRepository;
import com.nttdata.credit.services.CreditTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditTransactionServiceImpl implements CreditTransactionService {

    @Autowired
    private CreditTransactionRepository creditTransactionRepository;

    @Override
    public CreditTransaction save(CreditTransaction creditTransaction) {
        return creditTransactionRepository.save(creditTransaction);
    }

    @Override
    public List<CreditTransaction> findAll() {
        return creditTransactionRepository.findAll();
    }

    @Override
    public CreditTransaction findOneById(Long id) {
        return creditTransactionRepository.findById(id).orElse(null);
    }

    @Override
    public List<CreditTransaction> findAllByCreditId(Long creditId) {
        return creditTransactionRepository.findAllByCredit(creditId);
    }
}
