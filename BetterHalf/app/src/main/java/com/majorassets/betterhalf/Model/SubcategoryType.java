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
            case "movie":
                return MOVIE;
            case "music":
                return MUSIC;
            case "book":
                return BOOK;
            case "game":
                return GAME;
            case "theater":
                return THEATER;
            case "tvshow":
                return TV_SHOW;
            //FASHION
            case "accessory":
                return ACCESSORY;
            case "clothing":
                return CLOTHING;
            case "jewelry":
                return JEWELRY;
            case "shoe":
                return SHOE;
            //FOOD
            case "restaurant":
                return RESTAURANT;
            case "drink":
                return DRINK;
            case "entree":
                return ENTREE;
            case "side":
                return SIDE;
            case "snack":
                return SNACK;
            //HOBBY
            case "indoorhobbies":
                return INDOOR;
            case "outdoorhobbies":
                return OUTDOOR;
            case "sportsteams":
                return SPORT;
            //MEDICAL
            case "allergy":
                return ALLERGY;
            case "illness":
                return ILLNESS;
            case "phobia":
                return PHOBIA;
            case "medication":
                return MEDICATION;
            default:
                return INVALID;
        }
    }

    public static SubcategoryType getTypeFromTitle(String title)
    {
        switch(title.toLowerCase())
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

    public static String getDisplayableStringsFromType(SubcategoryType type, boolean isTableName)
    {
        switch (type)
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
                if (isTableName)
                    return "TVShows";
                return "TV Shows";
            case CLOTHING:
                return "Clothing";
            case SHOE:
                return "Shoes";
            case ACCESSORY:
                return "Accessories";
            case JEWELRY:
                return "Jewelry";
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
            case INDOOR:
                if (isTableName)
                    return "IndoorHobbies";
                return "Indoor Hobbies";
            case OUTDOOR:
                if (isTableName)
                    return "OutdoorHobbies";
                return "Outdoor Hobbies";
            case SPORT:
                if (isTableName)
                    return "SportsTeams";
                return "Sports Teams";
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

    public static String getFirebaseStringFromType(SubcategoryType type)
    {
        switch (type)
        {
            case MOVIE:
                return "movie";
            case MUSIC:
                return "music";
            case BOOK:
                return "book";
            case GAME:
                return "game";
            case THEATER:
                return "theater";
            case TV_SHOW:
                return "tvShow";
            case CLOTHING:
                return "clothing";
            case SHOE:
                return "shoe";
            case ACCESSORY:
                return "accessory";
            case JEWELRY:
                return "jewelry";
            case RESTAURANT:
                return "restaurant";
            case DRINK:
                return "drink";
            case SIDE:
                return "side";
            case ENTREE:
                return "entree";
            case SNACK:
                return "snack";
            case INDOOR:
                return "indoorHobbies";
            case OUTDOOR:
                return "outdoorHobbies";
            case SPORT:
                return "sportsTeams";
            case ALLERGY:
                return "allergy";
            case ILLNESS:
                return "illness";
            case PHOBIA:
                return "phobia";
            case MEDICATION:
                return "medication";
            default:
                return "invalid";
        }
    }

}


