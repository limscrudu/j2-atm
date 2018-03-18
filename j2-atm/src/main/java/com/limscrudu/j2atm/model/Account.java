package com.limscrudu.j2atm.model;

import java.math.BigDecimal;

public class Account {

	private String accountNo;
	private String pin;
	private BigDecimal currentBalance;
	private BigDecimal overdraft;

	public Account(String accountNo, String pin, BigDecimal currentBalance, BigDecimal overdraft) {
		super();
		this.accountNo = accountNo;
		this.pin = pin;
		this.currentBalance = currentBalance;
		this.overdraft = overdraft;
	}

	/**
	 * @return the accountNo
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * @param accountNo
	 *            the accountNo to set
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * @return the pin
	 */
	public String getPin() {
		return pin;
	}

	/**
	 * @param pin
	 *            the pin to set
	 */
	public void setPin(String pin) {
		this.pin = pin;
	}

	/**
	 * @return the currentBalance
	 */
	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}

	/**
	 * @param currentBalance
	 *            the currentBalance to set
	 */
	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}

	/**
	 * @return the overdraft
	 */
	public BigDecimal getOverdraft() {
		return overdraft;
	}

	/**
	 * @param overdraft
	 *            the overdraft to set
	 */
	public void setOverdraft(BigDecimal overdraft) {
		this.overdraft = overdraft;
	}

	public BigDecimal getAvailableForWidrawal() {
		return getCurrentBalance().add(this.getOverdraft());
	}

	public void updateBalance(Withdrawal withdrawal) {
		BigDecimal currentBalance = getCurrentBalance();
		BigDecimal withdrawalAmount = new BigDecimal(withdrawal.getAmount());
		if (withdrawalAmount.compareTo(currentBalance) > 0) {
			// the withdrawal amount exceeds the current balance so we're going into
			// overdraft
			setOverdraft(new BigDecimal(withdrawal.getAmount()).subtract(getCurrentBalance()));
			setCurrentBalance(new BigDecimal("0"));
		} else {
			setCurrentBalance(getCurrentBalance().subtract(BigDecimal.valueOf(withdrawal.getAmount())));
		}
	}
}
