package com.trading.services;
import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.domain.UserOtp;
import com.trading.repository.UserOtpRepo;



@Service
public class UserOtpService {
	@Autowired
	private UserOtpRepo userotprepo;
			
		User user = new User();

UserOtp userotpdb;
	public String verifyDetails(UserOtp userotp) throws Exception {
		userotpdb=userotprepo.findByTokenOTP(userotp.gettokenOTP());
		if(userotpdb!=null){
			if(userotpdb.getEmailid().equals(userotp.getEmailid())) {
               userotprepo.deleteAll();
               
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
		
