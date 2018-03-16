package com.limscrudu.j2atm.bo;

import org.junit.Test;

import com.limscrudu.j2atm.model.NoteNQuantity;
import com.limscrudu.j2atm.model.ResultStatus;
import com.limscrudu.j2atm.model.Wallet;
import com.limscrudu.j2atm.model.Withdrawal;

import junit.framework.TestCase;

public class ATMTest extends TestCase {

	private ATM atm = new ATM();
	private Integer initialBalance;
	
	@Override
	protected void setUp() throws Exception {
		atm.add(50, 20);
		atm.add(20, 30);
		atm.add(10, 30);
		atm.add(5,  20);
		initialBalance = 50*20 + 20*30 + 10*30 + 5*20;
	}

	@Test
	public void testWithdraw200AndLeftover() {
		Wallet wallet = atm.withdrawCash(new Withdrawal(200));
		boolean found50 = false;
		for (NoteNQuantity item: wallet.getMoney()) {
			if (item.getDenomination() == 50) { 
				assertEquals(4, item.getQuantity().intValue());	
				found50 = true;
				}
		}
		assertTrue(found50);
		assertEquals(16,atm.getAvailableDenominationQuantity(50).intValue());
	}
	@Test
	public void testWithdraw220() {
		Wallet wallet = atm.withdrawCash(new Withdrawal(220));
		boolean found50 = false;
		boolean found20 = false;
		for (NoteNQuantity item: wallet.getMoney()) {
			if (item.getDenomination() == 50) { 
				assertEquals(4, item.getQuantity().intValue());	
				found50 = true;
				}
			if (item.getDenomination() == 20) { 
				assertEquals(1, item.getQuantity().intValue());	
				found20 = true;
				}
		}
		assertTrue(found50);
		assertTrue(found20);
	}
	@Test
	public void testWithdraw210() {
		Wallet wallet = atm.withdrawCash(new Withdrawal(210));
		boolean found50 = false;
		boolean found10 = false;
		for (NoteNQuantity item: wallet.getMoney()) {
			if (item.getDenomination() == 50) { 
				assertEquals(4, item.getQuantity().intValue());	
				found50 = true;
				}
			if (item.getDenomination() == 10) { 
				assertEquals(1, item.getQuantity().intValue());	
				found10 = true;
				}
		}
		assertTrue(found50);
		assertTrue(found10);
	}
	@Test
	public void testWithdraw205() {
		Wallet wallet = atm.withdrawCash(new Withdrawal(205));
		boolean found50 = false;
		boolean found05 = false;
		for (NoteNQuantity item: wallet.getMoney()) {
			if (item.getDenomination() == 50) { 
				assertEquals(4, item.getQuantity().intValue());	
				found50 = true;
				}
			if (item.getDenomination() == 5) { 
				assertEquals(1, item.getQuantity().intValue());	
				found05 = true;
				}
		}
		assertTrue(found50);
		assertTrue(found05);
	}
	@Test
	public void testAvailableBalance() {
		Integer withdraw = 85;
		assertEquals(initialBalance.intValue(), atm.getAvailableBalance().intValue());
		atm.withdrawCash(new Withdrawal(withdraw));
		assertEquals(initialBalance - withdraw,atm.getAvailableBalance().intValue());
	}
	
	@Test
	public void testExceedsAvailableBalance() {
		Integer withdraw = initialBalance + 85;
		Wallet wallet = atm.withdrawCash(new Withdrawal(withdraw));
		assertEquals(ResultStatus.ATM_AVAILABLE_EXCEEDED, wallet.getResultStatus());
	}
	
	@Test
	public void testExactAmountNotAvailable() {
		Integer withdraw = 83;
		Wallet wallet = atm.withdrawCash(new Withdrawal(withdraw));
		assertEquals(ResultStatus.EXACT_AMOUNT_NOT_AVAILABLE, wallet.getResultStatus());		
	}

}
