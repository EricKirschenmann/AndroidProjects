package com.majorassets.betterhalf;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.majorassets.betterhalf.Database.DataItemRepository;
import com.majorassets.betterhalf.Database.Firebase.FirebaseProvider;
import com.majorassets.betterhalf.Database.SQLite.SQLiteProvider;
import com.majorassets.betterhalf.Database.SQLite.SQLiteUserDAL;
import com.majorassets.betterhalf.Model.BaseLikeableItem;
import com.majorassets.betterhalf.Model.Entertainment.BookItem;
import com.majorassets.betterhalf.Model.Entertainment.GameItem;
import com.majorassets.betterhalf.Model.Entertainment.MovieItem;
import com.majorassets.betterhalf.Model.Entertainment.MusicItem;
import com.majorassets.betterhalf.Model.Subcategory;
import com.majorassets.betterhalf.Model.SubcategoryType;
import com.majorassets.betterhalf.Model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity
{
	private SQLiteUserDAL dal;
	private SQLiteProvider sqliteDB;
	private FirebaseProvider firebaseDB;
	private Firebase ref;
	private Firebase subcategoryInstance;

	private User appUser;
	private Map<SubcategoryType, List<BaseLikeableItem>> appUserData;
	private DataItemRepository userRepo;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		Firebase.setAndroidContext(this);

		//data providers
		sqliteDB = SQLiteProvider.getSQLiteProvider(this.getApplicationContext());
		firebaseDB = FirebaseProvider.getDataProvider();

		dal = new SQLiteUserDAL(sqliteDB.getDatabase());

		ref = firebaseDB.getFirebaseInstance();

		appUser = GlobalResources.AppUser;
		userRepo = appUser.getDataItemRepository();
		appUserData = userRepo.getDataItems();

		//syncSQLiteToFirebase();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		//syncSQLiteToFirebase();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		Intent intent;
		switch(item.getItemId())
		{
			case R.id.action_settings:
				intent = new Intent(HomeActivity.this, SettingsActivity.class);
				startActivity(intent);
				return true;
			case R.id.action_logout:
				//this user is is officially logged out - was NOT logged on last
				appUser.setLoggedOnLast(false);
				//update the user in SQLite
				dal.updateUser(appUser);

				//TODO: popup an "Are you sure?" dialog (fragment) and logout through Firebase API
				ref.unauth(); //un-authenticate a user from firebase
				//return to login screen
				intent = new Intent(HomeActivity.this, LoginActivity.class);
				startActivity(intent);
				return true;
			case R.id.action_connect:
				intent = new Intent(HomeActivity.this, ConnectionActivity.class);
				startActivity(intent);
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void syncSQLiteToFirebase()
	{
		Map<String, Map<String, String>> firebaseMap = new HashMap<>();
		Map<String, String> internalMap = new HashMap<>();

		for (Map.Entry entry: appUserData.entrySet())
		{
			String sub = SubcategoryType.getFirebaseStringFromType((SubcategoryType)entry.getKey());
			subcategoryInstance = firebaseDB.getUserDataSubcategoryInstance(appUser.getUsername(),
					sub);

			List<BaseLikeableItem> items = (List<BaseLikeableItem>) entry.getValue();
			for (BaseLikeableItem item : items)
			{
				internalMap.put(item.getLabel(), item.getValue());
				firebaseMap.put(item.getID(), internalMap);
			}
			subcategoryInstance.setValue(firebaseMap);

			internalMap.clear();
			firebaseMap.clear();
		}
	}
}
