package com.trading.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

	/*@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CurrencyRepository currencyRepository;

	public Map<String, Object> transaction()
	
	{		Map<String, Object> result = new HashMap<String, Object>();
	
	Transaction transaction = new Transaction();
 
		Currency currency = new Currency();
		UserOrder buyOrder = orderRepository.findByOrderTypeAndStatusAndCoinType(OrderType.BUYER, TransactionOrderStatus.PENDING, WalletType.CRYPTO);
		UserOrder maxBuyOrder = orderRepository.findMaxByPrice(buyOrder.getPrice());

		UserOrder sellOrder = orderRepository.findByOrderTypeAndStatusAndCoinName(OrderType.SELLER, TransactionOrderStatus.PENDING, maxBuyOrder.getCoinName());
			currency = currencyRepository.findByCoinName(buyOrder.getCoinName());
			Currency minPrice = currencyRepository.findMinByPrice(currency.getPrice());
		UserOrder minSellOrder = orderRepository.findMinByPrice(sellOrder.getPrice());
			
		
	if(minPrice.getPrice() > minSellOrder.getPrice())
	{
		
		if(maxBuyOrder.getUser() != minSellOrder.getUser())
		{
				
				if(	maxBuyOrder.getPrice() > minSellOrder.getPrice())
				{
					if(maxBuyOrder.getCoinQuantity()<= minSellOrder.getCoinQuantity()) 

					{
					transaction.setBuyerId(maxBuyOrder.getOrderId());
					transaction.setStatus(TransactionOrderStatus.APPROVED);
					transaction.setSellerId(minSellOrder.getOrderId());
					transaction.setTransactionFee(currency.getFee());
					
					transaction.setTransactionCreatedOn(new Date());
					}
					
					else
					{
						transaction.setBuyerId(maxBuyOrder.getOrderId());
						transaction.setStatus(TransactionOrderStatus.APPROVED);
						transaction.setSellerId(minSellOrder.getOrderId());
						transaction.setTransactionFee(currency.getFee());
						
						transaction.setTransactionCreatedOn(new Date());
					}
				}}}
				
					
			else
			{
				if(maxBuyOrder.getPrice() > minPrice.getPrice())
				{
					if(maxBuyOrder.getCoinQuantity() => minPrice.getInitialSupply()) {
						
						transaction.setBuyerId(maxBuyOrder.getOrderId());
						transaction.setStatus(TransactionOrderStatus.APPROVED);
						transaction.setTransactionFee(currency.getFee());
						
						transaction.setTransactionCreatedOn(new Date());
					}
					else
					{
						set tran
					}
				}
				else
				{
					return price does not match
				}
				else
					{
					if(maxBuyOrder.get)}
					}
			
				}
			}
					
		{
			
		}
		
	}  */

}
