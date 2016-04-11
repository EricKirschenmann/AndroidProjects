package com.majorassets.betterhalf;

import android.database.SQLException;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.majorassets.betterhalf.Database.Firebase.FirebaseProvider;
import com.majorassets.betterhalf.Database.SQLite.SQLiteProvider;
import com.majorassets.betterhalf.Database.SQLite.SQLiteUserDAL;

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
        mDeleteAccountButton = (Button) view.findViewById(R.id.delete_account_btn);
        mSaveChangesButton = (Button) view.findViewById(R.id.save_account_btn);
        mFirstNameEdit = (EditText) view.findViewById(R.id.first_name_edit);
        mLastNameEdit = (EditText) view.findViewById(R.id.last_name_edit);
    }

    private void createEvents()
    {
        //TODO: pop up "Are you sure?" dialog
        mDeleteAccountButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //remove user from SQLite db
                dal.deleteUser(GlobalResources.AppUser);

                //TODO: RYAN - remove structure and delete user in Firebase
            }
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
