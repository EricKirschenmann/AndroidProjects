package com.majorassets.betterhalf;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.majorassets.betterhalf.Database.Firebase.FirebaseProvider;
import com.majorassets.betterhalf.Database.Firebase.FirebaseStructure;
import com.majorassets.betterhalf.Database.SQLite.SQLiteProvider;
import com.majorassets.betterhalf.Database.SQLite.SQLiteUserDAL;
import com.majorassets.betterhalf.Model.User;

/**
 * A placeholder fragment containing a simple view.
 */
public class SettingsActivityFragment extends Fragment
{
    private Button mDeleteAccountButton;
    private Button mSaveChangesButton;
    private EditText mFirstNameEdit;
    private EditText mLastNameEdit;

    private SQLiteProvider db;
    private SQLiteUserDAL dal;

    private FirebaseProvider firebaseDB;
    private Firebase rootRef;

    private User appUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);

        initializeComponents(view);
        createEvents();

        db = SQLiteProvider.getSQLiteProvider(getContext());
        dal = new SQLiteUserDAL(db.getDatabase());

        firebaseDB = FirebaseProvider.getDataProvider();
        rootRef = firebaseDB.getFirebaseInstance();

        return view;
    }


    private void initializeComponents(View view)
    {
        appUser = GlobalResources.AppUser;
        mDeleteAccountButton = (Button) view.findViewById(R.id.delete_account_btn);
        mSaveChangesButton = (Button) view.findViewById(R.id.save_account_btn);
        mFirstNameEdit = (EditText) view.findViewById(R.id.first_name_edit);
        mLastNameEdit = (EditText) view.findViewById(R.id.last_name_edit);
    }

    private void createEvents()
    {

        //TODO: FIXED - pop up "Are you sure?" dialog
        mDeleteAccountButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Delete your account?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Delete User account here
                                //remove user from SQLite db
                                dal.deleteUser(GlobalResources.AppUser);

                                //TODO: FIXED - RYAN - remove structure and delete user in Firebase
                                Firebase usersRef = rootRef.child(FirebaseStructure.USERS);
                                //Global resources.appuser
                                //String dUsername = GlobalResources.AppUser.getUsername();
                                //String dPassword = GlobalResources.AppUser.getPassword();
                                Firebase dThisUser = usersRef.child(appUser.getUsername());
                                dThisUser.removeValue();
                                dThisUser.removeUser(appUser.getEmail(), appUser.getPassword(), new Firebase.ResultHandler() {
                                    @Override
                                    public void onSuccess() {

                                        Firebase ref = firebaseDB.getFirebaseInstance();
                                        User appUser = GlobalResources.AppUser;
                                        //this user is is officially logged out - was NOT logged on last
                                        appUser.setLoggedOnLast(false);
                                        //update the user in SQLite
                                        dal.updateUser(appUser);

                                        ref.unauth(); //un-authenticate a user from firebase
                                        //return to login screen
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onError(FirebaseError firebaseError) {
                                        //TODO: RYAN - add error popup message
                                        Log.e("User not deleted", SettingsActivityFragment.class.getSimpleName());

                                    }
                                });

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Don't delete account
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        mSaveChangesButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Firebase usersRef = rootRef.child(FirebaseStructure.USERS);

                String first = mFirstNameEdit.getText().toString();
                String last = mLastNameEdit.getText().toString();

                GlobalResources.AppUser.setFirstName(first);
                GlobalResources.AppUser.setLastName(last);

                //TODO: FIXED - RYAN - update user structure in Firebase
                String username = GlobalResources.AppUser.getUsername();
                Firebase infoRef = usersRef.child(username).child("info");
                infoRef.child("firstName").setValue(first);
                infoRef.child("lastName").setValue(last);

            }
        });
    }
}
