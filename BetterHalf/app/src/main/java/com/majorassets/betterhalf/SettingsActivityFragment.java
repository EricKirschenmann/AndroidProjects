package com.majorassets.betterhalf;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.majorassets.betterhalf.Database.Firebase.FirebaseProvider;
import com.majorassets.betterhalf.Database.SQLite.SQLiteProvider;
import com.majorassets.betterhalf.Database.SQLite.SQLiteUserDAL;
import com.majorassets.betterhalf.Model.User;

/**
 * A placeholder fragment containing a simple view.
 */
public class SettingsActivityFragment extends Fragment {
    private Button mDeleteAccountButton;
    private Button mSaveChangesButton;
    private EditText mFirstNameEdit;
    private EditText mLastNameEdit;

    private SQLiteProvider db;
    private SQLiteUserDAL dal;

    private FirebaseProvider firebaseDB;
    private Firebase rootRef;
    private Firebase userRef;

    private User appUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);

        initializeComponents(view);
        createEvents();

        db = SQLiteProvider.getSQLiteProvider(getContext());
        dal = new SQLiteUserDAL(db.getDatabase());

        firebaseDB = FirebaseProvider.getDataProvider();
        rootRef = firebaseDB.getAllUsersInstance();

        return view;
    }

    private void initializeComponents(View view) {
        appUser = GlobalResources.AppUser;

        mDeleteAccountButton = (Button) view.findViewById(R.id.delete_account_btn);
        mSaveChangesButton = (Button) view.findViewById(R.id.save_account_btn);
        mFirstNameEdit = (EditText) view.findViewById(R.id.first_name_edit);
        mLastNameEdit = (EditText) view.findViewById(R.id.last_name_edit);
    }

    private void createEvents() {
        //TODO: pop up "Are you sure?" dialog
        mDeleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //public Dialog onCreateDialog(Bundle savedInstanceState) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Delete your account?")
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Firebase userRef = firebaseDB.getUserInstance(appUser.getUsername());
                                    userRef.removeValue();
                                    //TODO - Logout
                                    appUser.setLoggedOnLast(false);
                                    userRef.unauth(); //un-authenticate a user from firebase

                                    //remove user from SQLite db
                                    dal.deleteUser(appUser);
                                    rootRef.removeUser(appUser.getEmail(), appUser.getPassword(), new Firebase.ResultHandler() {
                                        @Override
                                        public void onSuccess() {
                                            //return to login screen
                                            Intent intent = new Intent(getContext(), LoginActivity.class);
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onError(FirebaseError firebaseError) {
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });

                    AlertDialog build = builder.create();
                    build.show();

                }
            //}

        });


        mSaveChangesButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String first = mFirstNameEdit.getText().toString();
                String last = mLastNameEdit.getText().toString();

                GlobalResources.AppUser.setFirstName(first);
                GlobalResources.AppUser.setLastName(last);

                //TODO: RYAN - update user structure in Firebase
            }
        });
    }
}
