package com.majorassets.betterhalf.Database.SQLite;

import android.content.ContentValues;
import android.database.CursorWrapper;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.majorassets.betterhalf.Database.SQLite.CursorWrappers.EntertainmentCursorWrapper;
import com.majorassets.betterhalf.Model.BaseDataItem;
import com.majorassets.betterhalf.Model.Subcategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marissa on 4/20/2016.
 */
public class SQLiteItemsDAL implements ISQLiteItemsDAL {

    private SQLiteDatabase db;

    public SQLiteItemsDAL(SQLiteDatabase db)
    {
        this.db = db;
    }

    /*
        Each of the functions in these methods can be used for any of
        the data items. When using the functions, you must specify
        which type of item you are using by using the following as
        a guide:

        tableName = name of table from schema
            ex: BooksTable.NAME
        colLabel = the label from the schema of the item
            ex: BooksTable.Cols.LABEL
        colValue = the value from the schema
            ex: BooksTable.Cols.VALUE
        colID = UUID from schema
            ex: BooksTable.Cols.UUID
    */



    // Add an item to database
    public void addItem(BaseDataItem item, String tableName){
        ContentValues values = SQLiteProvider.getDataContentValues(item);
        try
        {
            db.insertOrThrow(tableName, null, values);
        }
        catch(SQLException ex)
        {
            Log.d("ERROR", ex.getMessage());
        }
    }

    // Change an existing item
    public void updateItem(BaseDataItem item, String tableName, String colUUID, String colLabel, String colValue){
        String itemID = item.getID().toString();
        ContentValues values = SQLiteProvider.getDataContentValues(item);

        db.update(tableName, values, colUUID + " = ?", new String[] {itemID});
    }

    // Remove an existing item
    public BaseDataItem deleteItem(BaseDataItem item, String tableName, String colUUID){
        String itemID = item.getID().toString();

        db.delete(tableName, colUUID + "= ?", new String[]{itemID});

        return item;
    }

    @Override
    public List<BaseDataItem> getEntertainmentItems(String tableName)
    {
        EntertainmentCursorWrapper cursor = SQLiteProvider.queryEntertainmentItem(tableName, null, null);

        List<BaseDataItem> items = new ArrayList<>();
        try{
            if(cursor.getCount() == 0)
                return null;

            cursor.moveToFirst();

            //iterate the cursor over each record in DB
            while(!cursor.isAfterLast())
            {
                items.add(cursor.getItem());
                cursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }

        return items;
    }


}
