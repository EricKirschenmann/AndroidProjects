package com.majorassets.betterhalf.Database.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.majorassets.betterhalf.Database.SQLite.UserDBSchema.UserDBTable;

/**
 * Created by dgbla on 3/30/2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "betterhalf.db";
    private static final int VERSION = 1;

    public DataBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + UserDBTable.NAME + "(" +
                        "_id integer primary key autoincrement, " +
                        UserDBTable.Cols.UUID + ", " +
                        UserDBTable.Cols.EMAIL + ", " +
                        UserDBTable.Cols.PASSWORD + ", " +
                        UserDBTable.Cols.LOGGED_ON_LAST + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
