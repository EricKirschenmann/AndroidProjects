package com.majorassets.betterhalf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.majorassets.betterhalf.DataItemController.DataItemActivity;
import com.majorassets.betterhalf.DataItemController.DataItemActivityFragment;
import com.majorassets.betterhalf.Database.Firebase.FirebaseProvider;
import com.majorassets.betterhalf.Database.SQLite.DataDBSchema;
import com.majorassets.betterhalf.Database.SQLite.SQLiteItemsDAL;
import com.majorassets.betterhalf.Database.SQLite.SQLiteProvider;
import com.majorassets.betterhalf.Model.BaseDataItem;
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
import com.majorassets.betterhalf.Model.Subcategory;
import com.majorassets.betterhalf.Model.SubcategoryType;
import com.majorassets.betterhalf.Model.User;
/**
 * A placeholder fragment containing a simple view.
 */
public class SingleItemEditActivityFragment extends Fragment
{

    private EditText mItemLabel;
    private EditText mItemValue;
    private Button mAddButton;
    private CheckBox mFavorite;

    private DataItemActivityFragment mDataItemActivityFragment;

    private SQLiteProvider sqliteDB;
    private FirebaseProvider firebaseDB;

    private SQLiteItemsDAL dal;
    private Firebase userDataRef;

    private User appUser;
    private Subcategory subcategory;

    public SingleItemEditActivityFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view =  inflater.inflate(R.layout.fragment_single_item_edit, container, false);
        Firebase.setAndroidContext(getContext());

        initializeComponents(view);
        createEvents();

        return view;
    }

    private void initializeComponents(View view)
    {
        appUser = GlobalResources.AppUser;

        //data layer components
        firebaseDB = FirebaseProvider.getDataProvider();
        sqliteDB = SQLiteProvider.getSQLiteProvider(getContext());
        dal = new SQLiteItemsDAL(sqliteDB.getDatabase());

        userDataRef = firebaseDB.getUserDataInstance(appUser.getUsername());

        //UI components
        mItemLabel = (EditText) view.findViewById(R.id.item_name_edit);
        mItemValue = (EditText) view.findViewById(R.id.item_value_edit);
        mAddButton = (Button) view.findViewById(R.id.add_button);
        mFavorite = (CheckBox) view.findViewById(R.id.favorite_checkbox);
    }

    private void createEvents()
    {
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

                String subcat = getActivity().getTitle().toString().replace(" ", "");
                subcategory = new Subcategory(SubcategoryType.getTypeFromString(subcat));

                writeDataToSQLite(subcategory, mFavorite.isChecked());

                /*Intent intent = new Intent(getContext(), DataItemActivity.class);
                intent.putExtra(HomeActivityFragment.TITLE_EXTRA, subcat);
                startActivity(intent);*/

                getActivity().finish();
            }
        });
    }

    private void writeDataToSQLite(Subcategory sub, boolean isFavorite)
    {
        String label = mItemLabel.getText().toString();
        String value = mItemValue.getText().toString();
        String table = "";
        BaseLikeableItem item = null;

        switch(sub.getType())
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
        item.setID(appUser.getID()); //create relationship between user and data tables
        dal.addItem(item, table);
    }

    private void writeDatatoFirebase(Subcategory sub)
    {

    }
}
