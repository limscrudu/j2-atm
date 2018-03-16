package com.limscrudu.j2atm.model;

import java.util.ArrayList;
import java.util.List;

public class Wallet extends ResultStatus {
	 
	List<NoteNQuantity> money;
	
	public Wallet() {
		super();
		money = new ArrayList<>();
	}
	
	public  void add(NoteNQuantity item) {
			money.add(item);
	}

	/**
	 * @return the money
	 */
	public List<NoteNQuantity> getMoney() {
		return money;
	}
	
	

}
