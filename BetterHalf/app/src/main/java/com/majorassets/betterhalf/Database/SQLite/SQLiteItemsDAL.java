package com.majorassets.betterhalf.Database.SQLite;

import android.database.sqlite.SQLiteDatabase;

import com.majorassets.betterhalf.Model.BaseDataItem;

/**
 * Created by Marissa on 4/20/2016.
 */
public class SQLiteItemsDAL implements ISQLiteItemsDAL {

    private SQLiteDatabase db;

    public SQLiteItemsDAL(SQLiteDatabase db)
    {
        this.db = db;
    }

    // Add an item to database
    public void addItem(BaseDataItem item){

    }

    // Change an existing item
    public void updateItem(BaseDataItem item){

    }

    // Remove an existing item
    public BaseDataItem deleteItem(BaseDataItem item){

    }


}
