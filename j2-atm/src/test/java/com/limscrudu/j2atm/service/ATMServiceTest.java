/**
 * 
 */
package com.limscrudu.j2atm.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.limscrudu.j2atm.model.Balance;
import com.limscrudu.j2atm.model.NoteNQuantity;
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
		assertEquals(ResultStatus.OK, atmService.validateCustomerAccountDetails("123456789", "1234").getResultStatus());
	}

	@Test
	public void testValidateCustomerDetails987654321() {
		assertEquals(ResultStatus.OK, atmService.validateCustomerAccountDetails("987654321", "4321").getResultStatus());
	}

	@Test
	public void testValidateCustomerDetailsBadAcBadPin() {
		assertEquals(ResultStatus.INVALD_CUSTOMER_CREDENTIALS,
				atmService.validateCustomerAccountDetails("BadAccountNo", "BadPin").getResultStatus());
	}

	@Test
	public void testValidateCustomerDetailsGoodAc1BadPin() {
		assertEquals(ResultStatus.INVALD_CUSTOMER_CREDENTIALS,
				atmService.validateCustomerAccountDetails("123456789", "0000").getResultStatus());
	}

	@Test
	public void testValidateCustomerDetailsBadAcGoodPin1() {
		assertEquals(ResultStatus.INVALD_CUSTOMER_CREDENTIALS,
				atmService.validateCustomerAccountDetails("BadAccountNo", "1234").getResultStatus());
	}

	@Test
	public void testValidateCustomerDetailsGoodAc2BadPin() {
		assertEquals(ResultStatus.INVALD_CUSTOMER_CREDENTIALS,
				atmService.validateCustomerAccountDetails("987654321", "0000").getResultStatus());
	}

	@Test
	public void testValidateCustomerDetailsBadAcBadPin2() {
		assertEquals(ResultStatus.INVALD_CUSTOMER_CREDENTIALS,
				atmService.validateCustomerAccountDetails("BadAccountNo", "4321").getResultStatus());
	}
	
	@Test
	public void testWithdrawalOk1() {
		Wallet wallet = atmService.withdrawCash("123456789", new Withdrawal(900));
		List<NoteNQuantity> money = wallet.getMoney();
		assertEquals(1, money.size());
		NoteNQuantity item = money.get(0);
		assertEquals(50, item.getDenomination().intValue());
		assertEquals(18, item.getQuantity().intValue());
	}

	@Test
	public void testCustomerBalanceExceeded() {
		Wallet wallet = atmService.withdrawCash("123456789", new Withdrawal(1100));
		assertEquals(ResultStatus.EXCEEDS_CUSTOMER_BALANCE, wallet.getResultStatus());
	}
	
	@Test
	public void testInquireBalanceOk() {
		Balance balance = atmService.inquireBalance("123456789");
		assertEquals(800, balance.getBalance().intValue());
		assertEquals(200, balance.getOverdraft().intValue());
	}
	
	@Test
	public void testInquireBalanceFail() {
		Balance balance = atmService.inquireBalance("1234");
		assertNull(balance.getBalance());
		assertNull(balance.getOverdraft());
	}
}
