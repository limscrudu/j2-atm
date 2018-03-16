package com.limscrudu.j2atm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.limscrudu.j2atm.model.Balance;
import com.limscrudu.j2atm.model.Wallet;
import com.limscrudu.j2atm.model.Withdrawal;
import com.limscrudu.j2atm.service.ATMService;

@RestController
public class ATMController {

	@Autowired
	ATMService atmService;
	
	// TODO Passing the credentials in the clear like this is completely insecure, but my REST 
	// knowledge is not good enough to do something more secure
	
	@GetMapping("/atm/services/inquireBalance/{accountNo}/{pin}")
	public ResponseEntity<Balance> inqurieBalance(@PathVariable String accountNo, @PathVariable String pin) {

		if (!atmService.validateCustomerAccountDetails(accountNo, pin)) {
			return new ResponseEntity<Balance>(new Balance(), HttpStatus.UNAUTHORIZED);
		}

		Balance balance = atmService.inquireBalance(accountNo);
		
		if (balance == null)
			return new ResponseEntity<Balance>(new Balance(), HttpStatus.BAD_REQUEST);

		return new ResponseEntity<Balance>(balance, HttpStatus.OK);
	}

	@PostMapping("/atm/services/withdrawCash/{accountNo}/{pin}")
	public ResponseEntity<Wallet> withdrawCash(@PathVariable String accountNo, @PathVariable String pin, @RequestBody Withdrawal withdrawal) {
		
		if (!atmService.validateCustomerAccountDetails(accountNo, pin)) {
			return new ResponseEntity<Wallet>(new Wallet(), HttpStatus.UNAUTHORIZED);
		}

		Wallet wallet = atmService.withdrawCash(accountNo, withdrawal);

		if (wallet == null)
			return new ResponseEntity<Wallet>(new Wallet(), HttpStatus.BAD_REQUEST);

		return new ResponseEntity<Wallet>(wallet, HttpStatus.OK);
	}

}
