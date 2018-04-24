package com.example.demo.utility;

import java.util.Random;

public class OtpGenerator {
	public static char[] generateOtp(int len)
    {               
        String numbers = "0123456789";         
        Random rndm_method = new Random(); 
        char[] otp = new char[len]; 
        for (int i = 0; i < len; i++)
        {            
            otp[i] =  numbers.charAt(rndm_method.nextInt(numbers.length())); 
        }
        return otp;
    }
}
