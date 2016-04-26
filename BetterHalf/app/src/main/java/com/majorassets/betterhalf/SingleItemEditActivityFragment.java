package com.majorassets.betterhalf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.majorassets.betterhalf.DataItemController.DataItemActivity;
import com.majorassets.betterhalf.DataItemController.DataItemActivityFragment;
import com.majorassets.betterhalf.Database.Firebase.FirebaseProvider;
import com.majorassets.betterhalf.Database.SQLite.DataDBSchema;
import com.majorassets.betterhalf.Database.SQLite.SQLiteItemsDAL;
import com.majorassets.betterhalf.Database.SQLite.SQLiteProvider;
import com.majorassets.betterhalf.Model.Entertainment.BookItem;
import com.majorassets.betterhalf.Model.Entertainment.MovieItem;
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
                //TODO: store data item in SQLite
                //TODO: store data item in Firebase

                String subcat = getActivity().getTitle().toString();
                subcategory = new Subcategory(SubcategoryType.getTypeFromString(subcat));

                writeDataToSQLite(subcategory);

                Intent intent = new Intent(getContext(), DataItemActivity.class);
                intent.putExtra(HomeActivityFragment.TITLE_EXTRA, subcat);
                startActivity(intent);
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
                dal.addItem(movie, DataDBSchema.MoviesTable.NAME);
                break;
            case BOOK:
                BookItem book = new BookItem(label, value);
                dal.addItem(book, DataDBSchema.BooksTable.NAME);
                break;
        }
    }
}
