package com.majorassets.betterhalf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.firebase.client.Firebase;
import com.majorassets.betterhalf.Database.Firebase.FirebaseProvider;
import com.majorassets.betterhalf.Database.SQLite.SQLiteProvider;
import com.majorassets.betterhalf.Database.SQLite.SQLiteUserDAL;
import com.majorassets.betterhalf.Model.User;

public class ConnectionActivity extends AppCompatActivity {

    private User appUser;

    private SQLiteProvider sqliteDB;
    private SQLiteUserDAL dal;

    private Firebase ref;
    private FirebaseProvider firebaseDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        appUser = GlobalResources.AppUser;
        sqliteDB = SQLiteProvider.getSQLiteProvider(this);
        dal = new SQLiteUserDAL(sqliteDB.getDatabase());

        firebaseDB = FirebaseProvider.getDataProvider();
        ref = firebaseDB.getFirebaseInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_connection, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()) {
            case R.id.action_settings:
                intent = new Intent(ConnectionActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_home:
                intent = new Intent(ConnectionActivity.this, HomeActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_logout:
                //this user is is officially logged out - was NOT logged on last
                appUser.setLoggedOnLast(false);
                //update the user in SQLite
                dal.updateUser(appUser);

                //TODO: popup an "Are you sure?" dialog (fragment) and logout through Firebase API
                ref.unauth(); //un-authenticate a user from firebase
                //return to login screen
                intent = new Intent(ConnectionActivity.this, LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
