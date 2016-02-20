package com.majorassets.betterhalf;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by dgbla on 12/14/2015.
 */
public abstract class SingleFragmentActivity extends FragmentActivity
{
	protected abstract Fragment createFragment();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);

		FragmentManager fm = getSupportFragmentManager();

		//retrieve the fragment if it exists in the FragmentManager
		Fragment fragment = fm.findFragmentById(R.id.fragment_container);

		//if fragment is brand new (null), instantiate a new instance and add it the FragmentManager
		//beginTransaction creates and returns a new FragmentTransaction
		if(fragment == null){
			fragment = createFragment();
			fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
		}
	}
}
