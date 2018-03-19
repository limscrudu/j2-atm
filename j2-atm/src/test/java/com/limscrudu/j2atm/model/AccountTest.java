package com.limscrudu.j2atm.model;

import java.math.BigDecimal;

import org.junit.Test;

import junit.framework.TestCase;

public class AccountTest extends TestCase{
	
	private Account account1;
	private Integer initialBalance = 800;
	private Integer overdraft = 200;

	

	@Override
	protected void setUp() throws Exception {
		account1 = new Account("123456789", "1234", BigDecimal.valueOf(initialBalance), BigDecimal.valueOf(overdraft));
	}



	@Test
	public void testgetAvailableForWithdrawal() {
		assertEquals(initialBalance + overdraft, account1.getAvailableForWidrawal().intValue());
	}
	
	@Test
	public void testUpdateBalanceOnly() {
		Integer withdraw = 700;
		account1.updateBalance(new Withdrawal(withdraw));
		assertEquals(initialBalance - withdraw, account1.getCurrentBalance().intValue());
		assertEquals(overdraft.intValue(), account1.getOverdraft().intValue());
	}
	
	@Test
	public void testUpdateBalanceAndOverdraft() {
		Integer withdraw = 900;
		account1.updateBalance(new Withdrawal(withdraw));
		assertEquals(initialBalance - withdraw, account1.getCurrentBalance().intValue());
		assertEquals(overdraft.intValue(), account1.getOverdraft().intValue());
	}

}
