package com.trainingproject.utils;


/*import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ResponseHandler
{
	public static ResponseEntity<Object> generateResponse(HttpStatus status, boolean error, String message,
	Object response) {
	Map<String, Object> map = new HashMap<String, Object>();
	try {
		map.put("timestamp", new Date());
		map.put("status", status.value());
		map.put("isSuccess", error);
		map.put("message", message);
		map.put("data", response);
		return new ResponseEntity<Object>(map, status);
	} catch (Exception e) {
		map.clear();
		map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		map.put("message", e.getMessage());
		map.put("data", null);
		return new ResponseEntity<Object>(map, status);
	}
	}

	public static ResponseEntity<Object> invalidResponse(HttpStatus status, boolean error, String message) {
	Map<String, Object> map = new HashMap<String, Object>();
	try {
	map.put("timestamp", new Date());
	map.put("status", status.value());
	map.put("isSuccess", error);
	map.put("message", message);

	return new ResponseEntity<Object>(map, status);
	} catch (Exception e) {
	map.clear();
	map.put("timestamp", new Date());
	map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
	// map.put("isSuccess",false);
	map.put("message", e.getMessage());
	map.put("data", null);
	return new ResponseEntity<Object>(map, status);
	}
	}

	@org.springframework.web.bind.annotation.ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleException(MethodArgumentNotValidException exception) {
	String errorMsg = exception.getBindingResult().getFieldErrors().stream()
    .map(DefaultMessageSourceResolvable::getDefaultMessage).findFirst().orElse(exception.getMessage());

	return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, errorMsg, null);
	}

}*/

 import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ResponseHandler {

	public static ResponseEntity<Object> generateResponse(HttpStatus status, boolean error, String message,
			Object responseObj) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("timestamp", new Date());
			map.put("status", status.value());
			map.put("isSuccess", error);
			map.put("message", message);
			map.put("data", responseObj);

			return new ResponseEntity<Object>(map, status);
		} catch (Exception e) {
			map.clear();
			map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("message", e.getMessage());
			map.put("data", null);
			return new ResponseEntity<Object>(map, status);
		}
	}

	public static ResponseEntity<Object> invlidResponse(HttpStatus status, boolean error, String message) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("timestamp", new Date());
			map.put("status", status.value());
			map.put("isSuccess", error);
			map.put("message", message);

			return new ResponseEntity<Object>(map, status);
		} catch (Exception e) {
			map.clear();
			map.put("timestamp", new Date());
			map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			// map.put("isSuccess",false);
			map.put("message", e.getMessage());
			map.put("data", null);
			return new ResponseEntity<Object>(map, status);
		}
	}

	@org.springframework.web.bind.annotation.ExceptionHandler 
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleException(MethodArgumentNotValidException exception) {
		String errorMsg = exception.getBindingResult().getFieldErrors().stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.findFirst().orElse(exception.getMessage());
		
		return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, errorMsg, null);
	}
}

/*
  
 
 @RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<Object> saveUser(@RequestBody User user)
	{
		Map<String, Object> result = null;

		try {
			result = signUpService.addUser(user);

			if (result.get("isSuccess").equals(true)) {
				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			} else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, result.get("message").toString(), result);
			}

		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
	}


//////////
 * 
 *  public Map<String, Object> addUser(User user) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		if ((userRepository.findByEmail(user.getEmail()) == null)) {
			
			
			Random randomNumber = new Random();
			otp = randomNumber.nextInt(10000);

			Role role = null;
			if ((role = roleRepository.findByRoleType("user")) == null) {
				role = new Role();
				role.setRoleType("user");
			}

			User newUser = new User(user);
			newUser.getRoles().add(role);
			newUser.setStatus(UserStatus.INACTIVE);

			Wallet wallet = new Wallet();
			wallet.setWalletType(WalletType.FIAT);
			wallet.setBalance(0.0);
			wallet.setShadowBalance(0.0);
			wallet.setUser(newUser);
			newUser.getWallets().add(wallet);
			walletRepository.save(wallet);

			if ((userRepository.save(newUser) != null))
			{
				
				otpService.sendSms(otp);
				mailService.sendMail(otp, user.getEmail());

				verifyOtp.setId(user.getUserId());
				verifyOtp.setTokenOtp(otp);
				verifyOtp.setEmailId(user.getEmail());
				verifyOtp.getEmailId();
				verifyOtp.setDate(new Date());

				verifyOtpRepository.save(verifyOtp);

				result.put("isSuccess", true);
				result.put("message", "SUCCESS");
				return result;
			} else {
				result.put("isSuccess", false);
				result.put("message", "FAIL");
				return result;
			}
		} else {
			result.put("isSuccess", false);
			result.put("message", "User Already Exist.");
			return result;
		}

	}
	
	'''''''''''''''''''
	package com.example.demo.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.enums.UserStatus;
import com.example.demo.enums.WalletType;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.VerifyOtp;
import com.example.demo.model.Wallet;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VerifyOtpRepository;
import com.example.demo.repository.WalletRepository;
import com.example.demo.utility.EmailValidator;
import com.example.demo.utility.MobileNumberValidator;

@Service
public class SignUpService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private OTPService otpService;

	@Autowired
	private MailService mailService;

	@Autowired
	private VerifyOtpRepository verifyOtpRepository;

	@Autowired
	private WalletRepository walletRepository;
	
	@Autowired
	private EmailValidator emailValidator;
	
	@Autowired
	private MobileNumberValidator mobileNumberValidator;

	private VerifyOtp verifyOtp = new VerifyOtp();
	


	private Integer otp;

	public Map<String, Object> addUser(User user) 
	{
		Map<String, Object> result = new HashMap<String, Object>();
		
		if ((userRepository.findByEmail(user.getEmail()) == null)) 
		{
			
			String userName=user.getUserName();
			if(userName.equals("") || userName.isEmpty() || userName == null)
			{
				result.put("isSuccess", false);
				result.put("message", "User Name can't be null.");
				return result;
			}
			if(userName.length() >= 26 || userName.length() <= 6)
			{
				result.put("isSuccess", false);
				result.put("message", "Maximum characters allowed for this field is 6 to 25.");
				return result;
			}
			if(userName.trim().length() == 0)
			{
				result.put("isSuccess", false);
				result.put("message", "User Name must contain characters.");
				return result;
			}
			
			String email=user.getEmail();
			
			boolean b=emailValidator.validateEmail(email);
			
			if(!b)
			{
				result.put("isSuccess", false);
				result.put("message", "Please enter a valid email address.");
				return result;
			}
			
			String phoneNumber=user.getPhoneNumber();
			
			if(!mobileNumberValidator.isValid(phoneNumber))
			{
				result.put("isSuccess", false);
				result.put("message", "Please enter a valid mobile number.");
				return result;
			}
			
						
			Random randomNumber = new Random();
			otp = randomNumber.nextInt(10000);

			Role role = null;
			if ((role = roleRepository.findByRoleType("user")) == null) 
			{
				role = new Role();
				role.setRoleType("user");
			}

			User newUser = new User(user);
			newUser.getRoles().add(role);
			newUser.setStatus(UserStatus.INACTIVE);

			Wallet wallet = new Wallet();
			wallet.setWalletType(WalletType.FIAT);
			wallet.setCoinName("INR");
			wallet.setBalance(0.0);
			wallet.setShadowBalance(0.0);
			wallet.setUser(newUser);
			newUser.getWallets().add(wallet);
			
			
			newUser.setUserName(userName.trim());
			newUser.setEmail(email);
			if(userRepository.findByPhoneNumber(phoneNumber) == null)
			{	
				newUser.setPhoneNumber(phoneNumber);
			}

			if ((userRepository.save(newUser) != null))
			{
				walletRepository.save(wallet);
				otpService.sendSms(otp);
				mailService.sendMail(otp, user.getEmail());

				verifyOtp.setId(user.getUserId());
				verifyOtp.setTokenOtp(otp);
				verifyOtp.setEmailId(user.getEmail());
				verifyOtp.getEmailId();
				verifyOtp.setDate(new Date());

				verifyOtpRepository.save(verifyOtp);

				result.put("isSuccess", true);
				result.put("message", "Your account has been successfully created. Please, verify it by using OTP.");
				return result;
			} 
			else
			{
				result.put("isSuccess", false);
				result.put("message", "FAIL");
				return result;
			}
		} 
		else
		{
			result.put("isSuccess", false);
			result.put("message", "Oopss, this email id is already exist.");
			return result;
		}
	}

	public String verifyUserWithOtp(String emailId, Integer otp) {

		VerifyOtp vOtp = verifyOtpRepository.findByEmailId(emailId);
		if (vOtp == null) 
		{
			return "email does not exist";
		}
		
		Integer v_otp = vOtp.getTokenOtp();
		
		if (otp.equals(v_otp)) 
		{
			User user = userRepository.findByEmail(emailId);
			user.setStatus(UserStatus.ACTIVE);
			verifyOtpRepository.deleteById(vOtp.getId());
			return "Otp verification successfull.";
		} 
		else
		{
			return "Sorry, invalid email or otp";
		}
	}

	public Optional<User> getuserById(Integer id) {

		Optional<User> usrid = userRepository.findById(id);
		if (usrid != null) {
			return usrid;
		} else {
			throw new NullPointerException("Id doen't be exist");
		}
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();

	}

	public User update(User user) {

		User userdb = null;
		userdb = userRepository.findOneByUserId(user.getUserId());
		userdb.setUserName(user.getUserName());
		userdb.setEmail(user.getEmail());
		userdb.setPhoneNumber(user.getPhoneNumber());
		userdb.setCountry(user.getCountry());
		userdb.setPassword(user.getPassword());

		return userRepository.save(userdb);
	}

	public void delete(Integer id) {

		userRepository.deleteById(id);
	}
	
	/*
	 * public String addUser(User user) { if
	 * ((userRepository.findByEmail(user.getEmail()) == null)) { Random randomNumber
	 * = new Random(); otp = randomNumber.nextInt(10000);
	 * 
	 * 
	 * Role role = null; if((role = roleRepository.findByRoleType("user")) == null)
	 * { role = new Role(); role.setRoleType("user"); }
	 * 
	 * User newUser = new User(user); newUser.getRoles().add(role);
	 * newUser.setStatus(UserStatus.INACTIVE);
	 * System.out.println(newUser.getRoles()); Wallet wallet=new Wallet();
	 * wallet.setWalletType(WalletType.FIAT); wallet.setUser(user);
	 * wallet.setBalance(0.0); wallet.setShadowBalance(0.0);
	 * wallet.setUser(newUser); newUser.getWallets().add(wallet);
	 * 
	 * if ((userRepository.save(newUser) != null)) { otpService.sendSms(otp);
	 * mailService.sendMail(otp, user.getEmail());
	 * 
	 * verifyOtp.setId(user.getUserId()); verifyOtp.setTokenOtp(otp);
	 * verifyOtp.setEmailId(user.getEmail()); verifyOtp.getEmailId();
	 * verifyOtp.setDate(new Date());
	 * 
	 * verifyOtpRepository.save(verifyOtp);
	 * 
	 * return "Successfully sent otp."; } else { return "Failure"; } } else { return
	 * "Already existing user"; } }
	 */	