package com.majorassets.betterhalf.Database.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.majorassets.betterhalf.Database.SQLite.UserDBSchema.UserDBTable;
import com.majorassets.betterhalf.Database.SQLite.DataDBSchema.BooksTable;
import com.majorassets.betterhalf.Database.SQLite.DataDBSchema.MoviesTable;
import com.majorassets.betterhalf.Database.SQLite.DataDBSchema.GamesTable;
import com.majorassets.betterhalf.Database.SQLite.DataDBSchema.TheaterTable;
import com.majorassets.betterhalf.Database.SQLite.DataDBSchema.TVShowsTable;
import com.majorassets.betterhalf.Database.SQLite.DataDBSchema.MusicTable;

import com.majorassets.betterhalf.Database.SQLite.DataDBSchema.ClothingTable;
import com.majorassets.betterhalf.Database.SQLite.DataDBSchema.AccessoriesTable;
import com.majorassets.betterhalf.Database.SQLite.DataDBSchema.ShoesTable;
import com.majorassets.betterhalf.Database.SQLite.DataDBSchema.JewelryTable;

import com.majorassets.betterhalf.Database.SQLite.DataDBSchema.IndoorTable;
import com.majorassets.betterhalf.Database.SQLite.DataDBSchema.OutdoorTable;
import com.majorassets.betterhalf.Database.SQLite.DataDBSchema.SportsTable;

import com.majorassets.betterhalf.Database.SQLite.DataDBSchema.IllnessesTable;
import com.majorassets.betterhalf.Database.SQLite.DataDBSchema.PhobiasTable;
import com.majorassets.betterhalf.Database.SQLite.DataDBSchema.MedicationTable;
import com.majorassets.betterhalf.Database.SQLite.DataDBSchema.AllergiesTable;

import com.majorassets.betterhalf.Database.SQLite.DataDBSchema.RestaurantsTable;
import com.majorassets.betterhalf.Database.SQLite.DataDBSchema.DrinksTable;
import com.majorassets.betterhalf.Database.SQLite.DataDBSchema.SnacksTable;
import com.majorassets.betterhalf.Database.SQLite.DataDBSchema.EntreesTable;
import com.majorassets.betterhalf.Database.SQLite.DataDBSchema.SidesTable;

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
        /**  Strings for creating each table */
        // User Table
        String createUserTable = "CREATE TABLE " + UserDBTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                UserDBTable.Cols.UUID + ", " +
                UserDBTable.Cols.EMAIL + ", " +
                UserDBTable.Cols.PASSWORD + ", " +
                UserDBTable.Cols.LOGGED_ON_LAST + ")";

        // Entertainment Tables
        String createBooksTable = "CREATE TABLE " + BooksTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                DataDBSchema.BooksTable.Cols.LABEL + ", " +
                BooksTable.Cols.VALUE + ")";
        String createGamesTable = "CREATE TABLE " + GamesTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                GamesTable.Cols.LABEL + ", " +
                GamesTable.Cols.VALUE + ")";
        String createMoviesTable = "CREATE TABLE " + MoviesTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                MoviesTable.Cols.LABEL + ", " +
                MoviesTable.Cols.VALUE + ")";
        String createMusicTable = "CREATE TABLE " + MusicTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                MusicTable.Cols.LABEL + ", " +
                MusicTable.Cols.VALUE + ")";
        String createTheaterTable = "CREATE TABLE " + TheaterTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                TheaterTable.Cols.LABEL + ", " +
                TheaterTable.Cols.VALUE + ")";
        String createTVShowsTable = "CREATE TABLE " + TVShowsTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                TVShowsTable.Cols.LABEL + ", " +
                TVShowsTable.Cols.VALUE + ")";

        // Fashion Tables
        String createAccessoriesTable = "CREATE TABLE " + AccessoriesTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                AccessoriesTable.Cols.LABEL + ", " +
                AccessoriesTable.Cols.VALUE + ")";
        String createClothingTable = "CREATE TABLE " + ClothingTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                ClothingTable.Cols.LABEL + ", " +
                ClothingTable.Cols.VALUE + ")";
        String createJewelryTable = "CREATE TABLE " + JewelryTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                JewelryTable.Cols.LABEL + ", " +
                JewelryTable.Cols.VALUE + ")";
        String createShoesTable = "CREATE TABLE " + ShoesTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                ShoesTable.Cols.LABEL + ", " +
                ShoesTable.Cols.VALUE + ")";

        // Food Tables
        String createDrinksTable = "CREATE TABLE " + DrinksTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                DrinksTable.Cols.LABEL + ", " +
                DrinksTable.Cols.VALUE + ")";
        String createEntreesTable = "CREATE TABLE " + EntreesTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                EntreesTable.Cols.LABEL + ", " +
                EntreesTable.Cols.VALUE + ")";
        String createRestaurantsTable = "CREATE TABLE " + RestaurantsTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                RestaurantsTable.Cols.LABEL + ", " +
                RestaurantsTable.Cols.VALUE + ")";
        String createSidesTable = "CREATE TABLE " + SidesTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                SidesTable.Cols.LABEL + ", " +
                SidesTable.Cols.VALUE + ")";
        String createSnacksTable = "CREATE TABLE " + SnacksTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                SnacksTable.Cols.LABEL + ", " +
                SnacksTable.Cols.VALUE + ")";

        // Hobby Tables
        String createIndoorTable = "CREATE TABLE " + IndoorTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                IndoorTable.Cols.LABEL + ", " +
                IndoorTable.Cols.VALUE + ")";
        String createOutdoorTable = "CREATE TABLE " + OutdoorTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                OutdoorTable.Cols.LABEL + ", " +
                OutdoorTable.Cols.VALUE + ")";
        String createSportsTable = "CREATE TABLE " + SportsTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                SportsTable.Cols.LABEL + ", " +
                SportsTable.Cols.VALUE + ")";

        // Medical Tables
        String createAllergiesTable = "CREATE TABLE " + AllergiesTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                AllergiesTable.Cols.LABEL + ", " +
                AllergiesTable.Cols.VALUE + ")";
        String createIllnessesTable = "CREATE TABLE " + IllnessesTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                IllnessesTable.Cols.LABEL + ", " +
                IllnessesTable.Cols.VALUE + ")";
        String createMedicationTable = "CREATE TABLE " + MedicationTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                MedicationTable.Cols.LABEL + ", " +
                MedicationTable.Cols.VALUE + ")";
        String createPhobiasTable = "CREATE TABLE " + PhobiasTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                PhobiasTable.Cols.LABEL + ", " +
                PhobiasTable.Cols.VALUE + ")";

        /** Executing the table creation statements */
        // User Table
        db.execSQL(createUserTable);

        // Entertainment tables
        db.execSQL(createBooksTable);
        db.execSQL(createGamesTable);
        db.execSQL(createMoviesTable);
        db.execSQL(createMusicTable);
        db.execSQL(createTheaterTable);
        db.execSQL(createTVShowsTable);

        // Fashion Tables
        db.execSQL(createAccessoriesTable);
        db.execSQL(createClothingTable);
        db.execSQL(createJewelryTable);
        db.execSQL(createShoesTable);

        // Food Tables
        db.execSQL(createDrinksTable);
        db.execSQL(createEntreesTable);
        db.execSQL(createRestaurantsTable);
        db.execSQL(createSidesTable);
        db.execSQL(createSnacksTable);

        // Hobby Tables
        db.execSQL(createIndoorTable);
        db.execSQL(createOutdoorTable);
        db.execSQL(createSportsTable);

        // Medical Tables
        db.execSQL(createAllergiesTable);
        db.execSQL(createIllnessesTable);
        db.execSQL(createMedicationTable);
        db.execSQL(createPhobiasTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
