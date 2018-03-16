package com.limscrudu.j2atm.model;

public class ResultStatus {
	
	private String resultStatus;
	
	public static final String ATM_AVAILABLE_EXCEEDED = "Withdrawal amount exceeds available cash";
	public static final String EXCEEDS_CUSTOMER_BALANCE = "Withdrawal amount exceeds customer balance";
	public static final String EXACT_AMOUNT_NOT_AVAILABLE = "Exact withdrawal amount cannot be dispensed";
	public static final String OK = "Request Succeeded";
	
	public ResultStatus() {
		this.resultStatus = OK;
	}

	/**
	 * @return the resultStatus
	 */
	public String getResultStatus() {
		return resultStatus;
	}

	/**
	 * @param resultStatus the resultStatus to set
	 */
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}
	

}
