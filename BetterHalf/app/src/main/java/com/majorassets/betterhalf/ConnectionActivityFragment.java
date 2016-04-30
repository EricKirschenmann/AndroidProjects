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
import com.majorassets.betterhalf.Database.Firebase.FirebaseStructure;
import com.majorassets.betterhalf.Model.BaseDataItem;
import com.majorassets.betterhalf.Model.User;

import java.util.UUID;

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

        connectAccountsEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectAccountsEdit.setText("testuser8verizon");
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
                        BaseDataItem item;

                        User SO = new User(); //TODO RYAN - read out User object from Firebase
                        SO.setUsername(dataSnapshot.getKey());
                        appUser.setSignificantOther(SO);

                        user = dataSnapshot.getKey();
                        parent = dataSnapshot.getKey();

                           for (DataSnapshot childThis: dataSnapshot.getChildren()) {
                               child = childThis.getKey();
                               if (child == "info") {
                                   String infoChildKey;
                                   Object infoChildValue;
                                   Iterable<DataSnapshot> infoChildren = childThis.getChildren();
                                   for (DataSnapshot infoChild : infoChildren) {
                                       infoChildKey = infoChild.getKey();
                                       infoChildValue = infoChild.getValue();

                                       switch (infoChildKey) {
                                           case FirebaseStructure.EMAIL:
                                               SO.setEmail(infoChildValue.toString());
                                               break;
                                           case FirebaseStructure.FIRSTNAME:
                                               SO.setFirstName(infoChildValue.toString());
                                               break;
                                           case FirebaseStructure.LASTNAME:
                                               SO.setLastName(infoChildValue.toString());
                                               break;
                                           case FirebaseStructure.ID:
                                               SO.setID(UUID.fromString(infoChildValue.toString()));
                                               break;
                                           default:
                                               break;
                                       }
                                   }
                               }
                               else if(child == "data"){

                               }
                           }

                        appUser.setSignificantOther(SO);
                        String cEmail = SO.getEmail();
                        String cUsername = LoginHelperActivity.generateUsername(cEmail);
                        userRef.child("connection").child("status").setValue("pending");
                        userRef.child("connection").child("user").setValue(appUser.getUsername());
                        userRef = db.getUserInstance(appUser.getUsername());
                        userRef.child("connection").child("user").setValue(cUsername);
                        userRef.child("connection").child("status").setValue("requesting");
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
