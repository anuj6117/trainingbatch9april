package com.example.demo.controller;

import com.example.demo.model.TransactionDetails;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trading")

public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @RequestMapping(value="/transaction",method = RequestMethod.GET)
    public String makeTransaction(){
        return transactionService.makeTransaction();
    }

    @RequestMapping(value="/showalltransactions",method = RequestMethod.GET)
    public List<TransactionDetails> getallTrasactions()
    {
        return transactionService.getallTransactions();
    }
}
