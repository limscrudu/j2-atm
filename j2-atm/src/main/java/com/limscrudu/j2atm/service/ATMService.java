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

/**
 * @author limcearna
 *
 *         A service to provide a separation between the REST API and the actual
 *         functionality It, at least, provides the service calls on the API,
 *         and some helper methods
 */
@Component
public class ATMService {

	// TODO the data wouldn't be stored this way, it would be in a database
	// somewhere
	private static HashMap<String, Account> accounts = new HashMap<>();

	private static ATM atm = new ATM();

	static {
		accounts.put("123456789", new Account("123456789", "1234", BigDecimal.valueOf(800), BigDecimal.valueOf(200)));
		accounts.put("987654321", new Account("987654321", "4321", BigDecimal.valueOf(1230), BigDecimal.valueOf(150)));

		atm.add(50, 20);
		atm.add(20, 30);
		atm.add(10, 30);
		atm.add(5, 20);

	}

	/**
	 * Method used to validate the incoming request on one of the API calls. Given
	 * the account number and the pin, check if the account is valid, if not then
	 * set an invalid customer details status.
	 * 
	 * @param accountNo
	 *            - the customer account number
	 * @param pin
	 *            - the customer's pin
	 * @return - a result status of either okay or invalid customer credentials
	 */
	public ResultStatus validateCustomerAccountDetails(String accountNo, String pin) {
		ResultStatus result = new ResultStatus();
		if (accounts.containsKey(accountNo)) {
			if (accounts.get(accountNo).getPin().contentEquals(pin)) {
				result.setResultStatus(ResultStatus.OK);
			} else {
				result.setResultStatus(ResultStatus.INVALD_CUSTOMER_CREDENTIALS);
			}
		} else {
			result.setResultStatus(ResultStatus.INVALD_CUSTOMER_CREDENTIALS);
			;
		}
		return result;
	}

	/**
	 * Try to withdraw cash from the ATM and provide the result in a Wallet Fail if
	 * the withdrawal would exceed the customers available for withdrawal Finally
	 * update the account balance with the withdrawal
	 * 
	 * @param accountNo
	 *            - the Account from which to withdraw money
	 * @param withdrawal
	 *            - the amount to withdraw
	 * @return - a wallet of notes of denomination and quantities or a failed status
	 */
	public Wallet withdrawCash(String accountNo, Withdrawal withdrawal) {
		Wallet wallet = new Wallet();
		Account currentAccount = accounts.get(accountNo);
		BigDecimal availableAmount = currentAccount.getAvailableForWidrawal();
		if (withdrawal.getAmount() > availableAmount.intValue()) {
			wallet.setResultStatus(ResultStatus.EXCEEDS_CUSTOMER_BALANCE);
			return wallet;
		}

		wallet = atm.withdrawCash(withdrawal);
		if (wallet != null) {
			currentAccount.updateBalance(withdrawal);
		}
		return wallet;
	}

	/**
	 * Check an account for its balance information
	 * 
	 * @param accountNo
	 *            - the account to inquire
	 * @return - the balance information for the account or a failure
	 */
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
