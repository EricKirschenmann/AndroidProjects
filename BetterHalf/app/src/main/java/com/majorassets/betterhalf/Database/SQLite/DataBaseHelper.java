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

        //TODO: finish adding UUID column
        // Entertainment Tables
        String createBooksTable = "CREATE TABLE " + BooksTable.NAME + "(" +
                BooksTable.Cols.UUID + " integer primary key autoincrement, " +
                BooksTable.Cols.USER_ID + ", " +
                BooksTable.Cols.LABEL + ", " +
                BooksTable.Cols.VALUE + ", " +
                BooksTable.Cols.FAVORITE + ")";
        String createGamesTable = "CREATE TABLE " + GamesTable.NAME + "(" +
                GamesTable.Cols.UUID + " integer primary key autoincrement, " +
                GamesTable.Cols.USER_ID + ", " +
                GamesTable.Cols.LABEL + ", " +
                GamesTable.Cols.VALUE + ", " +
                GamesTable.Cols.FAVORITE + ")";
        String createMoviesTable = "CREATE TABLE " + MoviesTable.NAME + "(" +
                MoviesTable.Cols.UUID + " integer primary key autoincrement, " +
                MoviesTable.Cols.USER_ID + ", " +
                MoviesTable.Cols.LABEL + ", " +
                MoviesTable.Cols.VALUE + ", " +
                MoviesTable.Cols.FAVORITE + ")";
        String createMusicTable = "CREATE TABLE " + MusicTable.NAME + "(" +
                MusicTable.Cols.UUID + " integer primary key autoincrement, " +
                MusicTable.Cols.USER_ID + ", " +
                MusicTable.Cols.LABEL + ", " +
                MusicTable.Cols.VALUE + ", " +
                MusicTable.Cols.FAVORITE + ")";
        String createTheaterTable = "CREATE TABLE " + TheaterTable.NAME + "(" +
                TheaterTable.Cols.UUID + " integer primary key autoincrement, " +
                TheaterTable.Cols.USER_ID + ", " +
                TheaterTable.Cols.LABEL + ", " +
                TheaterTable.Cols.VALUE + ", " +
                TheaterTable.Cols.FAVORITE + ")";
        String createTVShowsTable = "CREATE TABLE " + TVShowsTable.NAME + "(" +
                TVShowsTable.Cols.UUID + " integer primary key autoincrement, " +
                TVShowsTable.Cols.USER_ID + ", " +
                TVShowsTable.Cols.LABEL + ", " +
                TVShowsTable.Cols.VALUE + ", " +
                TVShowsTable.Cols.FAVORITE + ")";

        // Fashion Tables
        String createAccessoriesTable = "CREATE TABLE " + AccessoriesTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                AccessoriesTable.Cols.UUID  + ", " +
                AccessoriesTable.Cols.LABEL + ", " +
                AccessoriesTable.Cols.VALUE + ", " +
                AccessoriesTable.Cols.FAVORITE + ")";
        String createClothingTable = "CREATE TABLE " + ClothingTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                ClothingTable.Cols.UUID  + ", " +
                ClothingTable.Cols.LABEL + ", " +
                ClothingTable.Cols.VALUE + ", " +
                ClothingTable.Cols.FAVORITE + ")";
        String createJewelryTable = "CREATE TABLE " + JewelryTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                JewelryTable.Cols.UUID  + ", " +
                JewelryTable.Cols.LABEL + ", " +
                JewelryTable.Cols.VALUE + ", " +
                JewelryTable.Cols.FAVORITE + ")";
        String createShoesTable = "CREATE TABLE " + ShoesTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                ShoesTable.Cols.UUID  + ", " +
                ShoesTable.Cols.LABEL + ", " +
                ShoesTable.Cols.VALUE + ", " +
                ShoesTable.Cols.FAVORITE + ")";

        // Food Tables
        String createDrinksTable = "CREATE TABLE " + DrinksTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                DrinksTable.Cols.UUID  + ", " +
                DrinksTable.Cols.LABEL + ", " +
                DrinksTable.Cols.VALUE + ", " +
                DrinksTable.Cols.FAVORITE + ")";
        String createEntreesTable = "CREATE TABLE " + EntreesTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                EntreesTable.Cols.UUID  + ", " +
                EntreesTable.Cols.LABEL + ", " +
                EntreesTable.Cols.VALUE + ", " +
                EntreesTable.Cols.FAVORITE + ")";
        String createRestaurantsTable = "CREATE TABLE " + RestaurantsTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                RestaurantsTable.Cols.UUID  + ", " +
                RestaurantsTable.Cols.LABEL + ", " +
                RestaurantsTable.Cols.VALUE + ", " +
                RestaurantsTable.Cols.FAVORITE + ")";
        String createSidesTable = "CREATE TABLE " + SidesTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                SidesTable.Cols.UUID  + ", " +
                SidesTable.Cols.LABEL + ", " +
                SidesTable.Cols.VALUE + ", " +
                SidesTable.Cols.FAVORITE + ")";
        String createSnacksTable = "CREATE TABLE " + SnacksTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                SnacksTable.Cols.UUID  + ", " +
                SnacksTable.Cols.LABEL + ", " +
                SnacksTable.Cols.VALUE + ", " +
                SnacksTable.Cols.FAVORITE + ")";

        // Hobby Tables
        String createIndoorTable = "CREATE TABLE " + IndoorTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                IndoorTable.Cols.UUID  + ", " +
                IndoorTable.Cols.LABEL + ", " +
                IndoorTable.Cols.VALUE + ", " +
                IndoorTable.Cols.FAVORITE + ")";
        String createOutdoorTable = "CREATE TABLE " + OutdoorTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                OutdoorTable.Cols.UUID  + ", " +
                OutdoorTable.Cols.LABEL + ", " +
                OutdoorTable.Cols.VALUE + ", " +
                OutdoorTable.Cols.FAVORITE + ")";
        String createSportsTable = "CREATE TABLE " + SportsTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                SportsTable.Cols.UUID  + ", " +
                SportsTable.Cols.LABEL + ", " +
                SportsTable.Cols.VALUE + ", " +
                SportsTable.Cols.FAVORITE + ")";

        // Medical Tables
        String createAllergiesTable = "CREATE TABLE " + AllergiesTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                AllergiesTable.Cols.UUID  + ", " +
                AllergiesTable.Cols.LABEL + ", " +
                AllergiesTable.Cols.VALUE + ", " +
                AllergiesTable.Cols.FAVORITE + ")";
        String createIllnessesTable = "CREATE TABLE " + IllnessesTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                IllnessesTable.Cols.UUID  + ", " +
                IllnessesTable.Cols.LABEL + ", " +
                IllnessesTable.Cols.VALUE + ", " +
                IllnessesTable.Cols.FAVORITE + ")";
        String createMedicationTable = "CREATE TABLE " + MedicationTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                MedicationTable.Cols.UUID  + ", " +
                MedicationTable.Cols.LABEL + ", " +
                MedicationTable.Cols.VALUE + ", " +
                MedicationTable.Cols.FAVORITE + ")";
        String createPhobiasTable = "CREATE TABLE " + PhobiasTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                PhobiasTable.Cols.UUID  + ", " +
                PhobiasTable.Cols.LABEL + ", " +
                PhobiasTable.Cols.VALUE + ", " +
                PhobiasTable.Cols.FAVORITE + ")";

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
