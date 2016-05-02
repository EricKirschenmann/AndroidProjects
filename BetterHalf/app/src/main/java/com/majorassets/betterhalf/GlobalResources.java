package com.majorassets.betterhalf;

import com.majorassets.betterhalf.Database.SQLite.DataDBSchema;
import com.majorassets.betterhalf.Model.MainCategoryType;
import com.majorassets.betterhalf.Model.Subcategory;
import com.majorassets.betterhalf.Model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dgbla on 2/28/2016.
 */
public class GlobalResources
{
    public static MainCategoryType mainTypePressed = MainCategoryType.INVALID;
    public static Map<MainCategoryType, List<Subcategory>> Subcategories = new HashMap<>();
    public static List<User> Users = new ArrayList<>();
    public static User AppUser;

    public static final String[] EntertainmentTableNames = new String[]
            {DataDBSchema.BooksTable.NAME, DataDBSchema.GamesTable.NAME,
                    DataDBSchema.MoviesTable.NAME, DataDBSchema.MusicTable.NAME,
                    DataDBSchema.TheaterTable.NAME, DataDBSchema.TVShowsTable.NAME};

    public static final String[] FashionTableNames = new String[]
            {DataDBSchema.ClothingTable.NAME, DataDBSchema.JewelryTable.NAME,
                    DataDBSchema.ShoesTable.NAME, DataDBSchema.AccessoriesTable.NAME};

    public static final String[] FoodTableNames = new String[]
            {DataDBSchema.RestaurantsTable.NAME, DataDBSchema.DrinksTable.NAME,
                    DataDBSchema.EntreesTable.NAME, DataDBSchema.SidesTable.NAME,
                    DataDBSchema.SnacksTable.NAME};

    public static final String[] HobbyTableNames = new String[]
            {DataDBSchema.IndoorTable.NAME, DataDBSchema.OutdoorTable.NAME, DataDBSchema.SportsTable.NAME};

    public static final String[] MedicalTableNames = new String[]
            {DataDBSchema.PhobiasTable.NAME, DataDBSchema.AllergiesTable.NAME,
                    DataDBSchema.MedicationTable.NAME, DataDBSchema.IllnessesTable.NAME};

    public static final String[] MainCategories = new String[] {"Entertainment", "Fashion", "Food", "Hobby", "Medical"};

    public static void addToGlobalSubcategories(MainCategoryType main, Subcategory sub)
    {
        List<Subcategory> lst;

        if(Subcategories.get(main) == null)
        {
            lst = new ArrayList<>();
            lst.add(sub);
            Subcategories.put(main, lst);
        }
        else
        {
            lst = Subcategories.get(main);
            lst.add(sub);
        }
    }
}
