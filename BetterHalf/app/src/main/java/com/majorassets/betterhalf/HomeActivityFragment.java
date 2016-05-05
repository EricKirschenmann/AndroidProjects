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
import com.majorassets.betterhalf.Database.Firebase.FirebaseProvider;
import com.majorassets.betterhalf.Database.SQLite.SQLiteItemsDAL;
import com.majorassets.betterhalf.Database.SQLite.SQLiteProvider;
import com.majorassets.betterhalf.Database.SQLite.SQLiteUserDAL;
import com.majorassets.betterhalf.Model.BaseLikeableItem;
import com.majorassets.betterhalf.Model.Entertainment.BookItem;
import com.majorassets.betterhalf.Model.Entertainment.GameItem;
import com.majorassets.betterhalf.Model.Entertainment.MovieItem;
import com.majorassets.betterhalf.Model.Entertainment.MusicItem;
import com.majorassets.betterhalf.Model.Entertainment.TVShowItem;
import com.majorassets.betterhalf.Model.Entertainment.TheaterItem;
import com.majorassets.betterhalf.Model.Fashion.AccessoriesItem;
import com.majorassets.betterhalf.Model.Fashion.ClothingItem;
import com.majorassets.betterhalf.Model.Fashion.JewelryItem;
import com.majorassets.betterhalf.Model.Fashion.ShoesItem;
import com.majorassets.betterhalf.Model.Food.DrinksItem;
import com.majorassets.betterhalf.Model.Food.EntreesItem;
import com.majorassets.betterhalf.Model.Food.RestaurantsItem;
import com.majorassets.betterhalf.Model.Food.SidesItem;
import com.majorassets.betterhalf.Model.Food.SnacksItem;
import com.majorassets.betterhalf.Model.Hobbies.IndoorItem;
import com.majorassets.betterhalf.Model.Hobbies.OutdoorItem;
import com.majorassets.betterhalf.Model.Hobbies.SportsItem;
import com.majorassets.betterhalf.Model.MainCategoryType;
import com.majorassets.betterhalf.Model.Medical.AllergiesItem;
import com.majorassets.betterhalf.Model.Medical.IllnessesItem;
import com.majorassets.betterhalf.Model.Medical.MedicalItem;
import com.majorassets.betterhalf.Model.Medical.PhobiasItem;
import com.majorassets.betterhalf.Model.Subcategory;
import com.majorassets.betterhalf.Model.SubcategoryType;
import com.majorassets.betterhalf.Model.User;

import java.util.ArrayList;
import java.util.HashMap;
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

	private FirebaseProvider db;

	private SQLiteProvider sqliteDB;
	private SQLiteUserDAL userDAL;
	private SQLiteItemsDAL itemsDAL;

	private FirebaseProvider firebaseDB;
	private Firebase currentUserRef; //instance for the app user
	private Firebase soUserRef; //instance for the significant other

	private User appUser;

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

		db = FirebaseProvider.getDataProvider();

		firebaseDB = FirebaseProvider.getDataProvider();
		sqliteDB = SQLiteProvider.getSQLiteProvider(getContext());
		userDAL = new SQLiteUserDAL(sqliteDB.getDatabase());
		itemsDAL = new SQLiteItemsDAL(sqliteDB.getDatabase());

		GlobalResources.Subcategories.clear();
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
	public void checkConnectionRequest()
	{
		//Instantiate necessary resources
		appUser = GlobalResources.AppUser;
		final Firebase currentUserRef = firebaseDB.getUserInstance(appUser.getUsername());

		currentUserRef.addListenerForSingleValueEvent(new ValueEventListener()
		{

			@Override
			public void onDataChange(DataSnapshot dataSnapshot)
			{
				if(dataSnapshot.child("connection").getValue() != null)
				{
					final String userRequesting = dataSnapshot.child("connection").child("user").getValue().toString();
					final Firebase soUserRef = firebaseDB.getUserInstance(userRequesting);

					String statusString = dataSnapshot.child("connection").child("status").getValue().toString();
					if (statusString.equals("pending"))
					{
						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
						builder.setMessage("Connect with " + userRequesting + "?")
								.setPositiveButton("Connect", new DialogInterface.OnClickListener()
								{
									public void onClick(DialogInterface dialog, int id)
									{
										currentUserRef.child("connection").child("status").setValue("connected");
										soUserRef.child("connection").child("status").setValue("connected");

										assignUserToSO(soUserRef, userRequesting);
										readSODataFromFirebase(userRequesting);
									}
								})
								.setNegativeButton("Ignore", new DialogInterface.OnClickListener()
								{
									public void onClick(DialogInterface dialog, int id)
									{
										currentUserRef.removeValue();
										soUserRef.removeValue();
									}
								});

						AlertDialog dialog = builder.create();
						dialog.show();
					}
					else if (statusString.equals("connected"))
					{
						if(!appUser.isConnected())
							assignUserToSO(soUserRef, userRequesting);
					}
				}
			}

			@Override
			public void onCancelled(FirebaseError firebaseError) {

			}
		});

	}

	private void assignUserToSO(Firebase soRef, final String soUsername)
	{
		soRef.addListenerForSingleValueEvent(new ValueEventListener()
		{
			@Override
			public void onDataChange(DataSnapshot dataSnapshot)
			{
				UUID soID = UUID.fromString((String)dataSnapshot.child("info").child("id").getValue());
				String email = (String)dataSnapshot.child("info").child("email").getValue();

				User SO = new User(soID);
				SO.setUsername(soUsername);
				SO.setEmail(email);

				appUser.setSignificantOther(SO);

				userDAL.updateUser(appUser);
			}

			@Override
			public void onCancelled(FirebaseError firebaseError)
			{

			}
		});
	}

	private void readSODataFromFirebase(final String username)
	{
		Firebase soDataRef = firebaseDB.getUserDataInstance(username);
		soDataRef.addListenerForSingleValueEvent(new ValueEventListener()
		{
			@Override
			public void onDataChange(DataSnapshot dataSnapshot)
			{
				if(appUser.getSignificantOther() != null)
				{
					SubcategoryType type;
					for (DataSnapshot subCategory : dataSnapshot.getChildren())
					{
						type = SubcategoryType.getTypeFromString(subCategory.getKey());
						Map<SubcategoryType, List<BaseLikeableItem>> map = appUser.getSignificantOther().getDataItems();
						List<BaseLikeableItem> innerList = new ArrayList<>();

						for (DataSnapshot id : subCategory.getChildren())
						{
							BaseLikeableItem item = null;
							switch (type)
							{
								//ENTERTAINMENT
								case BOOK:
									item = new BookItem(id.getKey());
									break;
								case MOVIE:
									item = new MovieItem(id.getKey());
									break;
								case MUSIC:
									item = new MusicItem(id.getKey());
									break;
								case THEATER:
									item = new GameItem(id.getKey());
									break;
								case TV_SHOW:
									item = new TVShowItem(id.getKey());
									break;
								case GAME:
									item = new TheaterItem(id.getKey());
									break;
								//FASHION
								case CLOTHING:
									item = new ClothingItem(id.getKey());
									break;
								case JEWELRY:
									item = new JewelryItem(id.getKey());
									break;
								case SHOE:
									item = new ShoesItem(id.getKey());
									break;
								case ACCESSORY:
									item = new AccessoriesItem(id.getKey());
									break;
								//FOOD
								case RESTAURANT:
									item = new RestaurantsItem(id.getKey());
									break;
								case ENTREE:
									item = new EntreesItem(id.getKey());
									break;
								case SIDE:
									item = new SidesItem(id.getKey());
									break;
								case SNACK:
									item = new SnacksItem(id.getKey());
									break;
								case DRINK:
									item = new DrinksItem(id.getKey());
									break;
								//HOBBY
								case INDOOR:
									item = new IndoorItem(id.getKey());
									break;
								case OUTDOOR:
									item = new OutdoorItem(id.getKey());
									break;
								case SPORT:
									item = new SportsItem(id.getKey());
									break;
								//MEDICAL
								case ILLNESS:
									item = new IllnessesItem(id.getKey());
									break;
								case PHOBIA:
									item = new PhobiasItem(id.getKey());
									break;
								case MEDICATION:
									item = new MedicalItem(id.getKey());
									break;
								case ALLERGY:
									item = new AllergiesItem(id.getKey());
									break;
							}

							for (DataSnapshot attribute : id.getChildren())
							{
								if(attribute.getKey().equals("favorite"))
									item.setIsFavorite((boolean)attribute.getValue());
								else
								{
									item.setLabel(attribute.getKey());
									item.setValue(attribute.getValue().toString());
									item.setUserID(appUser.getSignificantOther().getID());
								}
							}

							//write data to SQLite
							itemsDAL.addItem(item, SubcategoryType.getDisplayableStringsFromType(type, true));

							innerList.add(item);
						}

						map.put(type, innerList);
						appUser.getSignificantOther().setDataItems(map);
					}
				}

			}

			@Override
			public void onCancelled(FirebaseError firebaseError)
			{

			}
		});
	}
}