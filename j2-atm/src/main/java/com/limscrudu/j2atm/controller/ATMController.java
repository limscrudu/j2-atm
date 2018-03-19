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
import com.limscrudu.j2atm.model.ResultStatus;
import com.limscrudu.j2atm.model.Wallet;
import com.limscrudu.j2atm.model.Withdrawal;
import com.limscrudu.j2atm.service.ATMService;

@RestController
public class ATMController {

	/**
	 * The Service representing the ATM functionality.
	 */
	@Autowired
	ATMService atmService;

	/**
	 * Inquire Balance Service
	 * 
	 * @param accountNo
	 *            - the account number
	 * @param pin
	 *            - the account pin
	 * @return a Balance object which either has the balance details or a failed
	 *         status
	 */
	@GetMapping("/atm/services/inquireBalance/{accountNo}/{pin}")
	public ResponseEntity<Balance> inqurieBalance(@PathVariable String accountNo, @PathVariable String pin) {

		ResultStatus customerValid = atmService.validateCustomerAccountDetails(accountNo, pin);
		if (!(customerValid.getResultStatus().contentEquals(ResultStatus.OK))) {
			Balance balance = new Balance(customerValid.getResultStatus());
			return new ResponseEntity<Balance>(balance, HttpStatus.UNAUTHORIZED);
		}

		Balance balance = atmService.inquireBalance(accountNo);
		return new ResponseEntity<Balance>(balance, HttpStatus.OK);
	}

	/**
	 * The Withdraw Cash service
	 * 
	 * @param accountNo
	 *            - the account number
	 * @param pin
	 *            - the account pin
	 * @param withdrawal
	 *            - the amount to withdraw
	 * @return - a wallet containing notes of a denomination and the quantity of
	 *         them or a failed status
	 */
	@PostMapping("/atm/services/withdrawCash/{accountNo}/{pin}")
	public ResponseEntity<Wallet> withdrawCash(@PathVariable String accountNo, @PathVariable String pin,
			@RequestBody Withdrawal withdrawal) {

		ResultStatus customerValid = atmService.validateCustomerAccountDetails(accountNo, pin);
		if (!(customerValid.getResultStatus().contentEquals(ResultStatus.OK))) {
			Wallet wallet = new Wallet(customerValid.getResultStatus());
			return new ResponseEntity<Wallet>(wallet, HttpStatus.UNAUTHORIZED);
		}

		Wallet wallet = atmService.withdrawCash(accountNo, withdrawal);
		if (wallet != null && wallet.getResultStatus().contentEquals(ResultStatus.OK)) {
			return new ResponseEntity<Wallet>(wallet, HttpStatus.OK);
		} else {
			return new ResponseEntity<Wallet>(wallet, HttpStatus.BAD_REQUEST);
		}
			
		
	}

}
