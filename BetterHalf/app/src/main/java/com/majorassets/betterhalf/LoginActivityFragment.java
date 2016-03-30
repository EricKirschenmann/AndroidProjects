package com.majorassets.betterhalf;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.majorassets.betterhalf.Database.DataItemRepository;
import com.majorassets.betterhalf.Database.DataProvider;
import com.majorassets.betterhalf.Model.BaseDataItem;
import com.majorassets.betterhalf.Model.Entertainment.MovieItem;
import com.majorassets.betterhalf.Model.SubcategoryType;
import com.majorassets.betterhalf.Model.User;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivityFragment extends Fragment {

    private Button mLoginButton;
    private EditText mEmailEdit;
    private EditText mPasswordEdit;
    private TextView mNewUserTxt;
    private TextView mResponseTxt;
    private ProgressBar mLoginProgressBar;

    private DataProvider db;
    private Firebase mRootRef;
    private Firebase mUserRef;
    private Firebase mUserDataRef;
    private String mEmail;
    private String mPassword;
    private String mUsername;

    private Map<SubcategoryType, List<BaseDataItem>> userDataList;

    private String mNewUserLbl;
    private String mExistingLbl;
    private String mSignUpLbl;
    private String mLoginLbl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        //TODO: somehow "wipe" savedInstanceState
        //TODO: save user credentials into SQlite so, after initial account creation, user logs in automatically
        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        initializeUIComponents(view);
        createAndControlEvents();
        Firebase.setAndroidContext(getContext());
        db = DataProvider.getDataProvider();
        return view;
    }

    //wire up all the view components from the layout XMLs
    private void initializeUIComponents(View view)
    {
        mNewUserLbl = getResources().getString(R.string.newuser_txt);
        mExistingLbl = getResources().getString(R.string.existing_txt);
        mLoginLbl = getResources().getString(R.string.login_txt);
        mSignUpLbl = getResources().getString(R.string.signup_txt);

        mResponseTxt = (TextView) view.findViewById(R.id.response_txt);
        mNewUserTxt = (TextView) view.findViewById(R.id.newUser_txt);
        mLoginProgressBar = (ProgressBar) view.findViewById(R.id.login_progressBar);

        mEmailEdit = (EditText) view.findViewById(R.id.email_edit);
        mPasswordEdit = (EditText) view.findViewById(R.id.password_edit);
        mLoginButton = (Button) view.findViewById(R.id.login_btn);

        userDataList = new HashMap<>();
    }

    //establish event listeners as anonymous inner classes
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
                }
                else if(mNewUserTxt.getText().toString().equals(mExistingLbl))
                {
                    mLoginButton.setText(R.string.login_txt);
                    mNewUserTxt.setText(mNewUserLbl);
                }
            }
        });
    }

    private void attemptLogin()
    {
        mRootRef = db.getFirebaseInstance();

        mEmail = mEmailEdit.getText().toString();
        mPassword = mPasswordEdit.getText().toString();
        mUsername = generateUsername(mEmail);

        //TODO: implement progress bar
        //Attempt Login
        if(mLoginButton.getText().toString().equals(mLoginLbl))
            loginWithPassword(mEmail, mPassword);
        else if (mLoginButton.getText().toString().equals(mSignUpLbl))
            createNewAccount(mEmail, mPassword);
    }

    //use Firebase user authentication with an email and password
    private void loginWithPassword(String email, String password)
    {
        mRootRef.authWithPassword(email, password, new Firebase.AuthResultHandler()
        {
            @Override
            public void onAuthenticated(AuthData authData) {
                //get the reference for a user's data and parse it out into HashMap
                mUserDataRef = db.getUserDataInstance(mUsername);
                getUserData(mUserDataRef);

                //TODO: read User object from SQLite
                User user = new User();
                user.setEmail(mEmail);

                // THIS IS TEMPORARY TO MOVE FORWARD - must be read from SQLite//
                DataItemRepository userRepo = DataItemRepository.getDataItemRepository();
                userRepo.setDataItems(userDataList);
                user.setDataItemRepository(userRepo);

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

    private void createNewAccount(final String email, final String password)/**/
    {
        //Attempt to create a new user
        mRootRef.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>()
        {
            @Override
            public void onSuccess(Map<String, Object> result)
            {
                //TODO: log user in with first-time welcome screen
                mLoginButton.setText(R.string.login_txt);

                //TODO: store User object in SQLite
                User user = new User();
                user.setEmail(mEmail);

                loginWithPassword(mEmail, mPassword);
            }

            @Override
            public void onError(FirebaseError firebaseError)
            {
                //TODO: handle account creation errors
                mResponseTxt.setText(firebaseError.getMessage());
            }
        });
    }

    private void getUserData(Firebase ref)
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
        if(userDataList.get(subcategory) == null)
        {
            list = new ArrayList<>(); // use an empty list
            list.add(item);
            userDataList.put(subcategory, list); //create new entry for movies
        }
        else //add to an already define list
        {
            list = userDataList.get(subcategory);
            list.add(item);
        }
    }

    /* UTILITY */
    @NonNull
    /**
     * Derive a username from a user's email address
     */
    private String generateUsername(String email)
    {
        String emailProvider = email.substring(email.indexOf('@'), email.indexOf('.')); //e.g. yahoo, gmail
        email = email.substring(0, email.indexOf('@'));

        return email + emailProvider;
    }
}
