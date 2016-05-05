package com.majorassets.betterhalf;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.majorassets.betterhalf.DataItemController.DataItemActivity;
import com.majorassets.betterhalf.Database.DataItemRepository;
import com.majorassets.betterhalf.Database.Firebase.FirebaseProvider;
import com.majorassets.betterhalf.Database.Firebase.FirebaseStructure;
import com.majorassets.betterhalf.Model.BaseLikeableItem;
import com.majorassets.betterhalf.Model.MainCategoryType;
import com.majorassets.betterhalf.Model.Subcategory;
import com.majorassets.betterhalf.Model.SubcategoryType;
import com.majorassets.betterhalf.Model.User;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeActivityFragment extends Fragment
{
	private TextView mEntertainmentText;
	private TextView mFashionText;
	private TextView mFoodText;
	private	TextView mHobbyText;
	private TextView mMedicalText;

	private CardView mEntertainmentCardView;
	private CardView mFashionCardView;
	private CardView mFoodCardView;
	private CardView mHobbyCardView;
	private CardView mMedicalCardView;

	private Map<SubcategoryType, List<BaseLikeableItem>> userDataItems;
	private FirebaseProvider db;
	private Firebase mRootRef;

	private User appUser;
	private User SO;

	public static final String TITLE_EXTRA = "com.majorassets.betterhalf.title";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);

		initializeComponents(view);
		createEvents();
		checkConnectionRequest();
		return view;

	}

	private void initializeComponents(View view)
	{
		mEntertainmentText = (TextView) view.findViewById(R.id.entertainment_txt);
		mFashionText = (TextView) view.findViewById(R.id.fashion_text);
		mFoodText = (TextView) view.findViewById(R.id.food_text);
		mHobbyText = (TextView) view.findViewById(R.id.hobby_text);
		mMedicalText = (TextView) view.findViewById(R.id.medical_text);

		mEntertainmentCardView = (CardView) view.findViewById(R.id.entertainment_card_view);
		mFashionCardView = (CardView) view.findViewById(R.id.fashion_card_view);
		mFoodCardView = (CardView) view.findViewById(R.id.food_card_view);
		mHobbyCardView = (CardView) view.findViewById(R.id.hobby_card_view);
		mMedicalCardView = (CardView) view.findViewById(R.id.medical_card_view);

		userDataItems = DataItemRepository.getDataItemRepository().getDataItems();
		db = FirebaseProvider.getDataProvider();

		//right now have to call this 5 times - TODO: make dynamic
		//String mainCategory = mEntertainmentButton.getText().toString().toLowerCase();

		for(String mainCategory : GlobalResources.MainCategories)
			getSubcategoryData(db.getSubcategoryInstance(mainCategory));
	}

	private void createEvents()
	{
		mEntertainmentCardView.setOnClickListener(new View.OnClickListener()
		{
		@Override
			public void onClick(View v)
			{
				String title = mEntertainmentText.getText().toString();
				launchDataItemActivity(title);
			}
		});

		mFashionCardView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String title = mFashionText.getText().toString();
				launchDataItemActivity(title);
			}
		});

		mFoodCardView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String title = mFoodText.getText().toString();
				launchDataItemActivity(title);
			}
		});

		mHobbyCardView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String title = mHobbyText.getText().toString();
				launchDataItemActivity(title);
			}
		});

		mMedicalCardView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String title = mMedicalText.getText().toString();
				launchDataItemActivity(title);
			}
		});
	}

	private void launchDataItemActivity(String title)
	{
		GlobalResources.mainTypePressed = MainCategoryType.getTypeFromString(title);

		Intent intent = newIntent(title);
		startActivity(intent);
	}

	private void getSubcategoryData(Firebase ref)
	{
		ref.addListenerForSingleValueEvent(new ValueEventListener()
		{
			@Override
			public void onDataChange(DataSnapshot dataSnapshot)
			{
				Subcategory subcategory;
				if (dataSnapshot.hasChildren())
				{
					//should only have one child
					for (DataSnapshot child : dataSnapshot.getChildren())
					{
						String mainCategory = dataSnapshot.getKey();
						//next level should be subcategories
						for (DataSnapshot subChild : child.getChildren())
						{
							subcategory = new Subcategory();
							subcategory.setType(SubcategoryType.getTypeFromString(subChild.getKey().toLowerCase()));
							subcategory.setMainType(MainCategoryType.getTypeFromString(mainCategory.toLowerCase()));
							//set global data
							GlobalResources.addToGlobalSubcategories(subcategory.getMainType(), subcategory);
						}
					}
				}
			}

			@Override
			public void onCancelled(FirebaseError firebaseError)
			{

			}
		});
	}

	private Intent newIntent(String title)
	{
		Intent intent = new Intent(getContext(), DataItemActivity.class);
		intent.putExtra(TITLE_EXTRA, title);
		return intent;
	}

	//Check if a request to connect has been made
	public void checkConnectionRequest() {
		//Instantiate necessary resources
		final FirebaseProvider db = FirebaseProvider.getDataProvider();
		final User appUser = GlobalResources.AppUser;
		final Firebase thisUserRef = db.getUserInstance(appUser.getUsername());

		thisUserRef.addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				//If dataSnapshot.child("connection") != NULL
				if(dataSnapshot.child("connection").getValue() != null) {
					String statusString = dataSnapshot.child("connection").child("status").getValue().toString();

					if (statusString.equals("pending")) {
						String userRequesting = dataSnapshot.child("connection").child("user").getValue().toString();
						final Firebase userRequestingRef = db.getUserInstance(userRequesting);

						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
						builder.setMessage("Connect with " + userRequesting + "?")
								.setPositiveButton("Connect", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
										thisUserRef.child("connection").child("status").setValue("connected");
										userRequestingRef.child("connection").child("status").setValue("connected");
										//set SO user and info

										userRequestingRef.addListenerForSingleValueEvent(new ValueEventListener()
										{
											User SO = new User();
											@Override
											public void onDataChange(DataSnapshot dataSnapshot)
											{
												SO.setUsername(dataSnapshot.getKey());
												appUser.setSignificantOther(SO);

												for (DataSnapshot childThis: dataSnapshot.getChildren()) {
													String child = childThis.getKey();
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
											}
											@Override
											public void onCancelled(FirebaseError firebaseError)
											{

											}
										});
									}
								})
								.setNegativeButton("Ignore", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
										thisUserRef.child("connection").removeValue();
										userRequestingRef.child("connection").removeValue();
									}
								});

						AlertDialog dialog = builder.create();
						dialog.show();
					} else {
						//Fill with SO data?
					}
				}
			}

			@Override
			public void onCancelled(FirebaseError firebaseError) {

			}
		});

	}
}