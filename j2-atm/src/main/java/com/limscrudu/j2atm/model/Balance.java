package com.limscrudu.j2atm.model;

import java.math.BigDecimal;

/**
 * @author limcearna
 *
 *         A data object representing the result of a balance inquiry It reports
 *         the balance for the account, which is the balance plus the overdraft.
 */
public class Balance extends ResultStatus {

	private BigDecimal balance = null;
	private BigDecimal overdraft = null;

	public Balance() {
		super();
	}

	/**
	 * Constructor to allow for a specific result status
	 * 
	 * @param resultStatus
	 */
	public Balance(String resultStatus) {
		this.setResultStatus(resultStatus);
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getBalance() {
		return balance;
	}

	/**
	 * @param amount
	 *            the amount to set
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
	 * @param overdraft
	 *            the overdraft to set
	 */
	public void setOverdraft(BigDecimal overdraft) {
		this.overdraft = overdraft;
	}

}
