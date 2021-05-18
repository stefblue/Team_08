package com.swt.amc.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.swt.amc.api.UserInformation;
import com.swt.amc.exceptions.AmcException;
import com.swt.amc.interfaces.ILoginComponent;

@SpringBootTest
public class AuthenticationTests {

	@Autowired
	private ILoginComponent loginComponent;

	@Test
	public void authenticationTest() {
		assertTrue(loginComponent.isValidAuthentication("admin", "password"));
	}

	@Test
	public void authenticationFailureTest() {
		assertFalse(loginComponent.isValidAuthentication("notUser", "somePassword"));
	}

	@Test
	public void getUserNameAndPasswordTest() throws AmcException {
		UserInformation userInfo = loginComponent.getUserInformationForUsernameAndPassword("admin", "password");
		assertNotNull(userInfo);
		assertEquals(userInfo.getGivenName(), "Franz");
		assertEquals(userInfo.getLastName(), "Bauer");
	}

	@Test()
	public void getNoLinkTest() {
		try {
			loginComponent.getUserInformationForUsernameAndPassword("notUser", "somePassword");
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof AmcException);
		}
	}

}
