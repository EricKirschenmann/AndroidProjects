package com.majorassets.betterhalf.DataItemController;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.majorassets.betterhalf.Database.Firebase.FirebaseProvider;
import com.majorassets.betterhalf.Database.SQLite.SQLiteItemsDAL;
import com.majorassets.betterhalf.Database.SQLite.SQLiteProvider;
import com.majorassets.betterhalf.GlobalResources;
import com.majorassets.betterhalf.Model.BaseDataItem;
import com.majorassets.betterhalf.Model.Subcategory;
import com.majorassets.betterhalf.Model.SubcategoryType;
import com.majorassets.betterhalf.Model.User;
import com.majorassets.betterhalf.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A placeholder fragment containing a simple view.
 */
public class DataItemActivityFragment extends Fragment
{
    private ArrayList<String> Array = new ArrayList<String>();
    public HashMap stuffs = new HashMap();
    private DataItemPagerAdapter mDataItemPagerAdapter;
    private Map<SubcategoryType, List<BaseDataItem>> data;

    private SQLiteProvider sqliteDB;
    private FirebaseProvider firebaseDB;

    private SQLiteItemsDAL dal;
    private Firebase userDataRef;

    private User appUser;

    private Bundle args;
    private ListView mListView;
    private ArrayAdapter<String> mArrayAdapter;

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

		//setting the tool bar to primary color
		Window window = getActivity().getWindow();
		window.setStatusBarColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));

        data = new HashMap<>();
        appUser = GlobalResources.AppUser;

        //data layer components
        firebaseDB = FirebaseProvider.getDataProvider();
        sqliteDB = SQLiteProvider.getSQLiteProvider(getContext());
        dal = new SQLiteItemsDAL(sqliteDB.getDatabase());

        //SAMPLE DATA
        ArrayList<String> Movies = new ArrayList<String>();
        ArrayList<String> Books = new ArrayList<String>();
        ArrayList<String> Allergies = new ArrayList<String>();
        Movies.add("The Force Awakens");
        Movies.add("10 Cloverfield Lane");
        Books.add("Silmarillion");
        Books.add("Aftermath");
        Allergies.add("Banana");
        Allergies.add("Gluten");
        //stuffs.put("Books", Books);
        //stuffs.put("Allergies", Allergies);
        //stuffs.put("Movies", Movies);
        //SAMPLE DATA

        //DECLARE ADAPTER FOR LISTVIEW
        mArrayAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_expandable_list_item_1, Array);
        mListView = (ListView) view.findViewById(android.R.id.text1);
        mListView.setAdapter(mArrayAdapter);

        mDataItemPagerAdapter = new DataItemPagerAdapter(getFragmentManager());
        args = getArguments();

        readDataFromSQLite(view);

        return view;
	}

    private void readDataFromSQLite(View view)
    {
        List<BaseDataItem> items;

        int position = args.getInt(ARG_PAGE)-1;
        String table = mDataItemPagerAdapter.getPageTitle(position).toString();
        items = dal.getEntertainmentItems(table);

        SubcategoryType type = SubcategoryType.getTypeFromString(table);
        data.put(type, items);

        updateDisplay(type);
    }

    private void updateDisplay(SubcategoryType type)
    {
        //DECLARE VARIABLES FOR LISTVIEW
        String keyString = null;
        List<BaseDataItem> dataArrayHolder = null;

        //FIND CURRENT TAB AND FIND DATA ARRAY TO FILL LIST(IF EXISTS)(SHOULD PULL FROM SQLITE)
        if(data.containsKey(type))
        {
            for(int j=1 ; j<mDataItemPagerAdapter.getCount() ; j++)
            {
                if (args.getInt(ARG_PAGE) == j)
                {
                    mArrayAdapter.clear();
                    keyString = mDataItemPagerAdapter.getPageTitle(j-1).toString();
                    SubcategoryType keyType = SubcategoryType.getTypeFromString(keyString);
                    if(data.containsKey(keyType))
                        dataArrayHolder = data.get(keyType);

                    if (dataArrayHolder != null)
                        for (int i=0; i < dataArrayHolder.size(); i++)
                            mArrayAdapter.add((dataArrayHolder).get(i).getValue());
                }
            }
        }
    }
}

