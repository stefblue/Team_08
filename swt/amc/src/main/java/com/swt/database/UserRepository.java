package com.swt.database;


class UserRepository implements IUserRepository {
    public boolean isAdmin(String username, String password) {
        //Function Logic without Database Queries
        boolean admin;
        if(isUser(username, password))
        {
            if(admin)
            {
                return true;
            }
        }
        return false;

    }

    public boolean isUser(String username, String password) {
        //Function Logic without Database Queries
        String database_username;
        String username_password;


        if (username == database_username)
        {
            if (password == username_password)
            {
                return true;
            }
        }
    }
}
