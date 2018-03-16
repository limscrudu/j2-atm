/**
 * 
 */
package com.limscrudu.j2atm.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.limscrudu.j2atm.model.ResultStatus;
import com.limscrudu.j2atm.model.Wallet;
import com.limscrudu.j2atm.model.Withdrawal;

/**
 * @author limcearna
 *
 */
public class ATMServiceTest {
	
	@Autowired
	ATMService atmService = new ATMService();

	@Test
	public void testValidateCustomerDetails123456789() {
		assertTrue(atmService.validateCustomerAccountDetails("123456789", "1234"));
	}

	@Test
	public void testValidateCustomerDetails987654321() {
		assertTrue(atmService.validateCustomerAccountDetails("987654321", "4321"));
	}
	
	@Test
	public void testValidateCustomerDetailsBadAcBadPin() {
		assertFalse(atmService.validateCustomerAccountDetails("BadAccountNo", "BadPin"));		
	}

	@Test
	public void testValidateCustomerDetailsGoodAc1BadPin() {
		assertFalse(atmService.validateCustomerAccountDetails("123456789", "0000"));
	}
	@Test
	public void testValidateCustomerDetailsBadAcGoodPin1() {
		assertFalse(atmService.validateCustomerAccountDetails("BadAccountNo", "1234"));
	}
	@Test
	public void testValidateCustomerDetailsGoodAc2BadPin() {
		assertFalse(atmService.validateCustomerAccountDetails("987654321", "0000"));
	}
	@Test
	public void testValidateCustomerDetailsBadAcBadPin2() {
		assertFalse(atmService.validateCustomerAccountDetails("BadAccountNo", "4321"));
	}
	
	@Test
	public void testCustomerBalanceExceeded() {
		Wallet wallet = atmService.withdrawCash("123456789", new Withdrawal(1100));
		assertEquals(ResultStatus.EXCEEDS_CUSTOMER_BALANCE, wallet.getResultStatus());
	}
}
