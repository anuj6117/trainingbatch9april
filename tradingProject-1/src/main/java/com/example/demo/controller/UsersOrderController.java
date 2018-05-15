package com.example.demo.controller;

import com.example.demo.model.OrderDetails;
import com.example.demo.service.UsersOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsersOrderController {

    @Autowired
    UsersOrderService usersOrderService;

    @RequestMapping(name="/getallorders")
    public List<OrderDetails> getAllOrders(){
        return usersOrderService.getAllOrders();
    }
}

