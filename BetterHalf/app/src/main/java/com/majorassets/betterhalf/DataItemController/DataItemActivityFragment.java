package com.majorassets.betterhalf.DataItemController;


import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.majorassets.betterhalf.R;
import com.majorassets.betterhalf.SingleItemEditActivity;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A placeholder fragment containing a simple view.
 */
public class DataItemActivityFragment extends Fragment
{

    //TODO: Find out why onCreateView runs twice whenever the activity is opened

    private ArrayList<String> Array = new ArrayList<String>();
    public HashMap stuffs = new HashMap();
    private DataItemPagerAdapter mDataItemPagerAdapter;
    private ListView listView;
    private FloatingActionButton mAddItemFab;
    private String[] titles;

	public static final String ARG_PAGE = "com.majorassets.betterhalf.page";

	//create a new instance of the fragment identifying it by an int argument
	public static DataItemActivityFragment newInstance(int page)
	{
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, page);
		DataItemActivityFragment fragment = new DataItemActivityFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view =  inflater.inflate(R.layout.fragment_data_list, container, false);

        //SAMPLE DATA
        ArrayList<String> Movies = new ArrayList<>();
        ArrayList<String> Books = new ArrayList<>();
        ArrayList<String> Allergies = new ArrayList<>();
        ArrayList<String> Jewelry = new ArrayList<>();
        ArrayList<String> Music = new ArrayList<>();
        Movies.add("The Force Awakens");
        Movies.add("10 Cloverfield Lane");
        Books.add("Silmarillion");
        Books.add("Aftermath");
        Allergies.add("Banana");
        Allergies.add("Gluten");
        Music.add("Phil Collins");
        stuffs.put("Books", Books);
        stuffs.put("Allergies", Allergies);
        stuffs.put("Movies", Movies);
        stuffs.put("Music", Music);
        //SAMPLE DATA

        //DECLARE ADAPTER FOR LISTVIEW
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_expandable_list_item_1, Array);
        listView = (ListView) view.findViewById(android.R.id.text1);
        listView.setAdapter(adapter);

        mDataItemPagerAdapter = new DataItemPagerAdapter(getFragmentManager());
        final Bundle args = getArguments();
        //store all the titles into an array
        getTitles();


        /**
         * HOW DO YOU GET THE CURRENT PAGE TITLE!!!! I FORGOT -_- REMEMBER THIS
         */
        //get reference to floating action button in bottom right of screen
        mAddItemFab = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_item);
        mAddItemFab.setOnClickListener(new View.OnClickListener()
        {
            //have the click start the edit screen for a single item
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), SingleItemEditActivity.class);
                //gives wrong numbers!! check why
                intent.putExtra(Intent.EXTRA_REFERRER, titles[args.getInt(DataItemActivityFragment.ARG_PAGE) - 1]);
                startActivity(intent);
            }
        });

        //Add listener for the click on a list item to search the web
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String query = adapter.getItem(position);
                query += " "+ titles[getPageNum(args)];
                searchWeb(query);
            }
        });

        //DECLARE VARIABLES FOR LISTVIEW
        String keyString = null;
        Object dataArrayHolder = null;
        int dataArraySize = 0;

        //FIND CURRENT TAB AND FIND DATA ARRAY TO FILL LIST(IF EXISTS)(SHOULD PULL FROM SQLITE)
        if(stuffs.containsKey(mDataItemPagerAdapter.getPageTitle(0).toString())){
            for(int j=1 ; j<mDataItemPagerAdapter.getCount() ; j++) {
                if (args.getInt(DataItemActivityFragment.ARG_PAGE) == j) {
                    adapter.clear();
                    keyString = mDataItemPagerAdapter.getPageTitle(j-1).toString();
                    if(stuffs.containsKey(keyString)) {
                        dataArrayHolder = stuffs.get(keyString);
                        dataArraySize = ((ArrayList) dataArrayHolder).size();
                    }
                    for (int i=0; i < dataArraySize; i++) {
                        adapter.add(((ArrayList) dataArrayHolder).get(i).toString());
                    }
                }
            }
        }

        return view;
	}

    private int getPageNum(Bundle args) {
        return args.getInt(DataItemActivityFragment.ARG_PAGE) - 1;
    }

    private void getTitles() {
        titles = new String[mDataItemPagerAdapter.getCount()];

        for(int i = 0; i < mDataItemPagerAdapter.getCount(); i++) {
            titles[i] = mDataItemPagerAdapter.getPageTitle(i).toString();
        }
    }

    public void searchWeb(String query) {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, query);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}

