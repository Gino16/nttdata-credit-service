package com.nttdata.credit.services;

import com.nttdata.credit.entities.Credit;

import java.util.List;

public interface CreditService {
    public List<Credit> findAll();

    public Credit findOneById(Long id);

    public Credit create(Credit credit);

    public Credit edit(Long id, Credit credit);

    public void delete(Long id);
}
