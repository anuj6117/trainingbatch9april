package com.trading.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.Enum.OrderType;
import com.trading.Enum.TransactionOrderStatus;
import com.trading.Enum.WalletType;
import com.trading.domain.Currency;
import com.trading.domain.Transaction;
import com.trading.domain.UserOrder;
import com.trading.repository.CurrencyRepository;
import com.trading.repository.OrderRepository;

@Service
public class TransactionService {

/*	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CurrencyRepository currencyRepository;

	public String transaction(Transaction transaction)
	{ 
		Currency currency = new Currency();
		UserOrder buyOrder = orderRepository.findByOrderTypeAndStatusAndCoinType(OrderType.BUYER, TransactionOrderStatus.PENDING, WalletType.CRYPTO);
		UserOrder maxBuyOrder = orderRepository.findMaxByPrice(buyOrder.getPrice());

		UserOrder sellOrder = orderRepository.findByOrderTypeAndStatusAndCoinName(OrderType.SELLER, TransactionOrderStatus.PENDING, maxBuyOrder.getCoinName());
			currency = currencyRepository.findByCoinName(buyOrder.getCoinName());
			Currency minPrice = currencyRepository.findMinByPrice(currency.getPrice());
		UserOrder minSellOrder = orderRepository.findMinByPrice(sellOrder.getPrice());
		if(maxBuyOrder.getUser() != minSellOrder.getUser() )
			
		{
			
			if(minPrice.getPrice() > minSellOrder.getPrice())
				if(maxBuyOrder.getCoinQuantity()<= minSellOrder.getCoinQuantity()) {
					
				
				if(	maxBuyOrder.getPrice() > minSellOrder.getPrice())
				{
					transaction.setBuyerId(maxBuyOrder.getOrderId());
					transaction.setStatus(TransactionOrderStatus.APPROVED);
					transaction.setSellerId(minSellOrder.getOrderId());
					transaction.setTransactionFee(currency);
					transaction.set
				
				}}
			else
			{
				if(maxBuyOrder.getPrice() > minPrice.getPrice())
				{
					transaction.setBuyerId(maxBuyOrder.getOrderId());
					transaction.setStatus(TransactionOrderStatus.APPROVED);
					
				}
			}
					
		{
			
		}
		
	} */

}
