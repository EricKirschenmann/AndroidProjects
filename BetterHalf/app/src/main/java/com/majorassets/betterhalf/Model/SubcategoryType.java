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
    INVALID;

    public static SubcategoryType getTypeFromString(String subcategory)
{
    SubcategoryType sub = INVALID;
    switch(subcategory.toLowerCase())
    {
        case "movies":
            sub = MOVIE;
            break;
        case "music":
            sub = MUSIC;
            break;
        case "books":
            sub = BOOK;
            break;
        case "games":
            sub = GAME;
            break;
        case "theater":
            sub = THEATER;
            break;
        case "tvShows":
            sub = TV_SHOW;
            break;
        default:
            break; //invalid type
    }
    return sub;
}

    public static String getStringFromType(MainCategoryType main, SubcategoryType sub)
    {
        String strType = "invalid";
        switch (main)
        {
            case ENTERTAINMENT:
                GetEntertainmentString(sub);
                break;
            case FASHION:
                break;
            case FOOD:
                break;
            case HOBBY:
                break;
            case MEDICAL:
                break;
        }
        return strType;
    }

    private static String GetEntertainmentString(SubcategoryType sub)
    {
        String strType = "invalid";
        switch (sub)
        {
            case MOVIE:
                strType = "movies";
                break;
            case MUSIC:
                strType = "music";
                break;
            case BOOK:
                strType = "books";
                break;
            case GAME:
                strType = "games";
                break;
            case THEATER:
                strType = "theater";
                break;
            case TV_SHOW:
                strType = "tvShows";
                break;
            default:
                break;
        }
        return strType;
    }

}


