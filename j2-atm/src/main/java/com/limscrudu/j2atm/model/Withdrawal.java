package com.limscrudu.j2atm.model;

/**
 * @author limcearna
 * 
 * A withdrawal specified by an amount, used as the input to the withdraw cash service
 *
 */
public class Withdrawal {
	
	private Integer amount;
	
	public Withdrawal() {
		this.amount = 0;
	}

	public Withdrawal(Integer i) {
		this.amount = i;
	}

	/**
	 * @return the amount
	 */
	public Integer getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	

}
