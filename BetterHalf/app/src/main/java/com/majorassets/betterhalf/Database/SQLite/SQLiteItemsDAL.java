package com.majorassets.betterhalf.Database.SQLite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.majorassets.betterhalf.Database.SQLite.CursorWrappers.BaseItemCursorWrapper;
import com.majorassets.betterhalf.Database.SQLite.CursorWrappers.EntertainmentCursorWrapper;
import com.majorassets.betterhalf.Database.SQLite.CursorWrappers.FashionCursorWrapper;
import com.majorassets.betterhalf.Database.SQLite.CursorWrappers.FoodCursorWrapper;
import com.majorassets.betterhalf.Database.SQLite.CursorWrappers.HobbiesCursorWrapper;
import com.majorassets.betterhalf.Database.SQLite.CursorWrappers.MedicalCursorWrapper;
import com.majorassets.betterhalf.GlobalResources;
import com.majorassets.betterhalf.Model.BaseDataItem;
import com.majorassets.betterhalf.Model.BaseLikeableItem;
import com.majorassets.betterhalf.Model.Subcategory;
import com.majorassets.betterhalf.Model.SubcategoryType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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


    public int getItemID(BaseLikeableItem item, String tableName)
    {
        int ID = 0;
        BaseItemCursorWrapper cursor = SQLiteProvider.queryItemID(tableName,
                            DataDBSchema.BaseTable.Cols.VALUE + "= ?", new String[] {item.getValue()});
        try
        {
            if(cursor.getCount() == 0)
                return -1;

            cursor.moveToFirst();
            ID = Integer.parseInt(cursor.getItem().getID());
        }
        catch (SQLException ex)
        {
            Log.d("ERROR", ex.getMessage());
        }

        return ID;
    }

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

    @Override
    public void addItem(BaseLikeableItem item, String tableName)
    {
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
    public void updateItem(BaseLikeableItem item, String tableName, String colUUID){
        String itemID = item.getID();
        ContentValues values = SQLiteProvider.getDataContentValues(item);

        db.update(tableName, values, colUUID + " = ?", new String[] {itemID});
    }

    // Remove an existing item
    public BaseDataItem deleteItem(BaseDataItem item, String tableName, String colUUID){
        String itemID = item.getID();

        db.delete(tableName, colUUID + "= ?", new String[]{itemID});

        return item;
    }

    @Override
    public List<BaseLikeableItem> getEntertainmentItems(String tableName, UUID userId)
    {
        EntertainmentCursorWrapper cursor = SQLiteProvider.queryEntertainmentItem(tableName,
                DataDBSchema.BaseTable.Cols.USER_ID + "= ?", new String[]{userId.toString()});

        List<BaseLikeableItem> items = new ArrayList<>();
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

    @Override
    public List<BaseLikeableItem> getFashionItems(String tableName, UUID userId)
    {
        FashionCursorWrapper cursor = SQLiteProvider.queryFashionItem(tableName,
                DataDBSchema.BaseTable.Cols.USER_ID + "= ?", new String[]{userId.toString()});

        List<BaseLikeableItem> items = new ArrayList<>();
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

    @Override
    public List<BaseLikeableItem> getFoodItems(String tableName, UUID userId)
    {
        FoodCursorWrapper cursor = SQLiteProvider.queryFoodItem(tableName,
                DataDBSchema.BaseTable.Cols.USER_ID + "= ?", new String[]{userId.toString()});

        List<BaseLikeableItem> items = new ArrayList<>();
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

    @Override
    public List<BaseLikeableItem> getHobbyItems(String tableName, UUID userId)
    {
        HobbiesCursorWrapper cursor = SQLiteProvider.queryHobbyItem(tableName,
                DataDBSchema.BaseTable.Cols.USER_ID + "= ?", new String[]{userId.toString()});

        List<BaseLikeableItem> items = new ArrayList<>();
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

    @Override
    public List<BaseLikeableItem> getMedicalItems(String tableName, UUID userId)
    {
        MedicalCursorWrapper cursor = SQLiteProvider.queryMedicalItem(tableName,
                DataDBSchema.BaseTable.Cols.USER_ID + "= ?", new String[]{userId.toString()});

        List<BaseLikeableItem> items = new ArrayList<>();
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

    @Override
    public void getAllUserEntertainmentItems(UUID userId, Map<SubcategoryType, List<BaseLikeableItem>> items)
    {
        for (String table: GlobalResources.EntertainmentTableNames)
        {
            EntertainmentCursorWrapper cursor = SQLiteProvider.queryEntertainmentItem(table,
                    DataDBSchema.BaseTable.Cols.USER_ID + "= ?", new String[]{userId.toString()});

            try{
                if(cursor.getCount() == 0)
                    continue;

                cursor.moveToFirst();

                //iterate the cursor over each record in DB
                while(!cursor.isAfterLast())
                {
                    addEntertainmentItem(table, cursor, items);
                    cursor.moveToNext();
                }
            }
            finally
            {
                cursor.close();
            }
        }
    }

    private void addEntertainmentItem(String tableName, EntertainmentCursorWrapper cursor, Map<SubcategoryType, List<BaseLikeableItem>> items)
    {
        List<BaseLikeableItem> list;
        SubcategoryType subType = SubcategoryType.getTypeFromTitle(tableName);

        BaseLikeableItem itemToAdd = cursor.getItem();
        //if there are no entries for a movie then the list will be null
        if(items.get(subType) == null)
        {
            list = new ArrayList<>(); // use an empty list
            list.add(itemToAdd);

            items.put(subType, list); //create new entry for movies
        }
        else //add to an already define list
        {
            list = items.get(subType);
            list.add(itemToAdd);
        }
    }

    @Override
    public void getAllUserFashionItems(UUID userId, Map<SubcategoryType, List<BaseLikeableItem>> items)
    {
        for (String table: GlobalResources.FashionTableNames)
        {
            FashionCursorWrapper cursor = SQLiteProvider.queryFashionItem(table,
                    DataDBSchema.BaseTable.Cols.USER_ID + "= ?", new String[]{userId.toString()});

            try{
                if(cursor.getCount() == 0)
                    continue;

                cursor.moveToFirst();

                //iterate the cursor over each record in DB
                while(!cursor.isAfterLast())
                {
                    addFashionItem(table, cursor, items);
                    cursor.moveToNext();
                }
            }
            finally
            {
                cursor.close();
            }
        }
    }

    private void addFashionItem(String tableName, FashionCursorWrapper cursor, Map<SubcategoryType, List<BaseLikeableItem>> items)
    {
        List<BaseLikeableItem> list;
        SubcategoryType subType = SubcategoryType.getTypeFromTitle(tableName);

        if(items.get(subType) == null)
        {
            list = new ArrayList<>(); // use an empty list
            list.add(cursor.getItem());
            items.put(subType, list); //create new entry
        }
        else //add to an already define list
        {
            list = items.get(subType);
            list.add(cursor.getItem());
        }
    }

    @Override
    public void getAllUserFoodItems(UUID userId, Map<SubcategoryType, List<BaseLikeableItem>> items)
    {
        for (String table: GlobalResources.FoodTableNames)
        {
            FoodCursorWrapper cursor = SQLiteProvider.queryFoodItem(table,
                    DataDBSchema.BaseTable.Cols.USER_ID + "= ?", new String[]{userId.toString()});

            try
            {
                if (cursor.getCount() == 0)
                    continue;

                cursor.moveToFirst();

                //iterate the cursor over each record in DB
                while (!cursor.isAfterLast())
                {
                    addFoodItem(table, cursor, items);
                    cursor.moveToNext();
                }
            } finally
            {
                cursor.close();
            }
        }
    }

    private void addFoodItem(String tableName, FoodCursorWrapper cursor, Map<SubcategoryType, List<BaseLikeableItem>> items)
    {
        List<BaseLikeableItem> list;
        SubcategoryType subType = SubcategoryType.getTypeFromTitle(tableName);

        BaseLikeableItem itemToAdd = cursor.getItem();
        //if there are no entries for a movie then the list will be null
        if(items.get(subType) == null)
        {
            list = new ArrayList<>(); // use an empty list
            list.add(itemToAdd);

            items.put(subType, list); //create new entry for movies
        }
        else //add to an already define list
        {
            list = items.get(subType);
            list.add(itemToAdd);
        }
    }

    @Override
    public void getAllUserHobbyItems(UUID userId, Map<SubcategoryType, List<BaseLikeableItem>> items)
    {
        for (String table: GlobalResources.HobbyTableNames)
        {
            HobbiesCursorWrapper cursor = SQLiteProvider.queryHobbyItem(table,
                    DataDBSchema.BaseTable.Cols.USER_ID + "= ?", new String[]{userId.toString()});

            try
            {
                if (cursor.getCount() == 0)
                    continue;

                cursor.moveToFirst();

                //iterate the cursor over each record in DB
                while (!cursor.isAfterLast())
                {
                    addHobbyItem(table, cursor, items);
                    cursor.moveToNext();
                }
            } finally
            {
                cursor.close();
            }
        }
    }

    private void addHobbyItem(String tableName, HobbiesCursorWrapper cursor, Map<SubcategoryType, List<BaseLikeableItem>> items)
    {
        List<BaseLikeableItem> list;
        SubcategoryType subType = SubcategoryType.getTypeFromTitle(tableName);

        BaseLikeableItem itemToAdd = cursor.getItem();
        //if there are no entries for a movie then the list will be null
        if(items.get(subType) == null)
        {
            list = new ArrayList<>(); // use an empty list
            list.add(itemToAdd);

            items.put(subType, list); //create new entry for movies
        }
        else //add to an already define list
        {
            list = items.get(subType);
            list.add(itemToAdd);
        }
    }

    @Override
    public void getAllUserMedicalItems(UUID userId, Map<SubcategoryType, List<BaseLikeableItem>> items)
    {
        for (String table: GlobalResources.MedicalTableNames)
        {
            MedicalCursorWrapper cursor = SQLiteProvider.queryMedicalItem(table,
                    DataDBSchema.BaseTable.Cols.USER_ID + "= ?", new String[]{userId.toString()});

            try
            {
                if (cursor.getCount() == 0)
                    continue;

                cursor.moveToFirst();

                //iterate the cursor over each record in DB
                while (!cursor.isAfterLast())
                {
                    addMedicalItem(table, cursor, items);
                    cursor.moveToNext();
                }
            } finally
            {
                cursor.close();
            }
        }
    }

    private void addMedicalItem(String tableName, MedicalCursorWrapper cursor, Map<SubcategoryType, List<BaseLikeableItem>> items)
    {
        List<BaseLikeableItem> list;
        SubcategoryType subType = SubcategoryType.getTypeFromTitle(tableName);

        BaseLikeableItem itemToAdd = cursor.getItem();
        //if there are no entries for a movie then the list will be null
        if(items.get(subType) == null)
        {
            list = new ArrayList<>(); // use an empty list
            list.add(itemToAdd);

            items.put(subType, list); //create new entry for movies
        }
        else //add to an already define list
        {
            list = items.get(subType);
            list.add(itemToAdd);
        }
    }

}
