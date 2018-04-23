package com.trading.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.Enum.UserStatus;
import com.trading.domain.User;
import com.trading.domain.UserOtp;
import com.trading.repository.UserOtpRepository;
import com.trading.repository.UserRepository;

@Service
public class UserOtpService {
	
@Autowired
private UserOtpRepository userotprepository;
	
@Autowired
private UserRepository userrepository;

UserOtp userotpdb;
	
	public String verifyDetails(UserOtp userotp) throws Exception {
		userotpdb=userotprepository.findByTokenOTP(userotp.gettokenOTP());
		User user = userrepository.findByEmail(userotp.getEmail());
		if(userotpdb!=null){
			if(userotpdb.getEmail().equals(userotp.getEmail())) {
               userotprepository.deleteAll();
               user.setStatus(UserStatus.Active);
       			userrepository.save(user);               
       			return "Success";
       		}
			else
			{
				return "Failure";
			}	
		}
		else
		{
			return "Not Found";
		}
	}
}
		
