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
import com.majorassets.betterhalf.Model.BaseLikeableItem;
import com.majorassets.betterhalf.Model.MainCategoryType;
import com.majorassets.betterhalf.Model.SubcategoryType;
import com.majorassets.betterhalf.Model.User;
import com.majorassets.betterhalf.R;
import com.majorassets.betterhalf.SingleItemEditActivity;

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
    private Map<SubcategoryType, List<BaseLikeableItem>> data;

    private SQLiteProvider sqliteDB;
    private FirebaseProvider firebaseDB;

    private SQLiteItemsDAL dal;
    private Firebase userDataRef;

    private User appUser;

    private Bundle args;
    private ListView mListView;
    private DataItemArrayAdapter mDataItemArrayAdapter;
    private DataItemPagerAdapter mDataItemPagerAdapter;


    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_PAGE = "com.majorassets.betterhalf.page";

    /**
     * Argument when a user wishes to edit a specific item. The current data is passed forward to
     * SingleItemEditActivty
     */
    private static final String EDIT = "com.majorassets.betterhalf.edit";

    public DataItemActivityFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static DataItemActivityFragment newInstance(int sectionNumber) {
        DataItemActivityFragment fragment = new DataItemActivityFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_data_item, container, false);
//        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));


        data = new HashMap<>();
        appUser = GlobalResources.AppUser;

        //data layer components
        firebaseDB = FirebaseProvider.getDataProvider();
        sqliteDB = SQLiteProvider.getSQLiteProvider(getContext());
        dal = new SQLiteItemsDAL(sqliteDB.getDatabase());


        //DECLARE ADAPTER FOR LISTVIEW
        mListView = (ListView) rootView.findViewById(android.R.id.text2);

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
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, final int position, long l) {

                final String title = mDataItemPagerAdapter.getPageTitle(args.getInt(DataItemActivityFragment.ARG_PAGE)-1).toString();
                final SubcategoryType type = SubcategoryType.getTypeFromTitle(title.replace(" ", ""));
                final BaseDataItem item = data.get(type).get(position);

                final String tableName = SubcategoryType.getDisplayableStringsFromType(type, true);

                final SQLiteItemsDAL itemsDAL = dal;


                AlertDialog.Builder bobTheBuilder = new AlertDialog.Builder(getActivity());
                bobTheBuilder.setMessage(R.string.dialog_delete_item)
                        .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Delete Item here
                                //Call SQLite delete fn
                                itemsDAL.deleteItem(item, tableName, "uuid");

                                //refresh data within the listview without restarting the activity
                                readDataFromSQLite();
                            }
                        })
                        .setNegativeButton(R.string.edit, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Edit here
                                Intent editIntent = new Intent(getContext(), SingleItemEditActivity.class);
                                editIntent.putExtra(EDIT, item.getValue());
                            }
                        });
                // Creating the alertDialog
                AlertDialog alertDialog = bobTheBuilder.create();

                // Show the alert
                alertDialog.show();

                return true;
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        readDataFromSQLite();
    }

    private void readDataFromSQLite() {
        List<BaseLikeableItem> items;

        int position = args.getInt(ARG_PAGE)-1;
        String table = mDataItemPagerAdapter.getPageTitle(position).toString().replace(" ", "");

        items = getItems(table);

        SubcategoryType type = SubcategoryType.getTypeFromTitle(table);
        data.put(type, items);

        updateDisplay(items);
    }

    private List<BaseLikeableItem> getItems(String table) {
        List<BaseLikeableItem> items = null;

        switch (table) {
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

    private void updateDisplay(List<BaseLikeableItem> items) {
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