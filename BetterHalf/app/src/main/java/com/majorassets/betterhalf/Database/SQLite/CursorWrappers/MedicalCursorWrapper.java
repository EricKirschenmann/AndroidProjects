package com.majorassets.betterhalf.Database.SQLite.CursorWrappers;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.majorassets.betterhalf.Database.SQLite.DataDBSchema;
import com.majorassets.betterhalf.Model.Medical.MedicalItem;

import java.util.UUID;

/**
 * Created by Marissa on 4/19/2016.
 */
public class MedicalCursorWrapper extends CursorWrapper {

    public MedicalCursorWrapper(Cursor cursor)
    {
        super(cursor);
    }

    /*
        colLabel = the label from the schema of the item
            ex: BooksTable.Cols.LABEL
        colValue = the value from the schema
            ex: BooksTable.Cols.VALUE
    */
    public MedicalItem getItem(){
        String ID = getString(getColumnIndex(DataDBSchema.BaseTable.Cols.UUID));
        String label = getString(getColumnIndex(DataDBSchema.BaseTable.Cols.LABEL));
        String value = getString(getColumnIndex(DataDBSchema.BaseTable.Cols.VALUE));

        MedicalItem item = new MedicalItem(label, value);
        item.setID(ID);
        return item;
    }
}
