package com.example.demo.utilities;
import java.util.Random;


public class OtpGenearator {
    public static  Integer generateOtp(){
        Integer otp;
        Random random=new Random();
        otp=random.nextInt(999)+9000;
        return otp;
    }
}
