package com.traningproject1.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.traningproject1.demo.dto.AssignWalletDTO;
import com.traningproject1.demo.dto.ClassDTO;
import com.traningproject1.demo.dto.DepositAmountDTO;
import com.traningproject1.demo.dto.WithdrawAmountDTO;
import com.traningproject1.domain.User;
import com.traningproject1.service.ServiceClass;
import com.traningproject1.service.WalletService;

@RestController
public class ControllerClass {
	@Autowired
	ServiceClass serviceClass;
	
	@Autowired
	WalletService walletService;
	
	
@RequestMapping(value="/signup",method=RequestMethod.POST)
public String addUser(@RequestBody User user)
{
	User userCreated=serviceClass.addUser(user);
	if(userCreated!=null)
	return"success";
	else
		return "fail";
}


@RequestMapping(value="/getallusers",method=RequestMethod.GET)
public List<User> getAllUser()
{
	return serviceClass.getAllUser();
	
}


@RequestMapping(value="/getbyuserid",method=RequestMethod.GET)
public Optional<User> getUserById( Integer id)
{
 return serviceClass.getByUserId(id);	
}


@RequestMapping(value="/deleteuser",method=RequestMethod.GET)
public void deleteUser(Integer userId)
{
  serviceClass.deleteUser(userId);
}


@RequestMapping(value="/updateuser",method=RequestMethod.POST)
public  User updateUserData(@RequestBody User user)
{
 return  serviceClass.updateUserData(user);	
}


@RequestMapping(value="/assignrole",method=RequestMethod.POST)
public String assignRoleToUser(@RequestBody ClassDTO classDTO)
{
  try
  {
	  serviceClass.assignRoleToUser(classDTO);
  }
  catch(Exception e)
  {
	  return "Role Cannot Be Assigned"+e.getMessage();
  }
  return "Role Assign Succesfully";
}
@RequestMapping(value="/addwallet",method=RequestMethod.POST)
public String assignWallet(@RequestBody AssignWalletDTO assignwalletdto)
{
	serviceClass.assignWallet(assignwalletdto);
	return "success";
}
@RequestMapping(value="/depositamount",method=RequestMethod.POST)
public String depositAmount(@RequestBody DepositAmountDTO depositamountdto)
{ 
  serviceClass.depositAmount(depositamountdto);
  return "Success";	
}
@RequestMapping(value="/withdrawamount",method=RequestMethod.POST)
public String withdrawAmount(@RequestBody WithdrawAmountDTO withdrawamountdto)
{ 
  serviceClass.withdrawAmount(withdrawamountdto);
  return "Success";	
}
}
