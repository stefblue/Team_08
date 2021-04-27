package com.swt.database;


import java.util.List;

class UserRepository implements IUserRepository {

    public boolean isAdmin(String username, String password) {

        List<IUser> users = GetUsers();

        for(IUser user : users)
        {
            if(isUser(username, password))
            {
                if(user.IsAdmin())
                    return true;
                else return false;
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
