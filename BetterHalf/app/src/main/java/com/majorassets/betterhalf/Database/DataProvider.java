package com.majorassets.betterhalf.Database;

import com.firebase.client.Firebase;
import com.majorassets.betterhalf.Model.BaseDataItem;

import java.util.List;

/**
 * Created by dgbla on 2/20/2016.
 */
public class DataProvider
{
    private static DataProvider sDataProvider;

    public static final String FIREBASE_URL = "https://betterhalf.firebaseio.com/";
    private Firebase instance;
    private Firebase userInstance;

    //singleton firebase reference
    private DataProvider()
    {
        instance = new Firebase(FIREBASE_URL);
    }

    public Firebase getFirebaseInstance()
    {
        return instance;
    }

    public Firebase getUserInstance(String username)
    {
        return new Firebase(FIREBASE_URL + "users/" + username);
    }

    public static DataProvider getDataProvider()
    {
        if(sDataProvider == null)
            return new DataProvider();
        return sDataProvider;
    }
}
