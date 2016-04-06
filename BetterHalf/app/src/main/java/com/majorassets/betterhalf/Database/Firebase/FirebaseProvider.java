package com.majorassets.betterhalf.Database.Firebase;

import com.firebase.client.Firebase;

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
    private Firebase subcategoryInstance;

    //singleton firebase reference
    private FirebaseProvider()
    {
        instance = new Firebase(FIREBASE_URL);
    }

    public Firebase getFirebaseInstance()
    {
        return instance;
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

    //return all the attributes of the subcategories
    public Firebase getSubcategories(String mainCategory)
    {
        subcategoryInstance = new Firebase(FIREBASE_URL + "mainCategories/" + mainCategory.toLowerCase());
        return subcategoryInstance;
    }

    public static FirebaseProvider getDataProvider()
    {
        if(sDataProvider == null)
            sDataProvider = new FirebaseProvider();

        return sDataProvider;
    }
}
