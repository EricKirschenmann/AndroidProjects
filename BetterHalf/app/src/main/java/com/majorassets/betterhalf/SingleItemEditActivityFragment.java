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
import com.majorassets.betterhalf.Database.SQLite.DataDBSchema;
import com.majorassets.betterhalf.Database.SQLite.SQLiteItemsDAL;
import com.majorassets.betterhalf.Model.BaseDataItem;
import com.majorassets.betterhalf.Model.Entertainment.BookItem;

/**
 * A placeholder fragment containing a simple view.
 */
public class SingleItemEditActivityFragment extends Fragment
{

    private EditText mItemName;
    private EditText mItemValue;
    private Button mAddButton;
    private DataItemActivityFragment mDataItemActivityFragment;
    private SQLiteItemsDAL itemdal;

    public SingleItemEditActivityFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view =  inflater.inflate(R.layout.fragment_single_item_edit, container, false);
        Firebase.setAndroidContext(getContext());

        mItemName = (EditText) view.findViewById(R.id.item_name_edit);
        mItemName.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mItemName.setSelection(0); //place cursor at the beginning
            }
        });

        mItemValue = (EditText) view.findViewById(R.id.item_value_edit);
        mItemValue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mItemValue.setSelection(0);
            }
        });

        //ADD BUTTON (CHANGED SOME VARIABLES TO PUBLIC FOR TESTING)
        mAddButton = (Button) view.findViewById(R.id.add_button);
        mAddButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mDataItemActivityFragment = new DataItemActivityFragment();
                //mDataItemActivityFragment.Books.add(mItemValue.getText().toString());//SHOULD ADD TO SQLITE
                mAddButton.setText("Yay!");

                // Adding an item to SQLite Database
                // Currently hardcoded to add to add to BooksTable
                // TODO make this work for all tables

                BookItem item = new BookItem(mItemName.getText().toString(), mItemValue.getText().toString()); // Replace this with the specific data type
                itemdal.addItem(item, DataDBSchema.BooksTable.NAME, DataDBSchema.BooksTable.Cols.UUID, DataDBSchema.BooksTable.Cols.LABEL, DataDBSchema.BooksTable.Cols.VALUE);

                Intent intent = new Intent();
                intent.setClass(getContext(), DataItemActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
