package com.majorassets.betterhalf;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.majorassets.betterhalf.Database.DataProvider;
import com.majorassets.betterhalf.Model.BaseDataItem;
import com.majorassets.betterhalf.Model.DataItem;
import com.majorassets.betterhalf.Model.Entertainment.MovieItem;
import com.majorassets.betterhalf.Model.LikeableItem;
import com.majorassets.betterhalf.Model.Subcategory;


import java.util.List;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivityFragment extends Fragment {

    private Button mLoginButton;
    private EditText mEmailEdit;
    private EditText mPasswordEdit;
    private EditText mUsernameEdit;
    private TextView mNewUserTxt;
    private TextView mResponseTxt;
    private ProgressBar mLoginProgressBar;

    private DataProvider db;
    private Firebase ref;
    private String mEmail;
    private String mPassword;

    private Map<Subcategory, Object> userDataList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        //TODO: somehow "wipe" savedInstanceState
        //TODO: don't allow backward navigation to Login screen
        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        initializeUIComponents(view);
        Firebase.setAndroidContext(getContext());
        db = DataProvider.getDataProvider();
        createAndControlEvents();
        return view;
    }

    //wire up all the view components from the layout XMLs
    private void initializeUIComponents(View view)
    {
        mResponseTxt = (TextView) view.findViewById(R.id.response_txt);
        mNewUserTxt = (TextView) view.findViewById(R.id.newUser_txt);
        mLoginProgressBar = (ProgressBar) view.findViewById(R.id.login_progressBar);

        mEmailEdit = (EditText) view.findViewById(R.id.email_edit);
        mPasswordEdit = (EditText) view.findViewById(R.id.password_edit);
        mUsernameEdit = (EditText) view.findViewById(R.id.username_edit);
        mLoginButton = (Button) view.findViewById(R.id.login_btn);
    }

    private void createAndControlEvents()
    {
        ////// SETTING ONCLICK LISTENERS ////////
        mEmailEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mEmailEdit.setText("dgblanks@gmail.com"); //temp for testing
            }
        });

        mPasswordEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                mPasswordEdit.setText("test"); //temp password for testing
            }
        });

        mUsernameEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mUsernameEdit.setText("dillon-blanksma"); //temp for testing
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                attemptLogin();
            }
        });

        mNewUserTxt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mLoginButton.setText(R.string.signup_txt);
            }
        });
    }

    private void attemptLogin()
    {
        ref = db.getFirebaseInstance();

        mEmail = mEmailEdit.getText().toString();
        mPassword = mPasswordEdit.getText().toString();

        //TODO: implement progress bar
        //Attempt Login
        if(mLoginButton.getText().toString().equals("Login"))
            LoginWithPassword(mEmail, mPassword);
        else if (mLoginButton.getText().toString().equals("Sign Up"))
            CreateNewAccount(mEmail, mPassword);
    }

    //use Firebase user authentication with an email and password
    private void LoginWithPassword(String email, String password)
    {
        ref.authWithPassword(email, password, new Firebase.AuthResultHandler()
        {
            @Override
            public void onAuthenticated(AuthData authData) {
                GlobalResources.Username = mUsernameEdit.getText().toString();

                //get the reference for this user
                ref = db.getUserInstance(GlobalResources.Username);
                GetUserData(ref);

                //start the home activity
                Intent homeIntent = new Intent(getContext(), HomeActivity.class);
                startActivity(homeIntent);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                //TODO: handle invalid credentials or no account
                mResponseTxt.setText(firebaseError.getMessage());
            }
        });
    }

    private void CreateNewAccount(final String email, final String password)
    {
        //Attempt to create a new user
        ref.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>()
        {
            @Override
            public void onSuccess(Map<String, Object> result)
            {
                //TODO: log user in with first-time welcome screen
                mLoginButton.setText(R.string.login_txt);
                LoginWithPassword(email, password);
            }

            @Override
            public void onError(FirebaseError firebaseError)
            {
                //TODO: handle account creation errors
                mResponseTxt.setText(firebaseError.getMessage());
            }
        });
    }

    private void GetUserData(Firebase ref)
    {
        ref.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                String parent = "";
                //"drill down" to leaf nodes
                while(dataSnapshot.hasChildren())
                {
                    parent = dataSnapshot.getKey();
                    dataSnapshot = dataSnapshot.getChildren().iterator().next();
                }

                //maybe create new reference from parent
                BaseDataItem item;
                //TODO: parse out datasnapshot into separate objects
                switch (Subcategory.getTypeFromString(parent))
                {
                    case MOVIE:
                        item = new MovieItem(dataSnapshot.getKey(), dataSnapshot.getValue().toString());
                        break;
                    default:
                        break; //error check here
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError)
            {

            }
        });
    }
}
