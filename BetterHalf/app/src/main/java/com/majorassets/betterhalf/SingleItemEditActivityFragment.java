package com.majorassets.betterhalf;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.firebase.client.Firebase;
import com.majorassets.betterhalf.DataItemController.DataItemActivity;
import com.majorassets.betterhalf.Database.Firebase.FirebaseProvider;
import com.majorassets.betterhalf.Database.SQLite.DataDBSchema;
import com.majorassets.betterhalf.Database.SQLite.SQLiteItemsDAL;
import com.majorassets.betterhalf.Database.SQLite.SQLiteProvider;
import com.majorassets.betterhalf.Model.BaseLikeableItem;
import com.majorassets.betterhalf.Model.Entertainment.BookItem;
import com.majorassets.betterhalf.Model.Entertainment.GameItem;
import com.majorassets.betterhalf.Model.Entertainment.MovieItem;
import com.majorassets.betterhalf.Model.Entertainment.MusicItem;
import com.majorassets.betterhalf.Model.Entertainment.TVShowItem;
import com.majorassets.betterhalf.Model.Entertainment.TheaterItem;
import com.majorassets.betterhalf.Model.Fashion.AccessoriesItem;
import com.majorassets.betterhalf.Model.Fashion.ClothingItem;
import com.majorassets.betterhalf.Model.Fashion.JewelryItem;
import com.majorassets.betterhalf.Model.Fashion.ShoesItem;
import com.majorassets.betterhalf.Model.Food.DrinksItem;
import com.majorassets.betterhalf.Model.Food.EntreesItem;
import com.majorassets.betterhalf.Model.Food.RestaurantsItem;
import com.majorassets.betterhalf.Model.Food.SidesItem;
import com.majorassets.betterhalf.Model.Food.SnacksItem;
import com.majorassets.betterhalf.Model.Hobbies.IndoorItem;
import com.majorassets.betterhalf.Model.Hobbies.OutdoorItem;
import com.majorassets.betterhalf.Model.Hobbies.SportsItem;
import com.majorassets.betterhalf.Model.Medical.AllergiesItem;
import com.majorassets.betterhalf.Model.Medical.IllnessesItem;
import com.majorassets.betterhalf.Model.Medical.MedicalItem;
import com.majorassets.betterhalf.Model.Medical.PhobiasItem;
import com.majorassets.betterhalf.Model.SubcategoryType;
import com.majorassets.betterhalf.Model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class SingleItemEditActivityFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private EditText mItemLabel;
    private EditText mItemValue;
    private Button mAddButton;
    private Spinner mSpinner;
    private CheckBox mFavorite;

    private SQLiteProvider sqliteDB;
    private FirebaseProvider firebaseDB;

    private SQLiteItemsDAL dal;
    private Firebase userDataRef;

    private User appUser;
    private Map<SubcategoryType, List<BaseLikeableItem>> userDataMap;

    private String category;
    private String key;

    public SingleItemEditActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_single_item_edit, container, false);
        Firebase.setAndroidContext(getContext());

        initializeComponents(view);
        createEvents();

        return view;
    }

    private void initializeComponents(View view) {
        appUser = GlobalResources.AppUser;
        userDataMap = appUser.getDataItemRepository().getDataItems();

        //data layer components
        firebaseDB = FirebaseProvider.getDataProvider();
        sqliteDB = SQLiteProvider.getSQLiteProvider(getContext());
        dal = new SQLiteItemsDAL(sqliteDB.getDatabase());

        userDataRef = firebaseDB.getUserDataInstance(appUser.getUsername());

        mItemLabel = (EditText) view.findViewById(R.id.item_name_edit);
        mItemValue = (EditText) view.findViewById(R.id.item_value_edit);
        mAddButton = (Button) view.findViewById(R.id.add_button);
        mSpinner = (Spinner) view.findViewById(R.id.key_select);
        mFavorite = (CheckBox) view.findViewById(R.id.favorite_checkbox);

        category = getActivity().getIntent().getStringExtra(DataItemActivity.SUBCAT_EXTRA);

        setSpinner();
    }

    private void setSpinner() {
        if(category.equals("Books")) {
            mItemLabel.setVisibility(View.INVISIBLE);
            mSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.books_array, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            mSpinner.setOnItemSelectedListener(this);
        } else if(category.equals("Games")) {
            mItemLabel.setVisibility(View.INVISIBLE);
            mSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.games_array, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            mSpinner.setOnItemSelectedListener(this);
        } else if(category.equals("Movies")) {
            mItemLabel.setVisibility(View.INVISIBLE);
            mSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.movies_array, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            mSpinner.setOnItemSelectedListener(this);
        } else if(category.equals("Music")) {
            mItemLabel.setVisibility(View.INVISIBLE);
            mSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.music_array, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            mSpinner.setOnItemSelectedListener(this);
        } else if(category.equals("Theater")) {
            mItemLabel.setVisibility(View.INVISIBLE);
            mSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.theater_array, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            mSpinner.setOnItemSelectedListener(this);
        } else if(category.equals("TV Shows")) {
            mItemLabel.setVisibility(View.INVISIBLE);
            mSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.tvshow_array, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            mSpinner.setOnItemSelectedListener(this);
        } else if(category.equals("Accessories")) {
            mItemLabel.setVisibility(View.INVISIBLE);
            mSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.accessories_array, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            mSpinner.setOnItemSelectedListener(this);
        } else if(category.equals("Clothing")) {
            mItemLabel.setVisibility(View.INVISIBLE);
            mSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.clothing_array, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            mSpinner.setOnItemSelectedListener(this);
        } else if(category.equals("Jewelry")) {
            mItemLabel.setVisibility(View.INVISIBLE);
            mSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.jewelry_array, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            mSpinner.setOnItemSelectedListener(this);
        } else if(category.equals("Shoes")) {
            mItemLabel.setVisibility(View.INVISIBLE);
            mSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.shoes_array, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            mSpinner.setOnItemSelectedListener(this);
        } else if(category.equals("Drinks")) {
            mItemLabel.setVisibility(View.INVISIBLE);
            mSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.drinks_array, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            mSpinner.setOnItemSelectedListener(this);
        } else if(category.equals("Entrees")) {
            mItemLabel.setVisibility(View.INVISIBLE);
            mSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.entrees_array, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            mSpinner.setOnItemSelectedListener(this);
        } else if(category.equals("Restaurants")) {
            mItemLabel.setVisibility(View.INVISIBLE);
            mSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.restaurants_array, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            mSpinner.setOnItemSelectedListener(this);
        } else if(category.equals("Sides")) {
            mItemLabel.setVisibility(View.INVISIBLE);
            mSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.sides_array, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            mSpinner.setOnItemSelectedListener(this);
        } else if(category.equals("Snacks")) {
            mItemLabel.setVisibility(View.INVISIBLE);
            mSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.snacks_array, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            mSpinner.setOnItemSelectedListener(this);
        } else if(category.equals("Indoor Hobbies")) {
            mItemLabel.setVisibility(View.INVISIBLE);
            mSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.indoor_array, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            mSpinner.setOnItemSelectedListener(this);
        } else if(category.equals("Outdoor Hobbies")) {
            mItemLabel.setVisibility(View.INVISIBLE);
            mSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.outdoor_array, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            mSpinner.setOnItemSelectedListener(this);
        } else if(category.equals("Sports Teams")) {
            mItemLabel.setVisibility(View.INVISIBLE);
            mSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.sports_array, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            mSpinner.setOnItemSelectedListener(this);
        } else if(category.equals("Allergies")) {
            mItemLabel.setVisibility(View.INVISIBLE);
            mSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.allergies_array, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            mSpinner.setOnItemSelectedListener(this);
        } else if(category.equals("Illnesses")) {
            mItemLabel.setVisibility(View.INVISIBLE);
            mSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.illnesses_array, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            mSpinner.setOnItemSelectedListener(this);
        } else if(category.equals("Medications")) {
            mItemLabel.setVisibility(View.INVISIBLE);
            mSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.medications_array, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            mSpinner.setOnItemSelectedListener(this);
        } else if(category.equals("Phobias")) {
            mItemLabel.setVisibility(View.INVISIBLE);
            mSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.phobias_array, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            mSpinner.setOnItemSelectedListener(this);
        } else {
            mSpinner.setVisibility(View.INVISIBLE);
        }
    }

    private void createEvents() {
        mItemLabel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mItemLabel.setSelection(0); //place cursor at the beginning
            }
        });

        mItemValue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mItemValue.setSelection(0);
            }
        });

        //ADD BUTTON (CHANGED SOME VARIABLES TO PUBLIC FOR TESTING)
        mAddButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //TODO: store data item in Firebase

                String subcat = getActivity().getTitle().toString().replace(" ", ""); //take out spaces
                SubcategoryType type = SubcategoryType.getTypeFromTitle(subcat);

                writeDataToSQLite(type, mFavorite.isChecked());

                //quit the activity and return to previous activity
                getActivity().finish();
            }
        });
    }

    private void writeDataToSQLite(SubcategoryType type, boolean isFavorite)
    {
        String label;
        if(mItemLabel.getVisibility() == View.VISIBLE)
            label = mItemLabel.getText().toString();
        else
            label = mSpinner.getSelectedItem().toString();

        String value = mItemValue.getText().toString();
        String table = "";
        BaseLikeableItem item = null;

        switch(type)
        {
            //ENTERTAINMENT
            case MOVIE:
                item = new MovieItem(label, value);
                table = DataDBSchema.MoviesTable.NAME;
                break;
            case BOOK:
                item = new BookItem(label, value);
                table = DataDBSchema.BooksTable.NAME;
                break;
            case MUSIC:
                item = new MusicItem(label, value);
                table = DataDBSchema.MusicTable.NAME;
                break;
            case GAME:
                item = new GameItem(label, value);
                table = DataDBSchema.GamesTable.NAME;
                break;
            case THEATER:
                item = new TheaterItem(label, value);
                table = DataDBSchema.TheaterTable.NAME;
                break;
            case TV_SHOW:
                item = new TVShowItem(label, value);
                table = DataDBSchema.TVShowsTable.NAME;
                break;
            //FASHION
            case ACCESSORY:
                item = new AccessoriesItem(label, value);
                table = DataDBSchema.AccessoriesTable.NAME;
                break;
            case CLOTHING:
                item = new ClothingItem(label, value);
                table = DataDBSchema.ClothingTable.NAME;
                break;
            case JEWELRY:
                item = new JewelryItem(label, value);
                table = DataDBSchema.JewelryTable.NAME;
                break;
            case SHOE:
                item = new ShoesItem(label, value);
                table = DataDBSchema.ShoesTable.NAME;
                break;
            //FOOD
            case DRINK:
                item = new DrinksItem(label, value);
                table = DataDBSchema.DrinksTable.NAME;
                break;
            case ENTREE:
                item = new EntreesItem(label, value);
                table = DataDBSchema.EntreesTable.NAME;
                break;
            case RESTAURANT:
                item = new RestaurantsItem(label, value);
                table = DataDBSchema.RestaurantsTable.NAME;
                break;
            case SIDE:
                item = new SidesItem(label, value);
                table = DataDBSchema.SidesTable.NAME;
                break;
            case SNACK:
                item = new SnacksItem(label, value);
                table = DataDBSchema.SnacksTable.NAME;
                break;
            //HOBBY
            case INDOOR:
                item = new IndoorItem(label, value);
                table = DataDBSchema.IndoorTable.NAME;
                break;
            case OUTDOOR:
                item = new OutdoorItem(label, value);
                table = DataDBSchema.OutdoorTable.NAME;
                break;
            case SPORT:
                item = new SportsItem(label, value);
                table = DataDBSchema.SportsTable.NAME;
                break;
            //MEDICAL
            case ALLERGY:
                item = new AllergiesItem(label, value);
                table = DataDBSchema.AllergiesTable.NAME;
                break;
            case PHOBIA:
                item = new PhobiasItem(label, value);
                table = DataDBSchema.PhobiasTable.NAME;
                break;
            case MEDICATION:
                item = new MedicalItem(label, value);
                table = DataDBSchema.MedicationTable.NAME;
                break;
            case ILLNESS:
                item = new IllnessesItem(label, value);
                table = DataDBSchema.IllnessesTable.NAME;
                break;
        }

        item.setIsFavorite(isFavorite);
        item.setUserID(appUser.getID()); //create relationship between user and data tables
        dal.addItem(item, table);

        item.setID(String.valueOf(dal.getItemID(item, table)));
        addItemToUserMap(type, item);
    }

    private void addItemToUserMap(SubcategoryType type, BaseLikeableItem item)
    {
        List<BaseLikeableItem> list;

        if(userDataMap.get(type) == null)
        {
            list = new ArrayList<>();
            list.add(item);
            userDataMap.put(type, list);
        }
        else
        {
            list = userDataMap.get(type);
            list.add(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        key = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
