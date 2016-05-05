package com.majorassets.betterhalf.Model;

import com.majorassets.betterhalf.LoginHelperActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private User significantOther;

    private Map<SubcategoryType, List<BaseLikeableItem>> dataItems;

    public User()
    {
        dataItems = new HashMap<>();
        //TODO: hash password
    }

    public User(UUID ID)
    {
        this.ID = ID;
        dataItems = new HashMap<>();
    }

    public User(String email, String password)
    {
        this.email = email;
        this.password = password;
        dataItems = new HashMap<>();
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
        if(email != null)
            return LoginHelperActivity.generateUsername(email);
        else
            return null;
    }

    public void setUsername(String username)
    {
        this.username = username;
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

    public User getSignificantOther()
    {
        return significantOther;
    }

    public void setSignificantOther(User significantOther)
    {
        this.significantOther = significantOther;
    }

    public Map<SubcategoryType, List<BaseLikeableItem>> getDataItems()
    {
        return dataItems;
    }

    public void setDataItems(Map<SubcategoryType, List<BaseLikeableItem>> dataItems)
    {
        this.dataItems = dataItems;
    }

    public boolean isConnected()
    {
        return significantOther != null;
    }
}
