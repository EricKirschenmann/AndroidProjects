package com.majorassets.betterhalf.Model;

/**
 * Created by dgbla on 2/28/2016.
 */
public enum SubcategoryType
{
    //ENTERTAINMENT
    MOVIE,
    MUSIC,
    GAME,
    THEATER,
    BOOK,
    TV_SHOW,

    //FASHION
    CLOTHING,
    SHOE,
    ACCESSORY,
    JEWELRY,

    //FOOD
    RESTAURANT,
    DRINK,
    ENTREE,
    SIDE,
    SNACK,

    //HOBBY
    INDOOR,
    OUTDOOR,
    SPORT,

    //MEDICAL
    ALLERGY,
    ILLNESS,
    PHOBIA,
    MEDICATION,

    INVALID;

    public static SubcategoryType getTypeFromString(String subcategory)
    {
        switch(subcategory.toLowerCase())
        {
            //ENTERTAINMENT
            case "movies":
                return MOVIE;
            case "music":
                return MUSIC;
            case "books":
                return BOOK;
            case "games":
                return GAME;
            case "theater":
                return THEATER;
            case "tvshows":
                return TV_SHOW;
            //FASHION
            case "accessories":
                return ACCESSORY;
            case "clothing":
                return CLOTHING;
            case "jewelry":
                return JEWELRY;
            case "shoes":
                return SHOE;
            //FOOD
            case "restaurants":
                return RESTAURANT;
            case "drinks":
                return DRINK;
            case "entrees":
                return ENTREE;
            case "sides":
                return SIDE;
            case "snacks":
                return SNACK;
            //HOBBY
            case "indoorhobbies":
                return INDOOR;
            case "outdoorhobbies":
                return OUTDOOR;
            case "sportsteams":
                return SPORT;
            //MEDICAL
            case "allergies":
                return ALLERGY;
            case "illnesses":
                return ILLNESS;
            case "phobias":
                return PHOBIA;
            case "medications":
                return MEDICATION;
            default:
                return INVALID;
        }
    }

    public static String getStringFromType(MainCategoryType main, SubcategoryType sub)
    {
        switch (main)
        {
            case ENTERTAINMENT:
                return getEntertainmentString(sub);
            case FASHION:
                return getFashionString(sub);
            case FOOD:
                return getFoodString(sub);
            case HOBBY:
                return getHobbyString(sub);
            case MEDICAL:
                return getMedicalString(sub);
            default:
                return "invalid";
        }
    }

    private static String getEntertainmentString(SubcategoryType sub)
    {
        switch (sub)
        {
            case MOVIE:
                return "Movies";
            case MUSIC:
                return "Music";
            case BOOK:
                return "Books";
            case GAME:
                return "Games";
            case THEATER:
                return "Theater";
            case TV_SHOW:
                return "TV Shows";
            default:
                return "invalid";
        }
    }

    private static String getFashionString(SubcategoryType sub)
    {
        switch (sub)
        {
            case CLOTHING:
                return "Clothing";
            case SHOE:
                return "Shoes";
            case ACCESSORY:
                return "Accessories";
            case JEWELRY:
                return "Jewelry";
            default:
                return "invalid";
        }
    }

    private static String getFoodString(SubcategoryType sub)
    {
        switch(sub)
        {
            case RESTAURANT:
                return "Restaurants";
            case DRINK:
                return "Drinks";
            case SIDE:
                return "Sides";
            case ENTREE:
                return "Entrees";
            case SNACK:
                return "Snacks";
            default:
                return "invalid";
        }
    }

    private static String getHobbyString(SubcategoryType sub)
    {
        switch (sub)
        {
            case INDOOR:
                return "Indoor Hobbies";
            case OUTDOOR:
                return "Outdoor Hobbies";
            case SPORT:
                return "Sports Teams";
            default:
                return "invalid";
        }
    }

    private static String getMedicalString(SubcategoryType sub)
    {
        switch (sub)
        {
            case ALLERGY:
                return "Allergies";
            case ILLNESS:
                return "Illnesses";
            case PHOBIA:
                return "Phobias";
            case MEDICATION:
                return "Medications";
            default:
                return "invalid";
        }
    }

}


