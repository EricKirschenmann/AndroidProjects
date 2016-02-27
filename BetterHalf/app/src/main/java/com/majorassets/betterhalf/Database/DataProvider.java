package com.majorassets.betterhalf.Database;

import com.firebase.client.Firebase;

/**
 * Created by dgbla on 2/20/2016.
 */
public class DataProvider
{
    public static final String FIREBASE_URL = "https://betterhalf.firebaseio.com/";
    private Firebase instance;

    //singleton firbase reference
    public DataProvider()
    {
        if(instance == null)
            instance = new Firebase(FIREBASE_URL);
    }

    public Firebase getInstance()
    {
        return instance;
    }
}
