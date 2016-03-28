package com.majorassets.betterhalf.Model;

/**
 * Created by dgbla on 2/28/2016.
 */
public enum SubcategoryType
{
    MOVIE,
    MUSIC,
    GAME,
    THEATER,
    BOOK,
    TV_SHOW,
    CLOTHING,
    SHOE,
    ACCESSORY,
    JEWELRY,
    INVALID;

    public static SubcategoryType getTypeFromString(String subcategory)
{
    switch(subcategory.toLowerCase())
    {
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
        default:
            return INVALID;
    }
}

    public static String getStringFromType(MainCategoryType main, SubcategoryType sub)
    {
        switch (main)
        {
            case ENTERTAINMENT:
                return GetEntertainmentString(sub);
            case FASHION:
                return GetFashionString(sub);
            case FOOD:
                break;
            case HOBBY:
                break;
            case MEDICAL:
                break;
            default:
                return "invalid";
        }
        return "invalid";
    }

    private static String GetEntertainmentString(SubcategoryType sub)
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

    private static String GetFashionString(SubcategoryType sub)
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
}


