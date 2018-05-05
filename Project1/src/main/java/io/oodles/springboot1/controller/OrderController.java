package io.oodles.springboot1.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.oodles.springboot1.model.BuyOrder;
import io.oodles.springboot1.model.UserOrder;
import io.oodles.springboot1.model.UserTransaction;
import io.oodles.springboot1.repository.TransactionRepository;
import io.oodles.springboot1.service.OrderService;

@RestController
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	TransactionRepository transactionRepository;
	@GetMapping("/showallorder")
	public List<UserOrder> showallorder() {
		return orderService.showorder();
	}
	
	@PostMapping("/createbuyorder")
	public String buyorder(@RequestBody BuyOrder buyOrder) {
		return orderService.buy(buyOrder);
	}
	
	@PostMapping("/createsellorder")
	public String sellorder(@RequestBody BuyOrder buyOrder) {
		return orderService.sell(buyOrder);
	}
	
	@GetMapping("/getorderbyuserid")
	public Optional<UserOrder> getorder(@RequestParam Integer id) {
		return orderService.get(id);
	}
	
	@GetMapping("/transaction")
	public void maketransaction() {
		
		orderService.transaction();
	}
	@GetMapping("/showalltransaction")
	public List<UserTransaction> showall() {
		return orderService.show();
	}

}
