package com.majorassets.betterhalf.Database.SQLite.CursorWrappers;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.majorassets.betterhalf.Database.SQLite.DataDBSchema;
import com.majorassets.betterhalf.Model.Food.FoodItem;

import java.util.UUID;

/**
 * Created by Marissa on 4/19/2016.
 */
public class FoodCursorWrapper extends CursorWrapper {

    public FoodCursorWrapper(Cursor cursor)
    {
        super(cursor);
    }

    /*
        colLabel = the label from the schema of the item
            ex: BooksTable.Cols.LABEL
        colValue = the value from the schema
            ex: BooksTable.Cols.VALUE
    */
    public FoodItem getItem(){
        String ID = getString(getColumnIndex(DataDBSchema.BaseTable.Cols.UUID));
        String userID = getString(getColumnIndex(DataDBSchema.BaseTable.Cols.USER_ID));
        String label = getString(getColumnIndex(DataDBSchema.BaseTable.Cols.LABEL));
        String value = getString(getColumnIndex(DataDBSchema.BaseTable.Cols.VALUE));
        String favoriteStr = getString(getColumnIndex(DataDBSchema.BaseTable.Cols.FAVORITE));

        boolean isFavorite = favoriteStr.equals("1");

        FoodItem item = new FoodItem(label, value);
        item.setID(ID);
        item.setUserID(UUID.fromString(userID));
        item.setIsFavorite(isFavorite);

        return item;
    }
}
