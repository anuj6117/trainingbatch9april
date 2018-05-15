package com.example.demo.controller;

import com.example.demo.model.OrderDetails;
import com.example.demo.service.UsersOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class UsersOrderController {

    @Autowired
    UsersOrderService usersOrderService;

    @RequestMapping(name="/getallorders")
    public List<OrderDetails> getAllOrders(){
        return usersOrderService.getAllOrders();
    }
}

