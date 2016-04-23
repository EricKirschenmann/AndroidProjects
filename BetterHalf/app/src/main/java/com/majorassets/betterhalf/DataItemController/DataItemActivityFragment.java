package com.majorassets.betterhalf.DataItemController;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.majorassets.betterhalf.R;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A placeholder fragment containing a simple view.
 */
public class DataItemActivityFragment extends Fragment
{
    private ArrayList<String> Array = new ArrayList<String>();
    public HashMap stuffs = new HashMap();
    private DataItemPagerAdapter mDataItemPagerAdapter;
    private ListView listView;

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
        Movies.add("The Force Awakens");
        Movies.add("10 Cloverfield Lane");
        Books.add("Silmarillion");
        Books.add("Aftermath");
        Allergies.add("Banana");
        Allergies.add("Gluten");
        stuffs.put("Books", Books);
        stuffs.put("Allergies", Allergies);
        stuffs.put("Movies", Movies);
        //SAMPLE DATA

        //DECLARE ADAPTER FOR LISTVIEW
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_expandable_list_item_1, Array);
        listView = (ListView) view.findViewById(android.R.id.text1);
        listView.setAdapter(adapter);

        mDataItemPagerAdapter = new DataItemPagerAdapter(getFragmentManager());
        Bundle args = getArguments();

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
}

