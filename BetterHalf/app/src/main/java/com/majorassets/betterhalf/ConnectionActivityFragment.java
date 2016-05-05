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
import com.majorassets.betterhalf.Database.SQLite.SQLiteProvider;
import com.majorassets.betterhalf.Database.SQLite.SQLiteUserDAL;
import com.majorassets.betterhalf.Model.BaseDataItem;
import com.majorassets.betterhalf.Model.User;

import java.util.UUID;

/**
 * A placeholder fragment containing a simple view.
 */
public class ConnectionActivityFragment extends Fragment {
    private EditText connectAccountsEdit;
    private TextView connectionResponseTxt;
    private Button connectButton;
    private Button disconnectButton;
    private TextView disconnectText;

    private FirebaseProvider db;
    private Firebase userRef;
    private SQLiteProvider sqliteDB;
    private SQLiteUserDAL userDAL;

    private User appUser;
    private User SO;

    public ConnectionActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_connection, container, false);

        initializeComponents(view);
        //If not connected, Otter Half stops
        setDisconnectText(view);
        createEvents();

        return view;
    }

    private void initializeComponents(View view) {
        appUser = GlobalResources.AppUser;
        SO = appUser.getSignificantOther();
        db = FirebaseProvider.getDataProvider();

        connectAccountsEdit = (EditText) view.findViewById(R.id.connect_input_edit);
        connectionResponseTxt = (TextView) view.findViewById(R.id.connect_isvalid_txt);
        connectButton = (Button) view.findViewById(R.id.connect_btn);
        disconnectButton = (Button) view.findViewById(R.id.disconnect_btn);
        disconnectText = (TextView) view.findViewById(R.id.disconnect_txt);

        sqliteDB = SQLiteProvider.getSQLiteProvider(getContext());
        userDAL = new SQLiteUserDAL(sqliteDB.getDatabase());
    }

    private void createEvents() {
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = connectAccountsEdit.getText().toString();
                userRef = db.getUserInstance(username);

                new ConnectAccountsTask().execute();
            }
        });

        connectAccountsEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectAccountsEdit.setText("ranema88gmail");
            }
        });

        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = connectAccountsEdit.getText().toString();
                userRef = db.getUserInstance(username);
                //disconnectFromUser(v);
                new DisconnectAccountsTask().execute();

                userDAL.removeConnections(appUser);
                userDAL.removeConnections(appUser.getSignificantOther());
            }
        });
    }

    private class DisconnectAccountsTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            //User SO = appUser.getSignificantOther();
            final String soUsername = SO.getUsername();
            userRef = db.getUserInstance(appUser.getUsername());
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    userRef.child("connection").removeValue();
                    Firebase soUserRef = db.getUserInstance(soUsername);
                    soUserRef.child("connection").removeValue();
                    //userRef.child("connection").removeValue();
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            return null;
        }
    }

    private class ConnectAccountsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params)
        {
            //TODO: check if username exists in Firebase
            userRef.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    //Null Pointer Exception AND if user tried to connect to self
                    //Error message if trying to connect to self?
                    if(dataSnapshot.getValue() != null && !dataSnapshot.getKey().equals(appUser.getUsername()) )
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

                        //appUser.setSignificantOther(SO);
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

    private void setDisconnectText(View view){
        userRef = db.getUserInstance(appUser.getUsername());
        final View view2 = view;
        //if user is connected to significant other, get soUsername and set disconnectText

        //PROBLEM HERE
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //if user is connected to significant other, get soUsername and set disconnectText
                Boolean connectionReference = snapshot.child("connection").exists();
                if(!connectionReference){
                    disconnectButton.setVisibility(View.INVISIBLE);
                    disconnectText.setVisibility(View.INVISIBLE);
                }

                if(connectionReference ) {
                    String soUsername = snapshot.child("connection").child("user").getValue().toString();
                    disconnectText.setText("Do you want to disconnect with " + soUsername + "?");
                    disconnectButton = (Button) view2.findViewById(R.id.disconnect_btn);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });

    }
}
