package com.trading.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.Enum.UserStatus;
import com.trading.domain.User;
import com.trading.domain.UserOtp;
import com.trading.dto.UserOtpDto;
import com.trading.repository.UserOtpRepository;
import com.trading.repository.UserRepository;

@Service
public class UserOtpService {

	@Autowired
	private UserOtpRepository userotprepository;

	@Autowired
	private UserRepository userrepository;

	UserOtp userotpdb;

	public Map<String, Object> verifyDetails(UserOtpDto userotpDto) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		userotpdb = userotprepository.findByTokenOTP(userotpDto.getTokenOTP());
		User user = userrepository.findOneByUserId(userotpDto.getuserId());
		if (user == null) {
			result.put("isSuccess", false);
			result.put("message", "User does not exist");
			return result;
		}
		if (userotpdb != null) {
			if (userotpdb.getEmail().equals(user.getEmail())) {
				userotprepository.deleteAll();
				user.setStatus(UserStatus.Active);
				userrepository.save(user);
				result.put("isSuccess", true);
				result.put("message", "Success");
				return result;
			} else {
				result.put("isSuccess", false);
				result.put("message", "Failure");
				return result;
			}
		} else {
			result.put("isSuccess", false);
			result.put("message", "Invalid OTP");
			return result;
		}
	}
}
