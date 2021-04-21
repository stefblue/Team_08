package com.swt.database;

public interface IUserRepository {
    public boolean isAdmin(String username, String password);
}
