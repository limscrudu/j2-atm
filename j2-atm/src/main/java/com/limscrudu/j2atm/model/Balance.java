package com.limscrudu.j2atm.model;

import java.math.BigDecimal;

public class Balance extends ResultStatus {
	
	private BigDecimal balance;
	private BigDecimal overdraft;
	
	public Balance() {
		super();
		balance = new BigDecimal("0");
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getBalance() {
		return balance;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setBalance(BigDecimal amount) {
		this.balance = amount;
	}

	/**
	 * @return the overdraft
	 */
	public BigDecimal getOverdraft() {
		return overdraft;
	}

	/**
	 * @param overdraft the overdraft to set
	 */
	public void setOverdraft(BigDecimal overdraft) {
		this.overdraft = overdraft;
	}

}
