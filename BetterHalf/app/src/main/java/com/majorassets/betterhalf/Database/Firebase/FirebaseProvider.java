package com.majorassets.betterhalf.Database.Firebase;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.majorassets.betterhalf.Model.BaseDataItem;
import com.majorassets.betterhalf.Model.Entertainment.MovieItem;
import com.majorassets.betterhalf.Model.Entertainment.MusicItem;
import com.majorassets.betterhalf.Model.SubcategoryType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dgbla on 2/20/2016.
 */
public class FirebaseProvider
{
    private static FirebaseProvider sDataProvider;

    public static final String FIREBASE_URL = "https://betterhalf.firebaseio.com/";
    private Firebase instance;
    private Firebase userInstance;
    private Firebase userDataInstance;
    private Firebase allUsers;
    private Firebase subcategoryInstance;

    //singleton firebase reference
    private FirebaseProvider()
    {
        instance = new Firebase(FIREBASE_URL);
    }

    public static FirebaseProvider getDataProvider()
    {
        if(sDataProvider == null)
            sDataProvider = new FirebaseProvider();

        return sDataProvider;
    }

    public Firebase getFirebaseInstance()
    {
        return instance;
    }

    public Firebase getUserInstance(String username)
    {
        userInstance = new Firebase(FIREBASE_URL + "users/" + username);
        return userInstance;
    }

    public Firebase getAllUsersInstance()
    {
        allUsers = new Firebase(FIREBASE_URL + "users");
        return allUsers;
    }

    //return the info for a user
    public Firebase getUserInfoInstance(String username)
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

    public Firebase getUserConnectionInstance(String username)
    {
        userDataInstance = new Firebase(FIREBASE_URL + "users/" + username + "/connection");
        return userDataInstance;
    }

    public Firebase getUserDataSubcategoryInstance(String username, String subcategory)
    {
        subcategoryInstance = new Firebase(FIREBASE_URL + "users/" + username + "/data/" + subcategory);
        return subcategoryInstance;
    }

    //return all the attributes of the subcategories
    public Firebase getSubcategoryInstance(String mainCategory)
    {
        subcategoryInstance = new Firebase(FIREBASE_URL + "mainCategories/" + mainCategory.toLowerCase());
        return subcategoryInstance;
    }
}
