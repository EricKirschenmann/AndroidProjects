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
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.majorassets.betterhalf.Database.DataProvider;


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

    private Firebase ref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        //TODO: somehow "wipe" savedInstanceState
        //TODO: don't allow backward navigation to Login screen
        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        initializeUIComponents(view);

        Firebase.setAndroidContext(getContext());

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
                mPasswordEdit.setText("test"); //temp passwrod for testing
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
            public void onClick(View v) {
                DataProvider db = DataProvider.getDataProvider();
                ref = db.getFirebaseInstance();

                final String email = mEmailEdit.getText().toString();
                final String pwd = mPasswordEdit.getText().toString();

                //TODO: implement progress bar
                //Attempt Login
                if(mLoginButton.getText().toString().equals("Login"))
                    authenticateWithPassword(email, pwd);
                else if (mLoginButton.getText().toString().equals("Sign Up"))
                {
                    //Attempt to create a new user
                    ref.createUser(email, pwd, new Firebase.ValueResultHandler<Map<String, Object>>()
                    {
                        @Override
                        public void onSuccess(Map<String, Object> result)
                        {
                            //TODO: log user in with first-time welcome screen
                            mLoginButton.setText(R.string.login_txt);
                            authenticateWithPassword(email, pwd);
                        }

                        @Override
                        public void onError(FirebaseError firebaseError)
                        {
                            //TODO: handle account creation errors
                            mResponseTxt.setText(firebaseError.getMessage());
                        }
                    });
                }
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

        return view;
    }

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

    //use Firebase user authentication with an email and password
    private void authenticateWithPassword(String email, String password)
    {
        ref.authWithPassword(email, password, new Firebase.AuthResultHandler()
        {
            @Override
            public void onAuthenticated(AuthData authData) {
                GlobalResources.userID = authData.getUid();
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
}
