package com.nttdata.credit.services.impl;

import com.nttdata.credit.entities.Credit;
import com.nttdata.credit.repositories.CreditRepository;
import com.nttdata.credit.services.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditServiceImpl implements CreditService {

    @Autowired
    private CreditRepository creditRepository;

    @Override
    public List<Credit> findAll() {
        return creditRepository.findAll();
    }

    @Override
    public Credit findOneById(Long id) {
        return creditRepository.findById(id).orElse(null);
    }

    @Override
    public Credit create(Credit credit) {
        return creditRepository.save(credit);
    }

    @Override
    public Credit edit(Long id, Credit credit) {
        Credit newCredit = this.findOneById(id);
        if (newCredit == null){
            return  null;
        }
        newCredit.setLimitAmount(newCredit.getLimitAmount());
        return creditRepository.save(newCredit);
    }

    @Override
    public void delete(Long id) {
        creditRepository.deleteById(id);
    }
}
