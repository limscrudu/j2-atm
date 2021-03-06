package com.limscrudu.j2atm.bo;

import java.util.HashMap;

import com.limscrudu.j2atm.model.NoteNQuantity;
import com.limscrudu.j2atm.model.ResultStatus;
import com.limscrudu.j2atm.model.Wallet;
import com.limscrudu.j2atm.model.Withdrawal;

/**
 * @author limcearna
 *
 *         Provides a business object to represent the simulation of an ATM
 *         Money kept in the ATM is held in the cash store and is held in
 *         specific denominations of €50, €20, €10 and €5 Money is dispensed in
 *         quantities of these denominations Errors are reported is there is not
 *         enough money to meet the withdrawal or the exact amount cannot be
 *         withdrawn
 * 
 */
public class ATM {

	private HashMap<Integer, Integer> cashStore;

	private Integer[] denominations = { 50, 20, 10, 5 };

	public ATM() {
		cashStore = new HashMap<>();
	}

	/**
	 * Add an amount of money to the store by denomination and quantity Currently no
	 * check is made to see if that denomination already exists
	 * 
	 * @param denomination
	 *            - the denomination of the note added
	 * @param quantity
	 *            - the number of notes of that denomination
	 */
	public void add(Integer denomination, Integer quantity) {
		if (cashStore.containsKey(denomination)) {
			cashStore.put(denomination, cashStore.get(denomination) + quantity);
		} else {
			cashStore.put(denomination, quantity);
		}
	}

	/**
	 * Supports the withdrawal of Cash from the ATM It removes the appropriate
	 * denominations and their quantities to make up the withdrawal Fails if the
	 * withdrawal amount is greater than the amount available in the ATM or if the
	 * exact amount cannot be withdrawn. It uses a temporary cash store to make sure
	 * the withdrawal can be made before committing it to the cash store It only
	 * adds entries to the wallet for denominations that it is providing notes for
	 * in the withdrawal
	 * 
	 * @param withdrawal
	 *            - the amount of money to withdraw from the ATM
	 * @return - a wallet with quantities of notes and denominations amounting to
	 *         the withdrawal or a bad result status and no money
	 */
	public Wallet withdrawCash(Withdrawal withdrawal) {
		Wallet wallet = new Wallet();

		if (withdrawal.getAmount() > getAvailableBalance()) {
			wallet.setResultStatus(ResultStatus.ATM_AVAILABLE_EXCEEDED);
			return wallet;
		}

		// TODO creating a copy of the cash store is not the best way to do this, really
		// need transaction roll back as you would get with a database implementation
		HashMap<Integer, Integer> tmpCashStore = deepCopyCashStore();

		Integer amount = withdrawal.getAmount();
		Integer leftover = amount;

		for (int i = 0; i < denominations.length; i++) {
			Integer denomination = denominations[i];
			Integer quantityRequired = leftover / denomination;
			if (quantityRequired > 0) {
				Integer quantityAvailable = tmpCashStore.get(denomination);
				if (quantityAvailable < quantityRequired) {
					tmpCashStore.put(denomination, 0);
					quantityRequired = quantityAvailable;
				} else {
					tmpCashStore.put(denomination, quantityAvailable - quantityRequired);
				}
				if (quantityRequired > 0) {
					wallet.add(new NoteNQuantity(denomination, quantityRequired));
				}
				leftover = leftover - (quantityRequired * denomination);
			}
			if (leftover == 0)
				break;
		}
		if (leftover == 0) { // we succeeded lets post it
			cashStore = tmpCashStore;
		} else { // we didn't get the exact amount
			wallet = new Wallet();
			wallet.setResultStatus(ResultStatus.EXACT_AMOUNT_NOT_AVAILABLE);
			return wallet;
		}
		return wallet;
	}

	/**
	 * How much money is left in the ATM for withdrawal
	 * 
	 * @return - The amount of cash left in the ATM
	 */
	public Integer getAvailableBalance() {
		Integer availableBalance = 0;
		for (int i = 0; i < denominations.length; i++) {
			availableBalance += denominations[i] * cashStore.get(denominations[i]);
		}
		return availableBalance;
	}

	/**
	 * For a particular denomination how many notes are still in the cash store
	 * 
	 * @param denomination
	 * @return - the quantity of notes of the denomination
	 */
	public Integer getAvailableDenominationQuantity(Integer denomination) {
		if (cashStore.containsKey(denomination)) {
			return cashStore.get(denomination);
		} else {
			return 0;
		}
	}

	/* (non-Javadoc)
	 * Method used to create the temporary cash store
	 * 
	 * @return - the temporary cash store
	 */
	private HashMap<Integer, Integer> deepCopyCashStore() {
		HashMap<Integer, Integer> copy = new HashMap<>();
		for (int i = 0; i < denominations.length; i++) {
			copy.put(denominations[i], cashStore.get(denominations[i]));
		}
		return copy;
	}

}
