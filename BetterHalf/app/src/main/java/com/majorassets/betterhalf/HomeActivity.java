package com.majorassets.betterhalf;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.firebase.client.Firebase;
import com.majorassets.betterhalf.Database.Firebase.FirebaseProvider;
import com.majorassets.betterhalf.Database.SQLite.SQLiteProvider;
import com.majorassets.betterhalf.Database.SQLite.SQLiteUserDAL;
import com.majorassets.betterhalf.Model.User;

public class HomeActivity extends AppCompatActivity
{
	private SQLiteUserDAL dal;
	private SQLiteProvider sqliteDB;
	private FirebaseProvider firebaseDB;
	private Firebase ref;

	private User appUser;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		//data providers
		sqliteDB = SQLiteProvider.getSQLiteProvider(this.getApplicationContext());
		firebaseDB = FirebaseProvider.getDataProvider();

		dal = new SQLiteUserDAL(sqliteDB.getDatabase());

		ref = firebaseDB.getFirebaseInstance();
		appUser = GlobalResources.AppUser;
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
				ref = firebaseDB.getFirebaseInstance();
				appUser = GlobalResources.AppUser;
				//this user is is officially logged out - was NOT logged on last
				appUser.setLoggedOnLast(false);
				//update the user in SQLite
				dal.updateUser(appUser);

				//TODO: popup an "Are you sure?" dialog (fragment) and logout through Firebase API
				ref.unauth(); //un-authenticate a user from firebase
				//return to login screen
				intent = new Intent(HomeActivity.this, LoginActivity.class);
				startActivity(intent);
			case R.id.action_share:
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

}
