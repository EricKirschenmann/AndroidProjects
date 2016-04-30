package com.majorassets.betterhalf.Database.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.majorassets.betterhalf.Database.SQLite.CursorWrappers.BaseItemCursorWrapper;
import com.majorassets.betterhalf.Database.SQLite.CursorWrappers.EntertainmentCursorWrapper;
import com.majorassets.betterhalf.Database.SQLite.CursorWrappers.FashionCursorWrapper;
import com.majorassets.betterhalf.Database.SQLite.CursorWrappers.FoodCursorWrapper;
import com.majorassets.betterhalf.Database.SQLite.CursorWrappers.HobbiesCursorWrapper;
import com.majorassets.betterhalf.Database.SQLite.CursorWrappers.MedicalCursorWrapper;
import com.majorassets.betterhalf.Database.SQLite.CursorWrappers.UserCursorWrapper;
import com.majorassets.betterhalf.Database.SQLite.UserDBSchema.UserDBTable;
import com.majorassets.betterhalf.Database.SQLite.DataDBSchema.BaseTable;
import com.majorassets.betterhalf.Model.BaseDataItem;
import com.majorassets.betterhalf.Model.BaseLikeableItem;
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
    public static ContentValues getDataContentValues(BaseLikeableItem item){
        ContentValues values = new ContentValues();
        values.put(BaseTable.Cols.UUID, item.getID());
        values.put(BaseTable.Cols.USER_ID, item.getUserID().toString());
        values.put(BaseTable.Cols.LABEL, item.getLabel());
        values.put(BaseTable.Cols.VALUE, item.getValue());
        values.put(BaseTable.Cols.FAVORITE, item.isFavorite());
        return values;
    }

    public static ContentValues getDataContentValues(BaseDataItem item){
        ContentValues values = new ContentValues();
        values.put(BaseTable.Cols.UUID, item.getID());
        values.put(BaseTable.Cols.USER_ID, item.getUserID().toString());
        values.put(BaseTable.Cols.LABEL, item.getLabel());
        values.put(BaseTable.Cols.VALUE, item.getValue());
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

    public static EntertainmentCursorWrapper queryEntertainmentItem(String tableName, String whereClause, String[] whereArgs)
    {
        Cursor cursor = database.query(
                tableName,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new EntertainmentCursorWrapper(cursor);
    }

    public static FashionCursorWrapper queryFashionItem(String tableName, String whereClause, String[] whereArgs)
    {
        Cursor cursor = database.query(
                tableName,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new FashionCursorWrapper(cursor);
    }

    public static FoodCursorWrapper queryFoodItem(String tableName, String whereClause, String[] whereArgs)
    {
        Cursor cursor = database.query(
                tableName,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new FoodCursorWrapper(cursor);
    }

    public static HobbiesCursorWrapper queryHobbyItem(String tableName, String whereClause, String[] whereArgs)
    {
        Cursor cursor = database.query(
                tableName,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new HobbiesCursorWrapper(cursor);
    }

    public static MedicalCursorWrapper queryMedicalItem(String tableName, String whereClause, String[] whereArgs)
    {
        Cursor cursor = database.query(
                tableName,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new MedicalCursorWrapper(cursor);
    }

    public SQLiteDatabase getDatabase()
    {
        return database;
    }
}
