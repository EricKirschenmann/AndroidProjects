package com.majorassets.betterhalf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.majorassets.betterhalf.DataItemController.DataItemActivity;

public class SingleItemEditActivity extends AppCompatActivity {
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        if(getIntent().hasExtra(DataItemActivity.CAT_TITLE_EXTRA)) {
            mTitle = getIntent().getStringExtra(DataItemActivity.CAT_TITLE_EXTRA);

            //Set theme per activity based on the category
            if (mTitle.equals("Entertainment")) {
                setTheme(R.style.EntertainmentTheme);
            } else if (mTitle.equals("Fashion")) {
                setTheme(R.style.FashionTheme);
            } else if (mTitle.equals("Food")) {
                setTheme(R.style.FoodTheme);
            } else if (mTitle.equals("Hobby")) {
                setTheme(R.style.HobbyTheme);
            } else if (mTitle.equals("Medical")) {
                setTheme(R.style.MedicalTheme);
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(Intent.EXTRA_REFERRER)) {
            String title = intent.getStringExtra(Intent.EXTRA_REFERRER);
            toolbar.setTitle(title + " Add");
        }

        setSupportActionBar(toolbar);

        //make sure intent has subcategory
        if(getIntent().hasExtra(DataItemActivity.SUBCAT_EXTRA)) {
            //get and set the title
            mTitle = getIntent().getStringExtra(DataItemActivity.SUBCAT_EXTRA);
        } else {
            mTitle = "Invalid";
        }
        setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_single_item_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()) {
            case R.id.action_home:
                intent = new Intent(SingleItemEditActivity.this, HomeActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                intent = new Intent(SingleItemEditActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
