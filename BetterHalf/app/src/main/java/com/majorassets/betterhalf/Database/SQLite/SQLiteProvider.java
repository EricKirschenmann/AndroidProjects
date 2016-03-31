package com.majorassets.betterhalf.Database.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by dgbla on 3/30/2016.
 */
public class SQLiteProvider
{
    public SQLiteDatabase SQLiteDatabase;

    public SQLiteProvider(Context context)
    {
        SQLiteDatabase = new DataBaseHelper(context).getWritableDatabase();
    }
}
