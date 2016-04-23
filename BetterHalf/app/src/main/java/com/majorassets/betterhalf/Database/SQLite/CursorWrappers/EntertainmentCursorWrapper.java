package com.majorassets.betterhalf.Database.SQLite;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.majorassets.betterhalf.Model.Entertainment.EntertainmentItem;

import java.util.UUID;

/**
 * Created by Marissa on 4/19/2016.
 */
public class EntertainmentCursorWrapper extends CursorWrapper {

    public EntertainmentCursorWrapper(Cursor cursor)
    {
        super(cursor);
    }

    /*
        colLabel = the label from the schema of the item
            ex: BooksTable.Cols.LABEL
        colValue = the value from the schema
            ex: BooksTable.Cols.VALUE
        colID = UUID from schema
 */
    public EntertainmentItem getItem(String colUUID, String colLabel, String colValue){
        String userID = getString(getColumnIndex(colUUID));
        String label = getString(getColumnIndex(colLabel));
        String value = getString(getColumnIndex(colValue));

        EntertainmentItem item = new EntertainmentItem(label, value);
        item.setID(UUID.fromString(userID));
        return item;
    }
}
