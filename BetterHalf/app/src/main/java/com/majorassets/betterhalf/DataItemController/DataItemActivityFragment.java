package com.majorassets.betterhalf.DataItemController;


import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.majorassets.betterhalf.Database.Firebase.FirebaseProvider;
import com.majorassets.betterhalf.Database.SQLite.DataDBSchema;
import com.majorassets.betterhalf.Database.SQLite.SQLiteItemsDAL;
import com.majorassets.betterhalf.Database.SQLite.SQLiteProvider;
import com.majorassets.betterhalf.GlobalResources;
import com.majorassets.betterhalf.Model.BaseDataItem;
import com.majorassets.betterhalf.Model.BaseLikeableItem;
import com.majorassets.betterhalf.Model.Subcategory;
import com.majorassets.betterhalf.Model.SubcategoryType;
import com.majorassets.betterhalf.Model.User;
import com.majorassets.betterhalf.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A placeholder fragment containing a simple view.
 */
public class DataItemActivityFragment extends Fragment
{
    private ArrayList<String> Array = new ArrayList<String>();
    private DataItemPagerAdapter mDataItemPagerAdapter;

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

        Firebase.setAndroidContext(getContext());

		//setting the tool bar to primary color
		Window window = getActivity().getWindow();
		window.setStatusBarColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));

        appUser = GlobalResources.AppUser;

        //data layer components
        firebaseDB = FirebaseProvider.getDataProvider();
        sqliteDB = SQLiteProvider.getSQLiteProvider(getContext());
        dal = new SQLiteItemsDAL(sqliteDB.getDatabase());


        //DECLARE ADAPTER FOR LISTVIEW
        mArrayAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_expandable_list_item_1, Array);
        mListView = (ListView) view.findViewById(android.R.id.text1);
        mListView.setAdapter(mArrayAdapter);

        mDataItemPagerAdapter = new DataItemPagerAdapter(getFragmentManager());
        args = getArguments();

        readDataFromSQLite();

        //Add listener for the click on a list item to search the web
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String query = mArrayAdapter.getItem(position);
                searchWeb(query);
            }
        });

        return view;
	}

    @Override
    public void onResume()
    {
        super.onResume();
        readDataFromSQLite();
    }

    private void readDataFromSQLite()
    {
        List<BaseLikeableItem> items;

        int position = args.getInt(ARG_PAGE)-1;
        String table = mDataItemPagerAdapter.getPageTitle(position).toString().replace(" ", "");

        items = getItems(table);

        SubcategoryType type = SubcategoryType.getTypeFromTitle(table);

        updateDisplay(items);
    }

    private List<BaseLikeableItem> getItems(String table)
    {
        List<BaseLikeableItem> items = null;

        switch (table)
        {
            case DataDBSchema.MoviesTable.NAME:
            case DataDBSchema.MusicTable.NAME:
            case DataDBSchema.GamesTable.NAME:
            case DataDBSchema.BooksTable.NAME:
            case DataDBSchema.TheaterTable.NAME:
            case DataDBSchema.TVShowsTable.NAME:
                items = dal.getEntertainmentItems(table, appUser.getID());
                break;
            case DataDBSchema.AccessoriesTable.NAME:
            case DataDBSchema.ClothingTable.NAME:
            case DataDBSchema.JewelryTable.NAME:
            case DataDBSchema.ShoesTable.NAME:
                items = dal.getFashionItems(table, appUser.getID());
                break;
            case DataDBSchema.DrinksTable.NAME:
            case DataDBSchema.EntreesTable.NAME:
            case DataDBSchema.RestaurantsTable.NAME:
            case DataDBSchema.SidesTable.NAME:
            case DataDBSchema.SnacksTable.NAME:
                items = dal.getFoodItems(table, appUser.getID());
                break;
            case DataDBSchema.IndoorTable.NAME:
            case DataDBSchema.OutdoorTable.NAME:
            case DataDBSchema.SportsTable.NAME:
                items = dal.getHobbyItems(table, appUser.getID());
                break;
            case DataDBSchema.AllergiesTable.NAME:
            case DataDBSchema.IllnessesTable.NAME:
            case DataDBSchema.PhobiasTable.NAME:
            case DataDBSchema.MedicationTable.NAME:
                items = dal.getMedicalItems(table, appUser.getID());
                break;
            default:
                return null;

        }
        return items;
    }

    private void updateDisplay(List<BaseLikeableItem> items)
    {
        mArrayAdapter.clear();

        if(items != null && items.size() != 0)
        {
            for (BaseLikeableItem item : items)
                mArrayAdapter.add(item.getValue());
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

