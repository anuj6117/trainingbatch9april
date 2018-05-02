package com.trading.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trading.domain.UserOrder;
import com.trading.dto.OrderApprovalDto;
import com.trading.dto.UserOrderDto;
import com.trading.handler.ResponseHandler;
import com.trading.services.OrderService;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/createbuyorder", method = RequestMethod.POST)
	public ResponseEntity<Object> buyOrder(@Valid @RequestBody UserOrderDto userOrderDto) throws Exception {
		Map<String, Object> result = null;
		try {
			result = orderService.createBuyOrder(userOrderDto);
			if (result.get("isSuccess").equals(true)) {
				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			} else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, result.get("message").toString(), result);
			}
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}

	}

	@RequestMapping(value = "/createsellorder", method = RequestMethod.POST)
	public ResponseEntity<Object> sellOrder(@Valid @RequestBody UserOrderDto userOrderDto) throws Exception {
		Map<String, Object> result = null;
		try {
			result = orderService.createSellOrder(userOrderDto);
			if (result.get("isSuccess").equals(true)) {
				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			} else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, result.get("message").toString(), result);
			}
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}

	}

	@RequestMapping(value = "/getorderbyuserid", method = RequestMethod.GET)
	public Object getOrder(@Valid @RequestParam("userId") long userId) throws Exception {
		List<UserOrder> userOrder = orderService.getOrderByUserId(userId);
	if(userOrder.isEmpty())
	{
		return "Order Id does not exist";
	}
	return userOrder;
	}

	@RequestMapping(value = "/approveorder", method = RequestMethod.POST)
	public ResponseEntity<Object> approveOrder(@Valid @RequestBody OrderApprovalDto orderApprovalDto) {
		Map<String, Object> result = null;
		try {
			result = orderService.approveOrder(orderApprovalDto);
			if (result.get("isSuccess").equals(true)) {
				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			} else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, result.get("message").toString(), result);
			}
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}

	}
}