package com.majorassets.betterhalf.Database.SQLite.CursorWrappers;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.majorassets.betterhalf.Database.SQLite.DataDBSchema;
import com.majorassets.betterhalf.Model.BaseLikeableItem;
import com.majorassets.betterhalf.Model.Entertainment.EntertainmentItem;
import com.majorassets.betterhalf.Model.LikeableItem;

import java.util.UUID;

/**
 * Created by dgbla on 4/30/2016.
 */
public class BaseItemCursorWrapper extends CursorWrapper
{
    public BaseItemCursorWrapper(Cursor cursor)
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
    public BaseLikeableItem getItem(){
        String ID = getString(getColumnIndex(DataDBSchema.BaseTable.Cols.UUID));
        String userID = getString(getColumnIndex(DataDBSchema.BaseTable.Cols.USER_ID));
        String label = getString(getColumnIndex(DataDBSchema.BaseTable.Cols.LABEL));
        String value = getString(getColumnIndex(DataDBSchema.BaseTable.Cols.VALUE));

        BaseLikeableItem item = new LikeableItem(label, value);
        item.setID(ID);
        item.setUserID(UUID.fromString(userID));
        return item;
    }
}
