package com.limscrudu.j2atm.bo;

import java.util.HashMap;

import com.limscrudu.j2atm.model.NoteNQuantity;
import com.limscrudu.j2atm.model.ResultStatus;
import com.limscrudu.j2atm.model.Wallet;
import com.limscrudu.j2atm.model.Withdrawal;

public class ATM {
	
	private HashMap<Integer, Integer> cashStore;
	
	private Integer[] denominations = {50, 20, 10, 5};
	
	
	public ATM() {
		cashStore = new HashMap<>();
	}
	
	public void add(Integer denomination, Integer quantity) {
		if (cashStore.containsKey(denomination)) {
			cashStore.put(denomination, cashStore.get(denomination) + quantity);
		} else {
			cashStore.put(denomination, quantity);
		}
	}
	
	
	public Wallet withdrawCash(Withdrawal withdrawal) {
		Wallet wallet = new Wallet();
		
		if (withdrawal.getAmount() > getAvailableBalance() ) {
			wallet.setResultStatus(ResultStatus.ATM_AVAILABLE_EXCEEDED);
			return wallet;
		}
		
		//TODO creating a copy of the cash store  is not the best way to do this, really need transaction rollback
		// as you would get with a database implementation
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
					tmpCashStore.put(denomination,quantityAvailable - quantityRequired);
				}
				wallet.add(new NoteNQuantity(denomination,quantityRequired));
				leftover = leftover - (quantityRequired * denomination);				
			}
			if (leftover == 0) break;
		}
		if (leftover == 0) { //we succeeded lets post it
			cashStore = tmpCashStore;			
		} else { // we didn't get the exact amount
			wallet = new Wallet();
			wallet.setResultStatus(ResultStatus.EXACT_AMOUNT_NOT_AVAILABLE);
			return wallet;
		}
		return wallet;
	}

	public Integer getAvailableBalance() {
		Integer availableBalance = 0;
		for (int i = 0; i < denominations.length; i++) {
			availableBalance += denominations[i] * cashStore.get(denominations[i]);
		}
		return availableBalance;
	}
	
	public Integer getAvailableDenominationQuantity(Integer denomination) {
		if (cashStore.containsKey(denomination)) {
			return cashStore.get(denomination);		
		} else  {
			return 0;
		}
 	}
	
	private HashMap<Integer, Integer> deepCopyCashStore() {
		HashMap<Integer, Integer> copy = new HashMap<>();
		for (int i = 0; i < denominations.length; i++) {
			copy.put(denominations[i],	cashStore.get(denominations[i]));
		}
		return copy;
	}

}
