package com.majorassets.betterhalf;



import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeActivityFragment extends Fragment
{

	private Button mEntertainmentButton;
	private Button mFashionButton;
	private Button mFoodButton;
	private Button mHobbyButton;
	private Button mMedicalButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);

		////////// ENTERTAINMENT //////////
		mEntertainmentButton = (Button) view.findViewById(R.id.entertainment_button);
		mEntertainmentButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				//TODO: putExtra() data with Intent to determine title of DataListActivity (i.e., press Food button, "Food" would be title
				Intent intent = new Intent(getContext(), DataListActivity.class);
				startActivity(intent);
			}
		});

		////////// FASHION //////////
		mFashionButton = (Button) view.findViewById(R.id.fashion_button);
		mFashionButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				//TODO: putExtra() for all category buttons
				Intent intent = new Intent(getContext(), DataListActivity.class);
				startActivity(intent);
			}
		});

		////////// FOOD //////////
		mFoodButton = (Button) view.findViewById(R.id.food_button);
		mFoodButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getContext(), DataListActivity.class);
				startActivity(intent);
			}
		});

		////////// HOBBY //////////
		mHobbyButton = (Button) view.findViewById(R.id.hobby_button);
		mHobbyButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getContext(), DataListActivity.class);
				startActivity(intent);
			}
		});

		////////// MEDICAL //////////
		mMedicalButton = (Button) view.findViewById(R.id.medical_button);
		mMedicalButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getContext(), DataListActivity.class);
				startActivity(intent);
			}
		});

		return view;
	}
}