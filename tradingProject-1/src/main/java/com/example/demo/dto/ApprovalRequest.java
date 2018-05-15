package com.example.demo.dto;

import com.example.demo.enums.OrderStatus;

public class ApprovalRequest {

    private int orderId;
    private OrderStatus status;


    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
