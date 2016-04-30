package com.majorassets.betterhalf.DataItemController;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.firebase.client.Firebase;
import com.majorassets.betterhalf.HomeActivityFragment;
import com.majorassets.betterhalf.R;
import com.majorassets.betterhalf.SingleItemEditActivity;

public class DataItemActivity extends AppCompatActivity
{
	private FloatingActionButton mAddItemFab;
	private String mTitle;

	private DataItemPagerAdapter mDataItemPagerAdapter;
	private ViewPager mViewPager;
	private TabLayout mTabLayout;

	public static final String SUBCAT_EXTRA = "com.majorassets.betterhalf.subcat";

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
				int currentTab = mTabLayout.getSelectedTabPosition();
				TabLayout.Tab tab = mTabLayout.getTabAt(currentTab);
				mTitle = tab.getText().toString();

				Intent intent = new Intent(DataItemActivity.this, SingleItemEditActivity.class);
				intent.putExtra(SUBCAT_EXTRA, mTitle);
				startActivity(intent);
			}
		});

		mTitle = getIntent().getStringExtra(HomeActivityFragment.TITLE_EXTRA);
		setTitle(mTitle);

		setUpTabPageComponents();
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

		try
		{
			mViewPager.setAdapter(mDataItemPagerAdapter);

			mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
			mTabLayout.setupWithViewPager(mViewPager);
		}
		catch (Exception e)
		{
			Log.e("ERROR", e.getMessage());
		}
	}
}
