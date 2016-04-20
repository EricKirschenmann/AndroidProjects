package com.majorassets.betterhalf.Database.SQLite;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.majorassets.betterhalf.Model.Fashion.FashionItem;

/**
 * Created by Marissa on 4/19/2016.
 */
public class FashionCursorWrapper extends CursorWrapper {

    public FashionCursorWrapper(Cursor cursor)
    {
        super(cursor);
    }

    /*
    colLabel = the label from the schema of the item
        ex: BooksTable.Cols.LABEL
    colValue = the value from the schema
        ex: BooksTable.Cols.VALUE
*/
    public FashionItem getItem(String colLabel, String colValue){
        String label = getString(getColumnIndex(colLabel));
        String value = getString(getColumnIndex(colValue));

        FashionItem item = new FashionItem(label, value);
        return item;
    }
}