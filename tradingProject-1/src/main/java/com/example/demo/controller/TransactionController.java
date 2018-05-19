package com.example.demo.controller;

import com.example.demo.model.TransactionDetails;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @RequestMapping(value="/transaction")
    public String makeTransaction(){
        return transactionService.makeTransaction();
    }

    @RequestMapping(value="/showalltransactions")
    public List<TransactionDetails> getallTrasactions()
    {
        return transactionService.getallTransactions();
    }
}
