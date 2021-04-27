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
        List<IUser> users = GetUsers();

        for(IUser user : users){
            if (username == user.GetUsername()) {
                if (user.IsCorrectPassword(password)) {
                    return true;
                } else {
                return false;
                }
            }
        }
        return false;
    }
}
