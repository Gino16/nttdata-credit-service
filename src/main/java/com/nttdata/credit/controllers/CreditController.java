package com.nttdata.credit.controllers;

import com.nttdata.credit.entities.Credit;
import com.nttdata.credit.services.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credits")
public class CreditController {

    @Autowired
    private CreditService creditService;

    @GetMapping("/")
    public ResponseEntity<List<Credit>> findAll(){
        return new ResponseEntity<>(creditService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Credit> findOneById(@PathVariable Long id){
        Credit credit = creditService.findOneById(id);
        if(credit == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(credit, HttpStatus.OK);

    }

    @PostMapping("/")
    public ResponseEntity<Credit> create(@RequestBody Credit credit){
        return new ResponseEntity<>(creditService.create(credit), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Credit> edit(@PathVariable Long id, @RequestBody Credit credit){
        Credit newCredit = creditService.edit(id, credit);
        if (newCredit == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newCredit, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        creditService.delete(id);
    }

}
