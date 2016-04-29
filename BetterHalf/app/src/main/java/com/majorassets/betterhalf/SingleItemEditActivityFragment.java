package com.majorassets.betterhalf;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.firebase.client.Firebase;
import com.majorassets.betterhalf.DataItemController.DataItemActivity;
import com.majorassets.betterhalf.DataItemController.DataItemActivityFragment;
import com.majorassets.betterhalf.Database.Firebase.FirebaseProvider;
import com.majorassets.betterhalf.Database.SQLite.DataDBSchema;
import com.majorassets.betterhalf.Database.SQLite.SQLiteItemsDAL;
import com.majorassets.betterhalf.Database.SQLite.SQLiteProvider;
import com.majorassets.betterhalf.Model.Entertainment.BookItem;
import com.majorassets.betterhalf.Model.Entertainment.GameItem;
import com.majorassets.betterhalf.Model.Entertainment.MovieItem;
import com.majorassets.betterhalf.Model.Entertainment.MusicItem;
import com.majorassets.betterhalf.Model.Entertainment.TVShowItem;
import com.majorassets.betterhalf.Model.Entertainment.TheaterItem;
import com.majorassets.betterhalf.Model.Subcategory;
import com.majorassets.betterhalf.Model.SubcategoryType;
import com.majorassets.betterhalf.Model.User;
/**
 * A placeholder fragment containing a simple view.
 */
public class SingleItemEditActivityFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private EditText mItemLabel;
    private EditText mItemValue;
    private Button mAddButton;
    private Spinner mSpinner;
    private DataItemActivityFragment mDataItemActivityFragment;

    private SQLiteProvider sqliteDB;
    private FirebaseProvider firebaseDB;

    private SQLiteItemsDAL dal;
    private Firebase userDataRef;

    private User appUser;
    private Subcategory subcategory;

    private String category;
    private String key;

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

        mItemLabel = (EditText) view.findViewById(R.id.item_name_edit);
        mItemValue = (EditText) view.findViewById(R.id.item_value_edit);
        mAddButton = (Button) view.findViewById(R.id.add_button);
        mSpinner = (Spinner) view.findViewById(R.id.key_select);

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

                String subcat = getActivity().getTitle().toString().replace(" ", "");
                subcategory = new Subcategory(SubcategoryType.getTypeFromString(subcat));

                writeDataToSQLite(subcategory);

                getActivity().finish();
            }
        });
    }

    private void writeDataToSQLite(Subcategory sub)
    {
        String label = mItemLabel.getText().toString();
        String value = mItemValue.getText().toString();

        switch(sub.getType())
        {
            case MOVIE:
                MovieItem movie = new MovieItem(label, value);
                movie.setID(appUser.getID()); //user to item relationship
                dal.addItem(movie, DataDBSchema.MoviesTable.NAME);
                break;
            case BOOK:
                BookItem book = new BookItem(key, value);
                book.setID(appUser.getID());
                dal.addItem(book, DataDBSchema.BooksTable.NAME);
                break;
            case MUSIC:
                MusicItem music = new MusicItem(key, value);
                music.setID(appUser.getID());
                dal.addItem(music, DataDBSchema.MusicTable.NAME);
                break;
            case GAME:
                GameItem game = new GameItem(key, value);
                game.setID(appUser.getID());
                dal.addItem(game, DataDBSchema.GamesTable.NAME);
                break;
            case THEATER:
                TheaterItem theater = new TheaterItem(key, value);
                theater.setID(appUser.getID());
                dal.addItem(theater, DataDBSchema.TheaterTable.NAME);
                break;
            case TV_SHOW:
                TVShowItem tvShow = new TVShowItem(key, value);
                tvShow.setID(appUser.getID());
                dal.addItem(tvShow, DataDBSchema.TVShowsTable.NAME);
                break;
        }
    }

    private void writeDatatoFirebase(Subcategory sub)
    {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        key = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
