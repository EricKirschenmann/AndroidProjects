package com.majorassets.betterhalf;



import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.majorassets.betterhalf.DataItemController.DataItemActivity;
import com.majorassets.betterhalf.Database.DataItemRepository;
import com.majorassets.betterhalf.Database.DataProvider;
import com.majorassets.betterhalf.Model.BaseDataItem;
import com.majorassets.betterhalf.Model.MainCategory;
import com.majorassets.betterhalf.Model.MainCategoryType;
import com.majorassets.betterhalf.Model.Subcategory;
import com.majorassets.betterhalf.Model.SubcategoryType;

import java.io.Serializable;
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

	private Map<SubcategoryType, List<BaseDataItem>> userDataItems;
	private DataProvider db;

	public static final String TITLE_EXTRA = "com.majorassets.betterhalf.title";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);

		initializeComponents(view);


		////////// ENTERTAINMENT //////////
		mEntertainmentButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				//TODO: putExtra() data with Intent to determine title of DataItemActivity (i.e., press Food button, "Food" would be title
				String title = mEntertainmentButton.getText().toString();
				Intent intent = newIntent(new MainCategory(title));
				startActivity(intent);
			}
		});

		////////// FASHION //////////
		mFashionButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				//TODO: putExtra() for all category buttons
				String title = mFashionButton.getText().toString();
				Intent intent = newIntent(new MainCategory(title));
				startActivity(intent);
			}
		});

		////////// FOOD //////////
		mFoodButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String title = mFoodButton.getText().toString();
				Intent intent = newIntent(new MainCategory(title));
				startActivity(intent);
			}
		});

		////////// HOBBY //////////
		mHobbyButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String title = mHobbyButton.getText().toString();
				Intent intent = newIntent(new MainCategory(title));
				startActivity(intent);
			}
		});

		////////// MEDICAL //////////
		mMedicalButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String title = mMedicalButton.getText().toString();
				Intent intent = newIntent(new MainCategory(title));
				startActivity(intent);
			}
		});

		return view;
	}

	private void initializeComponents(View view)
	{
		mEntertainmentButton = (Button) view.findViewById(R.id.entertainment_button);
		mFashionButton = (Button) view.findViewById(R.id.fashion_button);
		mFoodButton = (Button) view.findViewById(R.id.food_button);
		mHobbyButton = (Button) view.findViewById(R.id.hobby_button);
		mMedicalButton = (Button) view.findViewById(R.id.medical_button);

		userDataItems = DataItemRepository.getDataItemRepository().getDataItems();
		db = DataProvider.getDataProvider();

		//right now have to call this 5 times - TODO: make dynamic
		String mainCategory = mEntertainmentButton.getText().toString().toLowerCase();
		getSubcategoryData(db.getSubcategories(mainCategory));
	}


	private void getSubcategoryData(Firebase ref)
	{
		ref.addListenerForSingleValueEvent(new ValueEventListener()
		{
			@Override
			public void onDataChange(DataSnapshot dataSnapshot)
			{
				if (dataSnapshot.hasChildren())
				{
					for (DataSnapshot child : dataSnapshot.getChildren())
					{
						Subcategory subcategory = new Subcategory();
						subcategory.setType(SubcategoryType.getTypeFromString(child.getKey()));
						subcategory.setMainType(MainCategoryType.getTypeFromString(dataSnapshot.getKey()));
						//set global data
						GlobalResources.Subcategories.add(subcategory);
					}
				}
			}

			@Override
			public void onCancelled(FirebaseError firebaseError)
			{

			}
		});
	}

	private Intent newIntent(MainCategory mainCategory)
	{
		Bundle args = new Bundle();
		Intent intent = new Intent(getContext(), DataItemActivity.class);
		args.putSerializable(TITLE_EXTRA, (Serializable)mainCategory);
		intent.putExtras(args);
		return intent;
	}
}