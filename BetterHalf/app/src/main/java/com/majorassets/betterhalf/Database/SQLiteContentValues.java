package com.majorassets.betterhalf.Database;

import android.content.ContentValues;

import com.majorassets.betterhalf.Database.SQLite.UserDBSchema;
import com.majorassets.betterhalf.Database.SQLite.UserDBSchema.UserDBTable;
import com.majorassets.betterhalf.Model.User;

/**
 * Created by dgbla on 3/30/2016.
 */
public class SQLiteContentValues
{
    public static ContentValues getUserContentValues(User user)
    {
        ContentValues values = new ContentValues();
        values.put(UserDBTable.Cols.UUID, user.getID().toString());
        values.put(UserDBTable.Cols.EMAIL, user.getEmail());
        values.put(UserDBTable.Cols.PASSWORD, user.getPassword());
        return values;
    }
}
