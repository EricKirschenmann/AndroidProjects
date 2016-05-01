package com.majorassets.betterhalf.Database.SQLite;

/**
 * Created by Marissa on 4/11/2016.
 *
 * Contains the schemas for each of the data item tables.
 */
public class DataDBSchema {

    public static class BaseTable
    {
        public static final class Cols
        {
            public static final String UUID = "uuid"; //unique item id
            public static final String USER_ID = "userid"; //user foreign key
            public static final String LABEL = "label";
            public static final String VALUE = "value";
            public static final String FAVORITE = "favorite";
        }
    }
    /*  Entertainment Tables */
    public static final class BooksTable extends BaseTable
    {
        public static final String NAME = "Books";
    }

    public static final class GamesTable extends BaseTable
    {
        public static final String NAME = "Games";
    }

    public static final class MoviesTable extends BaseTable
    {
        public static final String NAME = "Movies";
    }

    public static final class MusicTable extends BaseTable
    {
        public static final String NAME = "Music";
    }

    public static final class TheaterTable extends BaseTable
    {
        public static final String NAME = "Theater";
    }

    public static final class TVShowsTable extends BaseTable
    {
        public static final String NAME = "TVShows";
    }


    /*  Fashion */
    public static final class AccessoriesTable extends BaseTable
    {
        public static final String NAME = "Accessories";
    }

    public static final class ClothingTable extends BaseTable
    {
        public static final String NAME = "Clothing";
    }

    public static final class JewelryTable extends BaseTable
    {
        public static final String NAME = "Jewelry";
    }

    public static final class ShoesTable extends BaseTable
    {
        public static final String NAME = "Shoes";
    }


    /*  Food */
    public static final class DrinksTable extends BaseTable
    {
        public static final String NAME = "Drinks";
    }

    public static final class EntreesTable extends BaseTable
    {
        public static final String NAME = "Entrees";
    }

    public static final class RestaurantsTable extends BaseTable
    {
        public static final String NAME = "Restaurants";
    }

    public static final class SidesTable extends BaseTable
    {
        public static final String NAME = "Sides";
    }

    public static final class SnacksTable extends BaseTable
    {
        public static final String NAME = "Snacks";
    }


    /*  Hobbies */
    public static final class IndoorTable extends BaseTable
    {
        public static final String NAME = "IndoorHobbies";
    }

    public static final class OutdoorTable extends BaseTable
    {
        public static final String NAME = "OutdoorHobbies";
    }

    public static final class SportsTable extends BaseTable
    {
        public static final String NAME = "SportsTeams";
    }


    /*  Medical */
    public static final class AllergiesTable extends BaseTable
    {
        public static final String NAME = "Allergies";
    }

    public static final class IllnessesTable extends BaseTable
    {
        public static final String NAME = "Illnesses";
    }

    public static final class MedicationTable extends BaseTable
    {
        public static final String NAME = "Medications";
    }

    public static final class PhobiasTable extends BaseTable
    {
        public static final String NAME = "Phobias";
    }

}
