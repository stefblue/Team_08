package com.swt.database;

public interface IUser {
    public String GetUsername();
    public boolean IsCorrectPassword(String password);
    public boolean IsAdmin();
}
