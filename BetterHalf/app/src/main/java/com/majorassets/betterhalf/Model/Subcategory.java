package com.majorassets.betterhalf.Model;

/**
 * Created by dgbla on 2/28/2016.
 */
public enum Subcategory
{
    MOVIE,
    MUSIC,
    GAME,
    THEATER,
    BOOK,
    TV_SHOW,
    INVALID;

    public static Subcategory getTypeFromString(String subcategory)
    {
        Subcategory sub = INVALID;
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
}


