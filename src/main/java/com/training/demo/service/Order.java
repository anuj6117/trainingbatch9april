package com.training.demo.service;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity
@Table(name="order")
public class Order {
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer orderId;
	
}
