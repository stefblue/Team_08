package com.swt.amc.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import com.swt.amc.api.UserInformation;
import com.swt.amc.exceptions.AmcException;
import com.swt.amc.interfaces.IValidateCredentialsComponent;
import com.swt.amc.repositories.IUserInformationRepository;

@SpringBootTest()
@TestInstance(Lifecycle.PER_CLASS)
public class UserInfoRepositoryAndAuthenticationTests {

	@Autowired
	private IUserInformationRepository repository;

	@Autowired
	@Qualifier("validateCredentialsComponent")
	private IValidateCredentialsComponent loginComponent;

	@BeforeAll
	public void setUpRepo() {
		repository.deleteAll();
		UserInformation userInformation = new UserInformation();
		userInformation.setUserName("admin");
		userInformation.setGivenName("Franz");
		userInformation.setLastName("Bauer");
		userInformation.setPassword("password");
		repository.save(userInformation);
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
