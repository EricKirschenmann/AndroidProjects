package com.majorassets.betterhalf.Model;

import com.majorassets.betterhalf.Database.DataItemRepository;

import java.util.List;
import java.util.UUID;


/**
 * Created by dgbla on 2/28/2016.
 */
public class User
{
    private UUID ID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String username;

    private boolean loggedOnLast;

    private DataItemRepository mDataItemRepository;

    public User()
    {
        //TODO: hash password
    }

    public User(UUID ID)
    {
        this.ID = ID;
    }

    public User(String email, String password)
    {
        this.email = email;
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
{
    return lastName;
}

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public DataItemRepository getDataItemRepository()
    {
        return mDataItemRepository;
    }

    public void setDataItemRepository(DataItemRepository dataItemRepository)
    {
        mDataItemRepository = dataItemRepository;
    }

    public UUID getID()
    {
        return ID;
    }

    public void setID(UUID ID)
    {
        this.ID = ID;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public boolean isLoggedOnLast()
    {
        return loggedOnLast;
    }

    public void setLoggedOnLast(boolean loggedOnLast)
    {
        this.loggedOnLast = loggedOnLast;
    }
}
