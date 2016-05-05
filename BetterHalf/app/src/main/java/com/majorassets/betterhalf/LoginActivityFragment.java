package com.majorassets.betterhalf;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.majorassets.betterhalf.Database.Firebase.FirebaseProvider;
import com.majorassets.betterhalf.Database.Firebase.FirebaseStructure;
import com.majorassets.betterhalf.Database.SQLite.SQLiteProvider;
import com.majorassets.betterhalf.Database.SQLite.SQLiteUserDAL;
import com.majorassets.betterhalf.Model.BaseLikeableItem;
import com.majorassets.betterhalf.Model.SubcategoryType;
import com.majorassets.betterhalf.Model.User;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivityFragment extends Fragment {

    //UI components
    private Button mLoginButton;
    private EditText mEmailEdit;
    private EditText mPasswordEdit;
    private TextView mNewUserTxt;
    private TextView mForgotPwdTxt;
    private TextView mResponseTxt;

    //DB componenets
    private FirebaseProvider firebaseDB;
    private SQLiteProvider sqliteDB;
    private SQLiteUserDAL dal;
    private Firebase mRootRef;
    private Firebase userRef;

    //User components
    private User appUser;
    private String mEmail;
    private String mPassword;
    private String mUsername;

    //labels
    private String mNewUserLbl;
    private String mExistingLbl;
    private String mSignUpLbl;
    private String mLoginLbl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        //setup
        initializeComponents(view);
        createEvents();

        //for Firebase
        Firebase.setAndroidContext(getContext().getApplicationContext());

        //data providers
        firebaseDB = FirebaseProvider.getDataProvider();
        sqliteDB = SQLiteProvider.getSQLiteProvider(getContext());

        //to query sqlite firebaseDB
        dal = new SQLiteUserDAL(sqliteDB.getDatabase());

        return view;
    }

    //wire up all the view components from the layout XMLs
    private void initializeComponents(View view)
    {
        mNewUserLbl = getResources().getString(R.string.newuser_txt);
        mExistingLbl = getResources().getString(R.string.existing_txt);
        mLoginLbl = getResources().getString(R.string.login_txt);
        mSignUpLbl = getResources().getString(R.string.signup_txt);

        mResponseTxt = (TextView) view.findViewById(R.id.response_txt);
        mNewUserTxt = (TextView) view.findViewById(R.id.newUser_txt);
        mForgotPwdTxt = (TextView) view.findViewById(R.id.forgot_pwd_txt);

        mEmailEdit = (EditText) view.findViewById(R.id.email_edit);
        mPasswordEdit = (EditText) view.findViewById(R.id.password_edit);
        mLoginButton = (Button) view.findViewById(R.id.login_btn);
    }

    //establish event listeners as anonymous inner classes
    private void createEvents()
    {
        ////// SETTING ONCLICK LISTENERS ////////
        mEmailEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mEmailEdit.setText("ranema89@gmail.com"); //temp for testing
            }
        });

        mPasswordEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                mPasswordEdit.setText("pass"); //temp password for testing
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
                //alternate text depending on which is clicked
                if(mNewUserTxt.getText().toString().equals(mNewUserLbl))
                {
                    mLoginButton.setText(R.string.signup_txt);
                    mNewUserTxt.setText(mExistingLbl);
                    mForgotPwdTxt.setText("");
                }
                else if(mNewUserTxt.getText().toString().equals(mExistingLbl))
                {
                    mLoginButton.setText(R.string.login_txt);
                    mNewUserTxt.setText(mNewUserLbl);
                    mForgotPwdTxt.setText(R.string.forgot_pwd_txt);
                }
            }
        });

        mForgotPwdTxt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //TODO: dialog for new password
            }
        });
    }

    //either login or create an account
    private void attemptLogin()
    {
        mRootRef = firebaseDB.getFirebaseInstance();

        mEmail = mEmailEdit.getText().toString();
        mPassword = mPasswordEdit.getText().toString();
        //generated username based off email
        mUsername = LoginHelperActivity.generateUsername(mEmail);

        //Attempt Login
        if(mLoginButton.getText().toString().equals(mLoginLbl))
            //pass in 'false' showing that this is the initial login
            loginWithPassword(mEmail, mPassword, false);
        else if (mLoginButton.getText().toString().equals(mSignUpLbl))
            createNewAccount(mEmail, mPassword);
    }

    //use Firebase user authentication with an email and password
    private void loginWithPassword(String email, String password, final boolean postCreationLogin)
    {
        //first check if user with specified email exists in SQLite db
        appUser = dal.getUser(email);
        GlobalResources.AppUser = appUser;

        //if user is not in SQLite OR this a login after creating an account
        if(appUser == null || postCreationLogin)
        {
            mRootRef.authWithPassword(email, password, new Firebase.AuthResultHandler()
            {
                @Override
                public void onAuthenticated(AuthData authData)
                {

                    //create new user
                    appUser = new User(mEmail, mPassword);
                    appUser.setID(UUID.fromString(authData.getUid()));
                    appUser.setLoggedOnLast(true);
                    appUser.setDataItems(new HashMap<SubcategoryType, List<BaseLikeableItem>>());

                    GlobalResources.AppUser = appUser;

                    //add user to SQLite db
                    dal.addUser(appUser);

                    createNewUserFirebaseStructure(appUser);

                    //go to home screen
                    startHomeActivity();
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError)
                {
                    //TODO: handle invalid credentials or no account
                    mResponseTxt.setText(firebaseError.getMessage());
                }
            });
        }
        else
        {
            appUser.setLoggedOnLast(true);
            appUser.setDataItems(new HashMap<SubcategoryType, List<BaseLikeableItem>>());

            dal.updateUser(appUser);


            userRef = firebaseDB.getUserInstance(mUsername);
            userRef.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    if(dataSnapshot.getValue() == null)
                        createNewAccount(mEmail, mPassword);

                    else
                    {
                        if(!mEmail.equals(appUser.getEmail()))
                            mResponseTxt.setText(R.string.invalid_email);
                        else if(!mPassword.equals(appUser.getPassword()))
                            mResponseTxt.setText(R.string.invalid_password);
                        else
                            startHomeActivity();
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError)
                {

                }
            });

        }
    }

    //use Firebase account creation with email and password
    private void createNewAccount(String email, String password)
    {
        //Attempt to create a new user
        mRootRef.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>()
        {
            @Override
            public void onSuccess(Map<String, Object> result)
            {
                //TODO: log user in with first-time welcome screen
                mLoginButton.setText(R.string.login_txt);

                //Ensure we only add User to SQLite database once
                boolean shouldLogin = false;
                if(appUser == null)
                {
                    shouldLogin = true;
                    appUser = new User();
                    GlobalResources.AppUser = appUser;

                    appUser.setEmail(mEmail);
                    appUser.setPassword(mPassword);
                    appUser.setLoggedOnLast(true);
                }

                if(shouldLogin)
                    //automatic login after account creation
                    loginWithPassword(mEmail, mPassword, true);
            }

            @Override
            public void onError(FirebaseError firebaseError)
            {
                //TODO: handle account creation errors
                mResponseTxt.setText(firebaseError.getMessage());
            }
        });
    }

    private void createNewUserFirebaseStructure(User user)
    {
        /*
        Create new user in Firebase, with username child of "users", info being child of "username",
        and specific data "id" and "email" being children of "info"
        */

        Firebase usersRef = mRootRef.child(FirebaseStructure.USERS);

        Map<String, Map<String, String>> newUserInfoMap = new HashMap<>();
        Map<String, String> newUserDataMap = new HashMap<>();

        newUserDataMap.put(FirebaseStructure.EMAIL, mEmail);
        newUserDataMap.put(FirebaseStructure.ID, appUser.getID().toString());
        newUserInfoMap.put(FirebaseStructure.INFO, newUserDataMap);

        String newUsername = LoginHelperActivity.generateUsername(mEmail);
        Firebase newUserRef = usersRef.child(newUsername);
        newUserRef.setValue(newUserInfoMap);
    }

    private void startHomeActivity()
    {
        Intent homeIntent = new Intent(this.getContext(), HomeActivity.class);
        startActivity(homeIntent);
    }
}
