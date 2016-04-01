package com.majorassets.betterhalf.DataItemController;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.majorassets.betterhalf.R;
import com.majorassets.betterhalf.SingleItemEditActivity;

public class DataItemActivity extends AppCompatActivity
{
	private FloatingActionButton mAddItemFab;
	private String mTitle;

	private DataItemPagerAdapter mDataItemPagerAdapter;
	private ViewPager mViewPager;
	private TabLayout mTabLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		//get reference to floating action button in bottom right of screen
		mAddItemFab = (FloatingActionButton) findViewById(R.id.fab_add_item);
		mAddItemFab.setOnClickListener(new View.OnClickListener()
		{
			//have the click start the edit screen for a single item
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(DataItemActivity.this, SingleItemEditActivity.class);
				startActivity(intent);
			}
		});

		//mTitle = getIntent().getStringExtra(HomeActivityFragment.TITLE_EXTRA);
		//setTitle(mTitle);

		setUpTabPageComponents();
	}

	@Override
	public void onStop()
	{
		super.onStop();
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_data_list, menu);
		return true;
	}

	private void setUpTabPageComponents()
	{
		mDataItemPagerAdapter = new DataItemPagerAdapter(getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);

		mViewPager.setAdapter(mDataItemPagerAdapter);

		mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
		mTabLayout.setupWithViewPager(mViewPager);
	}
}
