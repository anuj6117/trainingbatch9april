package io.oodles.springboot1.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
@Service
public class OTP {
	 public static final String ACCOUNT_SID = "AC4c627dc6e58b5e5f84994302f997b60b";
	 //public static final String AUTH_TOKEN = "154809d683e2eafe73909cf0201bc84a"; //test
	 public static final String AUTH_TOKEN = "b8f8ec2b300de4ac8e6784d00bcc5cd2";
	 public static final String TWILIO_NUMBER = "+13028772463";
	 /*@Autowired
	 Mail mail;*/
	 /*@Autowired
	 JavaMailSender sender;
	*/
	 //public static final String TWILIO_NUMBER = "+919717562586";+15105647903 (513) 657-4064 
	 
	 /*@RequestMapping("/firstotp")
		//@ResponseBody
		public String f(){
	 sendSMS();
	 sendMail();
	 //mail.sendMail();
		return "hello world";
		
		}*/
	 /*
	 public void getphone(){
	 Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

	 IncomingPhoneNumber number = IncomingPhoneNumber
	 .creator(new PhoneNumber("+15005550006"))
	 .setVoiceUrl("http://demo.twilio.com/docs/voice.xml")
	 .create();

	 System.out.println(number.getSid());
	 }
	 */
	 public void sendSMS(int otp1) {
	 try {
		 /*Random rnd=new Random();
			int otp=rnd.nextInt(10000);*/
		
	 TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
	 
	 // Build a filter for the MessageList
	 List<NameValuePair> params = new ArrayList<NameValuePair>();
	 //params.add(new BasicNameValuePair("Body", "Hello, World!"));
	 params.add(new BasicNameValuePair("Body", "Your first message from Twilio!"+otp1));
	 params.add(new BasicNameValuePair("To", "+918744855890")); //Add real number here
	 params.add(new BasicNameValuePair("From", TWILIO_NUMBER));
	 
	 MessageFactory messageFactory = client.getAccount().getMessageFactory();
	 Message message = messageFactory.create(params);
	 System.out.println(message.getSid());
	 } 
	 catch (TwilioRestException e) {
	 System.out.println(e.getErrorMessage());
	 }
	 }
	 

}
