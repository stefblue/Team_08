package com.swt.amc.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

}
