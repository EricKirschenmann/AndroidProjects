package com.majorassets.betterhalf;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.majorassets.betterhalf.Database.Firebase.FirebaseProvider;
import com.majorassets.betterhalf.Database.SQLite.SQLiteItemsDAL;
import com.majorassets.betterhalf.Database.SQLite.SQLiteProvider;
import com.majorassets.betterhalf.Database.SQLite.SQLiteUserDAL;
import com.majorassets.betterhalf.Model.BaseLikeableItem;
import com.majorassets.betterhalf.Model.SubcategoryType;
import com.majorassets.betterhalf.Model.User;

import java.util.HashMap;
import java.util.List;


public class LoginHelperActivity extends AppCompatActivity
{
    private Firebase currentUserRef; //instance for the app user
    private Firebase soUserRef; //instance for the significant other

    private FirebaseProvider firebaseDB;
    private SQLiteProvider sqliteDB;
    private SQLiteUserDAL userDAL;
    private SQLiteItemsDAL itemsDAL;

    private User appUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);

        //data providers
        firebaseDB = FirebaseProvider.getDataProvider();
        sqliteDB = SQLiteProvider.getSQLiteProvider(this.getApplicationContext());

        //to query sqlite
        userDAL = new SQLiteUserDAL(sqliteDB.getDatabase());
        itemsDAL = new SQLiteItemsDAL(sqliteDB.getDatabase());

        //these represent all users that have ever logged in on the same device
        GlobalResources.Users = userDAL.getAllUsers();
        //this represents the global user - the one last logged in, using the app - accessible from anywhere
        GlobalResources.AppUser = getLastLoggedInUser(GlobalResources.Users);

        //first check if SQLite had user data (someone was last logged in on device)
        if(GlobalResources.AppUser != null)
        {
            appUser = GlobalResources.AppUser;

            //generate the username from user's email
            String username = appUser.getUsername();
            appUser.setUsername(username);

            currentUserRef = firebaseDB.getUserInstance(username);

            //retrieve user's data if they are not connected
            if (!appUser.isConnected())
                readUserDataFromSQLite(appUser);
            else
            {
                assignSignficantOther(username);
                readUserDataFromSQLite(appUser.getSignificantOther());
            }


            //continue with starting home screen or login screen
            try
            {
                currentUserRef.addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        //if user is not in firebase, launch new login page
                        if (dataSnapshot.getValue() == null)
                            startLoginActivity();
                            //go straight to the home screen
                        else
                            startHomeActivity();
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError)
                    {
                        Log.d("ERROR", firebaseError.getMessage());
                    }
                });
            } catch (Exception e)
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

    public void readUserDataFromSQLite(User user)
    {
        if(user.getEmail() != null)
        {
            try
            {
                if (user.getDataItems() == null)
                {
                    user.setDataItems(new HashMap<SubcategoryType, List<BaseLikeableItem>>());
                }

                itemsDAL.getAllUserEntertainmentItems(user.getID(), user.getDataItems());
                itemsDAL.getAllUserFashionItems(user.getID(), user.getDataItems());
                itemsDAL.getAllUserFoodItems(user.getID(), user.getDataItems());
                itemsDAL.getAllUserHobbyItems(user.getID(), user.getDataItems());
                itemsDAL.getAllUserMedicalItems(user.getID(), user.getDataItems());
            } catch (Exception e)
            {
                Log.e("Error", e.getMessage());
            }
        }
    }

    private void setUpSignificantOther(String significantOther)
    {
        soUserRef = firebaseDB.getUserInstance(significantOther);
        soUserRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                User SO = appUser.getSignificantOther();
                SO.setUsername(dataSnapshot.getKey());
                SO.setEmail(dataSnapshot.child("info").child("email").getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError)
            {

            }
        });
    }

    private void assignSignficantOther(String username)
    {
        currentUserRef = firebaseDB.getUserInstance(username);
        currentUserRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                String significantOther = dataSnapshot.child("connection").child("user").getValue().toString();
                setUpSignificantOther(significantOther);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError)
            {

            }
        });
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

    /* UTILITY */
    @NonNull
    /**
     * Derive a username from a user's email address
     */
    public static String generateUsername(String email)
    {
        String emailProvider = email.substring(email.indexOf('@') + 1, email.lastIndexOf('.')); //e.g. yahoo, gmail
        email = email.substring(0, email.indexOf('@'));

        email = email.replace(".", "");

        return email + emailProvider;
    }
}
