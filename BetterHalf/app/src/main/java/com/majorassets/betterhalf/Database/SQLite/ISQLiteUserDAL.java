package com.majorassets.betterhalf.Database.SQLite;

import com.majorassets.betterhalf.Model.User;

/**
 * Created by dgbla on 3/30/2016.
 */
public interface ISQLiteUserDAL
{
    void addUser(User user);
    void updateUser(User user);
    User deleteUser(User user);
}
