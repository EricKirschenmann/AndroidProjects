package com.majorassets.betterhalf;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.majorassets.betterhalf.Database.Firebase.FirebaseProvider;
import com.majorassets.betterhalf.Model.BaseDataItem;
import com.majorassets.betterhalf.Model.User;

/**
 * A placeholder fragment containing a simple view.
 */
public class ConnectionActivityFragment extends Fragment
{
    private EditText connectAccountsEdit;
    private TextView connectionResponseTxt;
    private Button connectButton;

    private FirebaseProvider db;
    private Firebase userRef;

    private User appUser;

    public ConnectionActivityFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_connection, container, false);

        initializeComponents(view);
        createEvents();

        return view;
    }

    private void initializeComponents(View view)
    {
        appUser = GlobalResources.AppUser;
        db = FirebaseProvider.getDataProvider();
        userRef = db.getFirebaseInstance();

        connectAccountsEdit = (EditText) view.findViewById(R.id.connect_input_edit);
        connectionResponseTxt = (TextView) view.findViewById(R.id.connect_isvalid_txt);
        connectButton = (Button) view.findViewById(R.id.connect_btn);

    }

    private void createEvents()
    {
        connectButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String username = connectAccountsEdit.getText().toString();
                userRef = db.getUserInstance(username);

                new ConnectAccountsTask().execute();


            }
        });
    }

    private class ConnectAccountsTask extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... params)
        {
            //TODO: check if username exists in Firebase
            userRef.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    if(dataSnapshot.getValue() != null)
                    {
                        String user;
                        String parent;
                        String child;
                        DataSnapshot next;
                        BaseDataItem item;

                        User SO = new User(); //TODO RYAN - read out User object from Firebase
                        SO.setUsername(dataSnapshot.getKey());
                        user = dataSnapshot.getKey();

                       while (dataSnapshot.hasChildren()) {
                           parent = dataSnapshot.getKey();
                           next = dataSnapshot.getChildren().iterator().next();
                           child = next.getKey();


                       }
                        appUser.setSignificantOther(SO);
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError)
                {

                }
            });
            return null;
        }
    }
}
