package com.majorassets.expandablelistview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    * Preparing the list data
    */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Entertainment");
        listDataHeader.add("Fashion");
        listDataHeader.add("Food");
        listDataHeader.add("Hobbies");
        listDataHeader.add("Medical");
        listDataHeader.add("Other");

        // Adding child data
        List<String> food = new ArrayList<String>();
        food.add("Dishes");
        food.add("Drinks");
        food.add("Restaurants");
        food.add("Snacks");

        Collections.sort(food);

        List<String> entertainment = new ArrayList<String>();
        entertainment.add("Books");
        entertainment.add("Games");
        entertainment.add("Movies");
        entertainment.add("TV Shows");
        entertainment.add("Music");
        entertainment.add("Theater");

        Collections.sort(entertainment);

        List<String> fashion = new ArrayList<String>();
        fashion.add("Clothing");
        fashion.add("Shoes");
        fashion.add("Accessories");
        fashion.add("Jewelry");

        Collections.sort(fashion);

        List<String> hobbies = new ArrayList<String>();
        hobbies.add("Indoor");
        hobbies.add("Outdoor");
        hobbies.add("Sports");

        Collections.sort(hobbies);

        List<String> medical = new ArrayList<String>();
        medical.add("Allergies");
        medical.add("Illnesses");
        medical.add("Phobias");
        medical.add("Medication");

        Collections.sort(medical);

        List<String> other = new ArrayList<String>();
        other.add("Animals");
        other.add("Colors");
        other.add("Add Your Own");

        Collections.sort(other);


        listDataChild.put(listDataHeader.get(0), entertainment); // Header, Child data
        listDataChild.put(listDataHeader.get(1), fashion);
        listDataChild.put(listDataHeader.get(2), food);
        listDataChild.put(listDataHeader.get(3), hobbies);
        listDataChild.put(listDataHeader.get(4), medical);
        listDataChild.put(listDataHeader.get(5), other);
    }
}
