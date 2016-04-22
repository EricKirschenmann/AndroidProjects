package com.majorassets.betterhalf.Database.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.majorassets.betterhalf.Database.SQLite.UserDBSchema.UserDBTable;
import com.majorassets.betterhalf.Model.BaseDataItem;
import com.majorassets.betterhalf.Model.User;

/**
 * Created by dgbla on 3/30/2016.
 */
public class SQLiteProvider
{
    private static SQLiteProvider sSQLiteProvider;
    private static SQLiteDatabase database;

    private SQLiteProvider(Context context)
    {
        database = new DataBaseHelper(context).getWritableDatabase();
    }

    //singleton implementation
    public static SQLiteProvider getSQLiteProvider(Context context)
    {
        if(sSQLiteProvider == null)
            sSQLiteProvider = new SQLiteProvider(context);

        return sSQLiteProvider;
    }

    public static ContentValues getUserContentValues(User user)
    {
        ContentValues values = new ContentValues();
        values.put(UserDBTable.Cols.UUID, user.getID().toString());
        values.put(UserDBTable.Cols.EMAIL, user.getEmail());
        values.put(UserDBTable.Cols.PASSWORD, user.getPassword());
        values.put(UserDBTable.Cols.LOGGED_ON_LAST, user.isLoggedOnLast());
        return values;
    }

    /*
     colLabel = the label from the schema of the item
         ex: BooksTable.Cols.LABEL
     colValue = the value from the schema
         ex: BooksTable.Cols.VALUE
    */
     public static ContentValues getDataContentValues(BaseDataItem item, String colLabel, String colValue)
     {
         ContentValues values = new ContentValues();
         values.put(colLabel, item.getLabel());
         values.put(colValue, item.getValue());
        return values;
     }


    public static UserCursorWrapper queryUser(String whereClause, String[] whereArgs)
    {
                Cursor cursor = database.query(
                UserDBTable.NAME,
                null, //select * columns
                whereClause,
                whereArgs,
                null, //group by
                null, //having
                null); //order by

        return new UserCursorWrapper(cursor);
    }

    public SQLiteDatabase getDatabase()
    {
        return database;
    }
}
