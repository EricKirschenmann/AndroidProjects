package com.majorassets.betterhalf;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.majorassets.betterhalf.Database.EndpointsAsyncTask;

public class HomeActivity extends SingleFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		createFragment();
	}

	protected Fragment createFragment() {
		return new HomeActivityFragment();
	}

}
