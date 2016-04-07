package com.majorassets.betterhalf;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.majorassets.betterhalf.Database.Firebase.FirebaseProvider;

public class HomeActivity extends AppCompatActivity
{
	private FirebaseProvider db = FirebaseProvider.getDataProvider();
	private Firebase ref;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
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
				//TODO: popup an "Are you sure?" dialog (fragment) and logout through Firebase API
				ref.unauth(); //un-authenticate a user
			case R.id.action_share:
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
