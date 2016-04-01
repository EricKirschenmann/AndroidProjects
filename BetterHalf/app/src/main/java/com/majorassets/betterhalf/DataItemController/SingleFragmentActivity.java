package com.majorassets.betterhalf.DataItemController;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.majorassets.betterhalf.HomeActivityFragment;
import com.majorassets.betterhalf.R;
import com.majorassets.betterhalf.SingleItemEditActivity;

public abstract class SingleFragmentActivity extends AppCompatActivity
{
	private FloatingActionButton mAddItemFab;
	private String mTitle;

	private DataItemPagerAdapter mDataItemPagerAdapter;
	private ViewPager mViewPager;
	private TabLayout mTabLayout;

	protected abstract Fragment createFragment();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);

		//TODO: have toolbar not cover up content on underlying fragment
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
				Intent intent = new Intent(SingleFragmentActivity.this, SingleItemEditActivity.class);
				startActivity(intent);
			}
		});

		mTitle = getIntent().getStringExtra(HomeActivityFragment.TITLE_EXTRA);
		setTitle(mTitle);

		setUpTabPages();

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_data_list, menu);
		return true;
	}

	private void setUpTabPages()
	{
		mDataItemPagerAdapter = new DataItemPagerAdapter(getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);

		mViewPager.setAdapter(mDataItemPagerAdapter);

		mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
		mTabLayout.setupWithViewPager(mViewPager);
	}

	/*@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		Intent intent;
		switch(item.getItemId())
		{
			case R.id.action_add_item:
				intent = new Intent(SingleFragmentActivity.this, SingleItemEditActivity.class);
				startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}*/
}
