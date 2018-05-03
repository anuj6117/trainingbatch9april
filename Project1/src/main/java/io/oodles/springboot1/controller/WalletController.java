package io.oodles.springboot1.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.oodles.springboot1.DTO.AddWallet;
import io.oodles.springboot1.model.ApprovalDTO;
import io.oodles.springboot1.model.Deposit;
import io.oodles.springboot1.model.Users;
import io.oodles.springboot1.model.Wallet;
import io.oodles.springboot1.repository.WalletRepository;
import io.oodles.springboot1.service.WalletService;

@RestController
public class WalletController {
	@Autowired
	WalletService walletService;
	
	@GetMapping("/getallwallet")
	public List<Wallet> getall(){
         return walletService.getallwallet();       	
	}
	@PostMapping("/addwallet")
	public String addwallet(@RequestBody AddWallet addwallet) {
		return walletService.add(addwallet);
	}
	/*@PostMapping("/newwallet")
	public Wallet newwallet(@RequestBody Wallet wallet) {
		return walletService.newwallet1(wallet);
	}*/
	@PostMapping("/depositamount")
	public String depositamount(@RequestBody Deposit deposit) {
		return walletService.deposit(deposit);
	}
	
@PostMapping("/approve")
public String approvedeposit(@RequestBody ApprovalDTO approvalDTO ) {
	System.out.println("????????????????????????????");
	return walletService.approve(approvalDTO);
}
	
	
	@GetMapping("/getbywalletid/{id}")
	public Optional<Wallet> getbyid(@PathVariable int id){
		return walletService.searchbyid(id);
	}
	
	
	@PostMapping("/updatewallet")
	public Wallet updatewallet(@RequestBody Wallet wallet) {
		return walletService.update(wallet);
	}
	
	@GetMapping("/deletewallet/{id}")
	public void deletewallet(@PathVariable int id) {
	    walletService.delete(id);
	}
	
	@GetMapping("/walletHistory")
		public Wallet getwallethistory(@RequestParam Integer userid,@RequestParam String coinname) {
		return walletService.wallethistory(userid,coinname);
	}
	

}
