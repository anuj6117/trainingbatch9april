package com.example.demo.service;

import com.example.demo.model.OrderDetails;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersOrderService {
    @Autowired
    OrderRepository orderRepository;

    public List<OrderDetails> getAllOrders(){
        return orderRepository.findAll();
    }

    public List<OrderDetails> getOrderByUserId(Integer userId){
        return orderRepository.findOneByUserId(userId);
    }
}
