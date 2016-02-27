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

        Firebase.setAndroidContext(getContext());

        mResponseTxt = (TextView) view.findViewById(R.id.response_txt);
        mLoginProgressBar = (ProgressBar) view.findViewById(R.id.login_progressBar);

        mEmailEdit = (EditText) view.findViewById(R.id.email_edit);
        mEmailEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mEmailEdit.setText("dgblanks@gmail.com"); //temp for testing
            }
        });

        mPasswordEdit = (EditText) view.findViewById(R.id.password_edit);
        mPasswordEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                mPasswordEdit.setText("test"); //temp passwrod for testing
            }
        });

        mLoginButton = (Button) view.findViewById(R.id.login_btn);
        mLoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                DataProvider db = new DataProvider();
                ref = db.getInstance();

                String email = mEmailEdit.getText().toString();
                String pwd = mPasswordEdit.getText().toString();

                //TODO: implement progress bar
                //Attempt Login
                if(mLoginButton.getText().toString().equals("Login"))
                {
                    ref.authWithPassword(email, pwd, new Firebase.AuthResultHandler()
                    {
                        @Override
                        public void onAuthenticated(AuthData authData) {
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
                else if (mLoginButton.getText().toString().equals("Sign Up"))
                {
                    //Attempt to create a new user
                    ref.createUser(email, pwd, new Firebase.ValueResultHandler<Map<String, Object>>()
                    {
                        @Override
                        public void onSuccess(Map<String, Object> result)
                        {
                            //TODO: log user in with first-time welcome screen
                            Object o = result.get("uid");
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

        mNewUserTxt = (TextView) view.findViewById(R.id.newUser_txt);
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
}
