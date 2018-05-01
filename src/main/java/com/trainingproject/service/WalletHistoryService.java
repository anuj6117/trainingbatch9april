package com.trainingproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainingproject.domain.WalletHistory;
import com.trainingproject.enums.CoinType;
import com.trainingproject.repository.WalletHistoryRepository;

@Service
public class WalletHistoryService {

	
	@Autowired
	private WalletHistoryRepository userRepository;
	
	

}
