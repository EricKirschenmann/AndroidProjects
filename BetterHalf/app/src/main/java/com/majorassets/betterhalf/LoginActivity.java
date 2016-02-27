package com.majorassets.betterhalf;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.ui.auth.core.AuthProviderType;
import com.firebase.ui.auth.core.FirebaseLoginBaseActivity;
import com.firebase.ui.auth.core.FirebaseLoginError;

public class LoginActivity extends AppCompatActivity //extends FirebaseLoginBaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ///////////// BUTTON TO DISPLAY LOGIN PROMPT /////////////
        /*mBtnLogin = (Button) findViewById(R.id.btnLogin);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFirebaseLoginPrompt();
            }
        });

        Firebase.setAndroidContext(this);*/

        ////////// ADDING FRAGMENT PROGRAMMATICALLY ///////
        /*FragmentManager fm = getSupportFragmentManager();

        //retrieve the fragment if it exists in the FragmentManager
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        //if fragment is brand new (null), instantiate a new instance and add it the FragmentManager
        //beginTransaction creates and returns a new FragmentTransaction
        if (fragment == null) {
            fragment = new LoginActivityFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }*/
    }


    ///////////////// EVENTS FOR FIREBASE LOGIN DIALOG ////////////////////////
    /*@Override
    protected void onStart() {
        super.onStart();

        setEnabledAuthProvider(AuthProviderType.PASSWORD);
    }

    @Override
    protected Firebase getFirebaseRef() {
        //TOD: get a reference to betterhalf.firebaseio.com
        if(ref == null)
            ref = new Firebase("https://betterhalf.firebaseio.com/");

        return ref;
    }

    @Override
    protected void onFirebaseLoginProviderError(FirebaseLoginError firebaseLoginError) {

    }

    @Override
    protected void onFirebaseLoginUserError(FirebaseLoginError firebaseLoginError) {

    }

    @Override
    public void onFirebaseLoggedIn(AuthData data) {
        // TOD: handle successful login
    }

    @Override
    public void onFirebaseLoggedOut() {
        // TOD: handle successful logout
    }*/
}
