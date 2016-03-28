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
    private Firebase userDataInstance;
    private Firebase subcategoryInstance;

    //singleton firebase reference
    private DataProvider()
    {
        instance = new Firebase(FIREBASE_URL);
    }

    public Firebase getFirebaseInstance()
    {
        return instance;
    }

    //return the info for a user
    public Firebase getUserInstance(String username)
    {
        userInstance = new Firebase(FIREBASE_URL + "users/" + username + "/info");
        return userInstance;
    }

    //return the data for a user
    public Firebase getUserDataInstance(String username)
    {
        userDataInstance = new Firebase(FIREBASE_URL + "users/" + username + "/data");
        return userDataInstance;
    }

    //return all the attributes of the subcategories
    public Firebase getSubcategories(String mainCategory)
    {
        subcategoryInstance = new Firebase(FIREBASE_URL + "mainCategories/" + mainCategory.toLowerCase());
        return subcategoryInstance;
    }

    public static DataProvider getDataProvider()
    {
        if(sDataProvider == null)
            return new DataProvider();
        return sDataProvider;
    }
}
