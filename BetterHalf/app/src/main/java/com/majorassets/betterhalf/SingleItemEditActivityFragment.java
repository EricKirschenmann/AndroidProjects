package com.majorassets.betterhalf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.majorassets.betterhalf.DataItemController.DataItemActivity;
import com.majorassets.betterhalf.DataItemController.DataItemActivityFragment;
import com.majorassets.betterhalf.Database.Firebase.FirebaseProvider;
import com.majorassets.betterhalf.Database.SQLite.DataDBSchema;
import com.majorassets.betterhalf.Database.SQLite.SQLiteProvider;
import com.majorassets.betterhalf.Database.SQLite.SQLiteUserDAL;
import com.majorassets.betterhalf.Model.User;

/**
 * A placeholder fragment containing a simple view.
 */
public class SingleItemEditActivityFragment extends Fragment
{

    private EditText mItemName;
    private EditText mItemValue;
    private Button mAddButton;
    private DataItemActivityFragment mDataItemActivityFragment;

    private SQLiteProvider sqliteDB;
    private FirebaseProvider firebaseDB;

    private SQLiteUserDAL dal;
    private Firebase userDataRef;

    private User appUser;

    public SingleItemEditActivityFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view =  inflater.inflate(R.layout.fragment_single_item_edit, container, false);
        Firebase.setAndroidContext(getContext());

        initializeComponents(view);
        createEvents();

        return view;
    }

    private void initializeComponents(View view)
    {
        appUser = GlobalResources.AppUser;
        userDataRef = firebaseDB.getUserDataInstance(appUser.getUsername());

        mItemName = (EditText) view.findViewById(R.id.item_name_edit);
        mItemValue = (EditText) view.findViewById(R.id.item_value_edit);
        mAddButton = (Button) view.findViewById(R.id.add_button);
    }

    private void createEvents()
    {
        mItemName.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mItemName.setSelection(0); //place cursor at the beginning
            }
        });

        mItemValue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mItemValue.setSelection(0);
            }
        });

        //ADD BUTTON (CHANGED SOME VARIABLES TO PUBLIC FOR TESTING)
        mAddButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent();
                intent.setClass(getContext(), DataItemActivity.class);
                startActivity(intent);
            }
        });
    }
}
