package com.majorassets.betterhalf.DataItemController;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.majorassets.betterhalf.HomeActivity;
import com.majorassets.betterhalf.HomeActivityFragment;
import com.majorassets.betterhalf.R;
import com.majorassets.betterhalf.SettingsActivity;
import com.majorassets.betterhalf.SingleItemEditActivity;

public class DataItemActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private DataItemPagerAdapter mDataItemPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private FloatingActionButton mAddItemFab;
    private String mTitle;
    private TabLayout mTabLayout;

    public static final String SUBCAT_EXTRA = "com.majorassets.betterhalf.subcat";
    public static final String CAT_TITLE_EXTRA = "com.majorassests.betterhalf.cat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //get and set the title
        mTitle = getIntent().getStringExtra(HomeActivityFragment.TITLE_EXTRA);
        setTitle(mTitle);

        //Set theme per activity based on the category
        if(mTitle.equals("Entertainment")) {
            setTheme(R.style.EntertainmentTheme);
        }
        else if(mTitle.equals("Fashion")) {
            setTheme(R.style.FashionTheme);
        }
        else if(mTitle.equals("Food")) {
            setTheme(R.style.FoodTheme);
        }
        else if(mTitle.equals("Hobby")) {
            setTheme(R.style.HobbyTheme);
        }
        else if(mTitle.equals("Medical")) {
            setTheme(R.style.MedicalTheme);
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_item);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mDataItemPagerAdapter = new DataItemPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mDataItemPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

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
                String mSubCatTitle = tab.getText().toString();

                Intent intent = new Intent(DataItemActivity.this, SingleItemEditActivity.class);
                intent.putExtra(SUBCAT_EXTRA, mSubCatTitle); //Subcategory title
                intent.putExtra(CAT_TITLE_EXTRA, mTitle); //Main category title
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_data_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Intent intent;
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            intent = new Intent(DataItemActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_home) {
            intent = new Intent(DataItemActivity.this, HomeActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
