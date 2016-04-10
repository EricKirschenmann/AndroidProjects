package com.majorassets.betterhalf;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.majorassets.betterhalf.Database.DataItemRepository;
import com.majorassets.betterhalf.Database.Firebase.FirebaseProvider;
import com.majorassets.betterhalf.Database.SQLite.SQLiteProvider;
import com.majorassets.betterhalf.Database.SQLite.SQLiteUserDAL;
import com.majorassets.betterhalf.Model.BaseDataItem;
import com.majorassets.betterhalf.Model.Entertainment.MovieItem;
import com.majorassets.betterhalf.Model.Entertainment.MusicItem;
import com.majorassets.betterhalf.Model.Subcategory;
import com.majorassets.betterhalf.Model.SubcategoryType;
import com.majorassets.betterhalf.Model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginHelperActivity extends AppCompatActivity
{
    private FirebaseProvider firebaseDB;
    private SQLiteProvider sqliteDB;
    private SQLiteUserDAL dal;

    private User appUser;
    private Map<SubcategoryType, List<BaseDataItem>> appUserData;
    private DataItemRepository userRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);

        //data providers
        firebaseDB = FirebaseProvider.getDataProvider();
        sqliteDB = SQLiteProvider.getSQLiteProvider(this.getApplicationContext());

        //to query sqlite
        dal = new SQLiteUserDAL(sqliteDB.getDatabase());

        //these represent all users that have ever logged in on the same device
        GlobalResources.Users = dal.getAllUsers();
        //this represents the global user - the one logged in, using the app - accessible from anywhere
        GlobalResources.AppUser = getLastLoggedInUser(GlobalResources.Users);

        //first check if SQLite had user data
        if(GlobalResources.AppUser != null)
        {
            appUser = GlobalResources.AppUser;
            //initialize repository
            userRepo = DataItemRepository.getDataItemRepository();
            //initialize item hashmap
            userRepo.setDataItems(new HashMap<SubcategoryType, List<BaseDataItem>>());
            //assign repo to app user
            appUser.setDataItemRepository(userRepo);
            //use variable for data instead of get method...
            appUserData = userRepo.getDataItems();

            //generate the username from user's email
            String username = generateUsername(appUser.getEmail());
            appUser.setUsername(username);

            //retrieve datasnapshot of user instance (includes info and data sub trees)
            Firebase ref = firebaseDB.getUserInstance(username);

            try
            {
                ref.addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        //if user is not in firebase, launch new login page
                        if(dataSnapshot.getValue() == null)
                            startLoginActivity();
                        //otherwise retrieve the user's data and go straight to the home screen
                        else
                        {
                            getUserData(firebaseDB.getUserDataInstance(appUser.getUsername()));
                            startHomeActivity();
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError)
                    {
                        Log.d("ERROR", firebaseError.getMessage());
                    }
                });
            }
            catch (Exception e)
            {
                throw e;
            }
        }
        //if SQLite is empty (of users) then launch Login screen
        else
            startLoginActivity();
    }

    //Ensure that we can only return one last logged in user
    private User getLastLoggedInUser(List<User> users)
    {
        if(users == null)
            return null;

        if(users.isEmpty())
            return null;

        for(User user : users)
        {
            if(user.isLoggedOnLast())
                return user;
        }

        return null;
    }

    private void startHomeActivity()
    {
        Intent homeScreen = new Intent(LoginHelperActivity.this, HomeActivity.class);
        homeScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeScreen);
        finish();
    }

    private void startLoginActivity()
    {
        Intent loginScreen = new Intent(LoginHelperActivity.this, LoginActivity.class);
        loginScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginScreen);
        finish();
    }

    public void getUserData(Firebase ref)
    {
        ref.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                String parent;
                DataSnapshot next;
                SubcategoryType subcategory;
                BaseDataItem item;
                //"drill down" to leaf nodes
                while(dataSnapshot.hasChildren())
                {
                    parent = dataSnapshot.getKey();
                    next = dataSnapshot.getChildren().iterator().next();
                    subcategory = SubcategoryType.getTypeFromString(parent);
                    switch (subcategory)
                    {
                        //TODO: parse out datasnapshot into separate objects
                        case MOVIE:
                            item = new MovieItem(next.getKey(), next.getValue().toString());
                            addDataItem(subcategory, item);
                            dataSnapshot = next;
                            break;
                        case MUSIC:
                            item = new MusicItem(next.getKey(), next.getValue().toString());
                            addDataItem(subcategory, item);
                            dataSnapshot = next;
                            break;
                        default:
                            dataSnapshot = next; //parent was some other folder; keep going
                            break; //error check here
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError)
            {

            }
        });
    }

    private void addDataItem(SubcategoryType subcategory, BaseDataItem item)
    {
        List<BaseDataItem> list;
        //if there are no entries for a movie then the list will be null
        if(appUserData.get(subcategory) == null)
        {
            list = new ArrayList<>(); // use an empty list
            list.add(item);
            appUserData.put(subcategory, list); //create new entry for movies
        }
        else //add to an already define list
        {
            list = appUserData.get(subcategory);
            list.add(item);
        }
    }

    /* UTILITY */
    @NonNull
    /**
     * Derive a username from a user's email address
     */
    public static String generateUsername(String email)
    {
        String emailProvider = email.substring(email.indexOf('@') + 1, email.indexOf('.')); //e.g. yahoo, gmail
        email = email.substring(0, email.indexOf('@'));

        return email + emailProvider;
    }
}
