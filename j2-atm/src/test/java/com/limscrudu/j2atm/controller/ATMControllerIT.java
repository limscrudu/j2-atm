package com.limscrudu.j2atm.controller;


import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.limscrudu.j2atm.J2AtmApplication;
import com.limscrudu.j2atm.model.Withdrawal;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = J2AtmApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ATMControllerIT {
	
	@LocalServerPort
	private int port;
	
	TestRestTemplate restTemplate = new TestRestTemplate();
	
	HttpHeaders headers = new HttpHeaders();

	@Test
	public void test_A_InquireBalanceSuccess() throws Exception {
		
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/atm/services/inquireBalance/123456789/1234"),
				HttpMethod.GET, entity, String.class);
		
		String expected = "{\"resultStatus\":\"Request Succeeded\",\"balance\":800,\"overdraft\":200}";

		JSONAssert.assertEquals(expected, response.getBody(), false);

	}

	@Test
	public void test_B_InquireBalanceBadAccount() throws Exception {
		
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/atm/services/inquireBalance/1234/1234"),
				HttpMethod.GET, entity, String.class);
		
		String expected = "{\"resultStatus\":\"Invald Customer Credentials\",\"balance\":null,\"overdraft\":null}";

		JSONAssert.assertEquals(expected, response.getBody(), false);

	}
	
	@Test
	public void test_C_InquireBalanceBadPin() throws Exception {
		
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/atm/services/inquireBalance/123456789/0000"),
				HttpMethod.GET, entity, String.class);
		
		String expected = "{\"resultStatus\":\"Invald Customer Credentials\",\"balance\":null,\"overdraft\":null}";

		JSONAssert.assertEquals(expected, response.getBody(), false);

	}

	@Test
	public void test_D_WithdrawCashSuccess() throws Exception {
		
		Withdrawal withdrawal = new Withdrawal(900);
		
		HttpEntity<Withdrawal> entity = new HttpEntity<Withdrawal>(withdrawal, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/atm/services/withdrawCash/123456789/1234"),
				HttpMethod.POST, entity, String.class);
		
		String expected = "{\"resultStatus\":\"Request Succeeded\",\"money\":[{\"denomination\":50,\"quantity\":18}]}";

		JSONAssert.assertEquals(expected, response.getBody(), false);

		
	}
	
	
	@Test
	public void test_E_WithdrawCashMaxATMAmountExceeded() throws Exception {
		
		Withdrawal withdrawal = new Withdrawal(1200);
		
		HttpEntity<Withdrawal> entity = new HttpEntity<Withdrawal>(withdrawal, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/atm/services/withdrawCash/987654321/4321"),
				HttpMethod.POST, entity, String.class);
		
		String expected = "{\"resultStatus\":\"Withdrawal amount exceeds available cash\",\"money\":[]}";

		JSONAssert.assertEquals(expected, response.getBody(), false);
		
	}
	
	
	@Test
	public void test_F_WithdrawCashCustomerBalanceExceeded() throws Exception {
		
		Withdrawal withdrawal = new Withdrawal(500);
		
		HttpEntity<Withdrawal> entity = new HttpEntity<Withdrawal>(withdrawal, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/atm/services/withdrawCash/123456789/1234"),
				HttpMethod.POST, entity, String.class);
		
		String expected = "{\"resultStatus\":\"Withdrawal amount exceeds customer balance\",\"money\":[]}";

		JSONAssert.assertEquals(expected, response.getBody(), false);

		
	}
	
	@Test
	public void test_G_WithdrawCashExactAmountUnavailable() throws Exception {
		
		Withdrawal withdrawal = new Withdrawal(11);
		
		HttpEntity<Withdrawal> entity = new HttpEntity<Withdrawal>(withdrawal, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/atm/services/withdrawCash/123456789/1234"),
				HttpMethod.POST, entity, String.class);
		
		String expected = "{\"resultStatus\":\"Exact withdrawal amount cannot be dispensed\",\"money\":[]}";

		JSONAssert.assertEquals(expected, response.getBody(), false);

		
	}
	
	
	
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}


}
