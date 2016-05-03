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

import com.firebase.client.Firebase;
import com.majorassets.betterhalf.Database.Firebase.FirebaseProvider;
import com.majorassets.betterhalf.Database.SQLite.SQLiteProvider;
import com.majorassets.betterhalf.Database.SQLite.SQLiteUserDAL;
import com.majorassets.betterhalf.GlobalResources;
import com.majorassets.betterhalf.HomeActivity;
import com.majorassets.betterhalf.HomeActivityFragment;
import com.majorassets.betterhalf.LoginActivity;
import com.majorassets.betterhalf.Model.User;
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

    private User appUser;

    private SQLiteProvider sqliteDB;
    private SQLiteUserDAL userDAL;

    private FirebaseProvider firebaseDB;
    private Firebase ref;

    public static final String SUBCAT_EXTRA = "com.majorassets.betterhalf.subcat";
    public static final String CAT_TITLE_EXTRA = "com.majorassests.betterhalf.cat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_item);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        appUser = GlobalResources.AppUser;
        sqliteDB = SQLiteProvider.getSQLiteProvider(this);
        userDAL = new SQLiteUserDAL(sqliteDB.getDatabase());

        firebaseDB = FirebaseProvider.getDataProvider();
        ref = firebaseDB.getFirebaseInstance();

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

        mTitle = getIntent().getStringExtra(HomeActivityFragment.TITLE_EXTRA);
        setTitle(mTitle);

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
        if (id == R.id.action_settings)
        {
            intent = new Intent(DataItemActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_logout)
        {
            //this user is is officially logged out - was NOT logged on last
            appUser.setLoggedOnLast(false);
            //update the user in SQLite
            userDAL.updateUser(appUser);

            //TODO: popup an "Are you sure?" dialog (fragment) and logout through Firebase API
            ref.unauth(); //un-authenticate a user from firebase
            //return to login screen
            intent = new Intent(DataItemActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
