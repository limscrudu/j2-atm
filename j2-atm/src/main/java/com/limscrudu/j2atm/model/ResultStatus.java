package com.limscrudu.j2atm.model;

/**
 * @author limcearna
 *
 *         A data object used to return a status of the request of any of the
 *         service calls. It is primarily used to report errors back to the
 *         service caller
 */
public class ResultStatus {

	private String resultStatus;

	public static final String ATM_AVAILABLE_EXCEEDED = "Withdrawal amount exceeds available cash";
	public static final String EXCEEDS_CUSTOMER_BALANCE = "Withdrawal amount exceeds customer balance";
	public static final String EXACT_AMOUNT_NOT_AVAILABLE = "Exact withdrawal amount cannot be dispensed";
	public static final String INVALD_CUSTOMER_CREDENTIALS = "Invald Customer Credentials";
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
	 * @param resultStatus
	 *            the resultStatus to set
	 */
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}

}
