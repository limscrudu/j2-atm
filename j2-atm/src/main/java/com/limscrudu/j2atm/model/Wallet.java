package com.limscrudu.j2atm.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author limcearna
 *
 *         A data object to hold the notes and quantities of them for a
 *         particular withdrawal. The content (money) in the wallet is the
 *         collection of notes and quantities that make up the withdrawal
 * 
 *         There is also a status in the class that specifies the success or
 *         failure of the withdrawal
 * 
 */
public class Wallet extends ResultStatus {

	List<NoteNQuantity> money;

	public Wallet() {
		super();
		money = new ArrayList<>();
	}

	/**
	 * Constructor to allow for a specific result status
	 * 
	 * @param resultStatus
	 */
	public Wallet(String resultStatus) {
		this.setResultStatus(resultStatus);
	}

	/**
	 * Add a denomination and quantity of it to the wallet
	 * 
	 * @param item
	 *            - a denomination and a quantity of notes
	 */
	public void add(NoteNQuantity item) {
		money.add(item);
	}

	/**
	 * @return the money
	 */
	public List<NoteNQuantity> getMoney() {
		return money;
	}

}
