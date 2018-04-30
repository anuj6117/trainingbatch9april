package com.trading.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trading.dto.UserWalletDto;
import com.trading.handler.ResponseHandler;
import com.trading.services.WalletService;

@RestController
public class WalletController {

	@Autowired
	private WalletService walletService;

	@RequestMapping(value = "/addwallet", method = RequestMethod.POST)
	public ResponseEntity<Object> addWallet(@Valid @RequestBody UserWalletDto userwalletdto) {
		Map<String, Object> result = null;
		try {
			result = walletService.insertWallet(userwalletdto);
			if (result.get("isSuccess").equals(true)) {
				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			} else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, result.get("message").toString(), result);
			}
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}

	}

	@RequestMapping(value = "/depositamount", method = RequestMethod.POST)
	public ResponseEntity<Object> deposit(@Valid @RequestBody UserWalletDto userwalletdto) {
		Map<String, Object> result = null;
		try {
			result = walletService.depositAmount(userwalletdto);
			if (result.get("isSuccess").equals(true)) {
				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			} else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, result.get("message").toString(), result);
			}
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}

	}

	@RequestMapping(value = "/withdrawamount", method = RequestMethod.POST)
	public ResponseEntity<Object> withdraw(@Valid @RequestBody UserWalletDto userwalletdto) {
		Map<String, Object> result = null;
		try {
			result = walletService.withdrawAmount(userwalletdto);
			if (result.get("isSuccess").equals(true)) {
				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			} else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, result.get("message").toString(), result);
			}
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}

	}

}
