package com.trading.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.Enum.StatusType;
import com.trading.domain.User;
import com.trading.domain.UserOtp;
import com.trading.repository.UserOtpRepo;
import com.trading.repository.UserRepo;



@Service
public class UserOtpService {
	@Autowired
	private UserOtpRepo userotprepo;
	@Autowired
private UserRepo userrepo;
	

UserOtp userotpdb;
	public String verifyDetails(UserOtp userotp) throws Exception {
		userotpdb=userotprepo.findByTokenOTP(userotp.gettokenOTP());
		User user = userrepo.findByEmail(userotp.getEmail());
		if(userotpdb!=null){
			if(userotpdb.getEmail().equals(userotp.getEmail())) {
               userotprepo.deleteAll();
               user.setStatus(StatusType.Active);
       		userrepo.save(user);               
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
}}
		
