package com.swt.database.test;

import com.swt.database.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRepositoryTests {
    @Autowired
    private IUserRepository userRepository;

    @Test
    public void isAdmin_admitsValidAdmin(){
        String validAdminUsername = "admin";
        String validAdminPassword = "admin";
        boolean userIsAdmitted = userRepository.isAdmin(validAdminUsername, validAdminPassword);
        Assert.assertEquals(userIsAdmitted, true);
    }

    @Test
    public void isUser_admitsValidUser(){
        String validUsername = "validUser";
        String validPassword = "validUserPassword";
        boolean userIsAdmitted = userRepository.isUser(validUsername, validPassword);
        Assert.assertEquals(userIsAdmitted, true);
    }

    @Test
    public void isUser_rejectsInvalidUser(){
        String invalidUsername = "invalidUser";
        String invalidPassword = "invalidUserPassword";
        boolean userIsAdmitted = userRepository.isUser(invalidUsername, invalidPassword);
        Assert.assertEquals(userIsAdmitted, false);
    }
}
