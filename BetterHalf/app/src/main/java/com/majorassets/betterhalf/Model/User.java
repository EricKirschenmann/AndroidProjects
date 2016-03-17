package com.majorassets.betterhalf.Model;

import com.majorassets.betterhalf.Database.DataItemRepository;

import java.util.List;

/**
 * Created by dgbla on 2/28/2016.
 */
public class User
{
    private String firstName;
    private String lastName;
    private String email;

    private DataItemRepository mDataItemRepository;

    public User()
    {
        firstName = "John";
        lastName = "Doe";
        email = "jdoe@test.com";
        mDataItemRepository = DataItemRepository.getDataItemRepository();
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

    public DataItemRepository getDataItemRepository()
    {
        return mDataItemRepository;
    }

    public void setDataItemRepository(DataItemRepository dataItemRepository)
    {
        mDataItemRepository = dataItemRepository;
    }
}
