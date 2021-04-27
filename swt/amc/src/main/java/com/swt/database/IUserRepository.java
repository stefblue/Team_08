package com.swt.database;


import java.util.List;

public interface IUserRepository {
    public boolean isAdmin(String username, String password);
    public boolean isUser(String username, String password);
    public List<IUser> GetUsers();
}
