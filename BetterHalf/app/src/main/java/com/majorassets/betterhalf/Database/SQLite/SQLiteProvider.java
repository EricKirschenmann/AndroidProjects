package com.majorassets.betterhalf.Database.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.majorassets.betterhalf.Database.SQLite.UserDBSchema.UserDBTable;
import com.majorassets.betterhalf.Model.User;

/**
 * Created by dgbla on 3/30/2016.
 */
public class SQLiteProvider
{
    public static SQLiteDatabase SQLiteDatabase;

    public SQLiteProvider(Context context)
    {
        SQLiteDatabase = new DataBaseHelper(context).getWritableDatabase();
    }

    public static ContentValues getUserContentValues(User user)
    {
        ContentValues values = new ContentValues();
        values.put(UserDBTable.Cols.UUID, user.getID().toString());
        values.put(UserDBTable.Cols.EMAIL, user.getEmail());
        values.put(UserDBTable.Cols.PASSWORD, user.getPassword());
        return values;
    }

    public static UserCursorWrapper queryUser(String whereClause, String[] whereArgs)
    {
        Cursor cursor = SQLiteDatabase.query(
                UserDBTable.NAME,
                null, //select * columns
                whereClause,
                whereArgs,
                null, //group by
                null, //having
                null); //order by

        return new UserCursorWrapper(cursor);
    }
}
