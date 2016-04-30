package com.majorassets.betterhalf.DataItemController;


import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.majorassets.betterhalf.Model.MainCategoryType;
import com.majorassets.betterhalf.Model.SubcategoryType;
import com.majorassets.betterhalf.Model.User;
import com.majorassets.betterhalf.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * A placeholder fragment containing a simple view.
 */
public class DataItemActivityFragment extends Fragment {
    private ArrayList<String> itemList = new ArrayList<>();
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
    private DataItemArrayAdapter mDataItemArrayAdapter;

	public static final String ARG_PAGE = "com.majorassets.betterhalf.page";

	//create a new instance of the fragment identifying it by an int argument
	public static DataItemActivityFragment newInstance(int page) {
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, page);
		DataItemActivityFragment fragment = new DataItemActivityFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view =  inflater.inflate(R.layout.fragment_data_list, container, false);

        data = new HashMap<>();
        appUser = GlobalResources.AppUser;

        //data layer components
        firebaseDB = FirebaseProvider.getDataProvider();
        sqliteDB = SQLiteProvider.getSQLiteProvider(getContext());
        dal = new SQLiteItemsDAL(sqliteDB.getDatabase());


        //DECLARE ADAPTER FOR LISTVIEW
        mArrayAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_expandable_list_item_1, itemList);
        mListView = (ListView) view.findViewById(android.R.id.text1);
        //mListView.setAdapter(mArrayAdapter);

        mDataItemPagerAdapter = new DataItemPagerAdapter(getFragmentManager());
        args = getArguments();

        readDataFromSQLite();

        // if user presses an item in the list Google search that item and its subcategory
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String query = mDataItemArrayAdapter.getValue(position);
                query += " " + mDataItemPagerAdapter.getPageTitle(args.getInt(DataItemActivityFragment.ARG_PAGE) - 1);
                searchWeb(query);
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {


                final String title = mDataItemPagerAdapter.getPageTitle(args.getInt(DataItemActivityFragment.ARG_PAGE)-1).toString();
                final SubcategoryType type = SubcategoryType.getTypeFromString(title.replace(" ", ""));
                final BaseDataItem item = data.get(type).get(position);
                final UUID id = item.getID();
                final MainCategoryType mainCat = MainCategoryType.getTypeFromString(getActivity().getTitle().toString());

                final String tableName = SubcategoryType.getStringFromType(mainCat, type);

                final SQLiteItemsDAL itemsDAL = dal;


                AlertDialog.Builder bobTheBuilder = new AlertDialog.Builder(getActivity());
                bobTheBuilder.setMessage(R.string.dialog_delete_item)
                        .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Delete Item here
                                //Call SQLite delete fn
                                itemsDAL.deleteItem(item, tableName, "uuid");

                                Intent intent = getActivity().getIntent();
                                getActivity().finish();
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.edit, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Edit here
                                //Go to update page
                            }
                        });
                // Creating the alertDialog
                AlertDialog alertDialog = bobTheBuilder.create();

                // Show the alert
                alertDialog.show();

                return true;
            }
        });

        return view;
	}

    @Override
    public void onResume() {
        super.onResume();
        readDataFromSQLite();
    }

    private void readDataFromSQLite() {
        List<BaseDataItem> items;

        int position = args.getInt(ARG_PAGE)-1;
        String table = mDataItemPagerAdapter.getPageTitle(position).toString().replace(" ", "");

        items = getItems(table);

        SubcategoryType type = SubcategoryType.getTypeFromString(table);
        data.put(type, items);

        //updateDisplay(items);
        updateDisplay2(items);
    }

    private List<BaseDataItem> getItems(String table) {
        List<BaseDataItem> items = null;

        switch (table) {
            case DataDBSchema.MoviesTable.NAME:
            case DataDBSchema.MusicTable.NAME:
            case DataDBSchema.GamesTable.NAME:
            case DataDBSchema.BooksTable.NAME:
            case DataDBSchema.TheaterTable.NAME:
            case DataDBSchema.TVShowsTable.NAME:
                items = dal.getEntertainmentItems(table, appUser.getID());
                break;

        }

        return items;
    }

    private void updateDisplay(List<BaseDataItem> items) {
        mArrayAdapter.clear();

        if(items != null && items.size() != 0) {
            for (BaseDataItem item : items)
                mArrayAdapter.add(item.getValue() + "\t\t\t-\t\t\t" + item.getLabel());
        }
    }

    private void updateDisplay2(List<BaseDataItem> items) {
        //ArrayList<BaseDataItem> objects = (ArrayList<BaseDataItem>) items;
        mDataItemArrayAdapter = new DataItemArrayAdapter(getContext(), items);
        mListView.setAdapter(mDataItemArrayAdapter);
    }

    //using implicit intents create a web search with a given string
    public void searchWeb(String query) {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, query);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}

