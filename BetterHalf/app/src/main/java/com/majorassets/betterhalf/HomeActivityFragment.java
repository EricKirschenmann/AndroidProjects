package com.majorassets.betterhalf;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.majorassets.betterhalf.Database.*;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeActivityFragment extends Fragment {

	private Button mEntertainmentButton;

	public HomeActivityFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view =  inflater.inflate(R.layout.fragment_home, container, false);

		mEntertainmentButton = (Button)(getActivity().findViewById(R.id.entertainment_button));
		mEntertainmentButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Intent intent = new Intent(getActivity(), DataListActivityFragment.class);
				//startActivity(intent);
                new EndpointsAsyncTask().execute(new Pair<Context, String>(getActivity(), "Manfred"));
            }
		});

		return view;
	}
}
