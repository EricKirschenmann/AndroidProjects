package com.majorassets.betterhalf.Database.SQLite;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.majorassets.betterhalf.Model.Hobbies.HobbyItem;

/**
 * Created by Marissa on 4/19/2016.
 */
public class HobbiesCursorWrapper extends CursorWrapper {

    public HobbiesCursorWrapper(Cursor cursor)
    {
        super(cursor);
    }

    /*
        colLabel = the label from the schema of the item
            ex: BooksTable.Cols.LABEL
        colValue = the value from the schema
            ex: BooksTable.Cols.VALUE
    */
    public HobbyItem getItem(String colLabel, String colValue){
        String label = getString(getColumnIndex(colLabel));
        String value = getString(getColumnIndex(colValue));

        HobbyItem item = new HobbyItem(label, value);
        return item;
    }
}
