package com.limscrudu.j2atm.service;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.limscrudu.j2atm.bo.ATM;
import com.limscrudu.j2atm.model.Account;
import com.limscrudu.j2atm.model.Balance;
import com.limscrudu.j2atm.model.ResultStatus;
import com.limscrudu.j2atm.model.Wallet;
import com.limscrudu.j2atm.model.Withdrawal;

@Component
public class ATMService {
	
	// TODO the data wouldn't be stored this way, it would be in a database somewhere
	private static HashMap<String, Account> accounts = new HashMap<>();
	
	private static ATM atm = new ATM();
	
	static {
		accounts.put("123456789", new Account("123456789", "1234", BigDecimal.valueOf(800), BigDecimal.valueOf(200)));
		accounts.put("987654321", new Account("987654321", "4321", BigDecimal.valueOf(1230), BigDecimal.valueOf(150)));
		
		atm.add(50, 20);
		atm.add(20, 30);
		atm.add(10, 30);
		atm.add(5,  20);

	}
	

	public boolean validateCustomerAccountDetails(String accountNo, String pin) {
		if (accounts.containsKey(accountNo)) {
			if (accounts.get(accountNo).getPin().contentEquals(pin)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;			
		}
		
	}
	public Wallet withdrawCash(String accountNo, Withdrawal withdrawal) {
		Wallet wallet = new Wallet();
		Account currentAccount = accounts.get(accountNo);
		BigDecimal availableAmount = currentAccount.getCurrentBalance().add(currentAccount.getOverdraft());
		if (withdrawal.getAmount() > availableAmount.intValue()) {
			wallet.setResultStatus(ResultStatus.EXCEEDS_CUSTOMER_BALANCE);
			return wallet;
		}

		wallet = atm.withdrawCash(withdrawal);
		if (wallet != null) {
			BigDecimal currentBalance = currentAccount.getCurrentBalance();
			BigDecimal withdrawalAmount = new BigDecimal(withdrawal.getAmount());
			if (withdrawalAmount.compareTo(currentBalance) > 0) {
				// the withdrawal amount exceeds the current balance so we're going into overdraft
				currentAccount.setOverdraft(new BigDecimal(withdrawal.getAmount()).subtract(currentAccount.getCurrentBalance()));
				currentAccount.setCurrentBalance(new BigDecimal("0"));
			} else {
				currentAccount.setCurrentBalance(currentAccount.getCurrentBalance().subtract(BigDecimal.valueOf(withdrawal.getAmount())));				
			}
		}
		return wallet;
	}

	public Balance inquireBalance(String accountNo) {
		Balance balance = new Balance();
		if (accounts.containsKey(accountNo)) {
			Account account = accounts.get(accountNo);
			balance.setBalance(account.getCurrentBalance());
			balance.setOverdraft(account.getOverdraft());
		}
		return balance;
	}


}
