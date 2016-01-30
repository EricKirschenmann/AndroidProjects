package com.majorassets.betterhalf;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.majorassets.betterhalf.Database.TestSQLServerConnection;


/**
 * A placeholder fragment containing a simple view.
 */
public class HomeActivityFragment extends Fragment {

    private Button mEntertainmentButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mEntertainmentButton = (Button) view.findViewById(R.id.entertainment_button);
        mEntertainmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestSQLServerConnection.connectToDB();
                //Intent intent = new Intent(getContext(), DataListActivity.class);
                //startActivity(intent);
            }
        });

        return view;
    }
}
