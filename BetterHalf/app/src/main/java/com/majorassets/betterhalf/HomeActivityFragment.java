package com.majorassets.betterhalf;



import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.majorassets.betterhalf.Database.DataItemRepository;
import com.majorassets.betterhalf.Model.BaseDataItem;
import com.majorassets.betterhalf.Model.Subcategory;

import java.util.List;
import java.util.Map;

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

	private Map<Subcategory, List<BaseDataItem>> userDataItems;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);

		InitializeComponents(view);

		////////// ENTERTAINMENT //////////
		mEntertainmentButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				//TODO: putExtra() data with Intent to determine title of DataListActivity (i.e., press Food button, "Food" would be title
				GoToDataListActivity();
			}
		});

		////////// FASHION //////////
		mFashionButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				//TODO: putExtra() for all category buttons
				GoToDataListActivity();
			}
		});

		////////// FOOD //////////
		mFoodButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				GoToDataListActivity();
			}
		});

		////////// HOBBY //////////
		mHobbyButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				GoToDataListActivity();
			}
		});

		////////// MEDICAL //////////
		mMedicalButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				GoToDataListActivity();
			}
		});

		return view;
	}

	private void InitializeComponents(View view)
	{
		mEntertainmentButton = (Button) view.findViewById(R.id.entertainment_button);
		mFashionButton = (Button) view.findViewById(R.id.fashion_button);
		mFoodButton = (Button) view.findViewById(R.id.food_button);
		mHobbyButton = (Button) view.findViewById(R.id.hobby_button);
		mMedicalButton = (Button) view.findViewById(R.id.medical_button);

		userDataItems = DataItemRepository.getDataItemRepository().getDataItems();
	}

	private void GoToDataListActivity()
	{
		Intent intent = new Intent(getContext(), DataListActivity.class);
		startActivity(intent);
	}
}