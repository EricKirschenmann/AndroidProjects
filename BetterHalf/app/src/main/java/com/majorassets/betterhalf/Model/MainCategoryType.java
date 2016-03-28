package com.majorassets.betterhalf.Model;

/**
 * Created by dgbla on 2/29/2016.
 */
public enum MainCategoryType
{
    ENTERTAINMENT,
    FASHION,
    FOOD,
    HOBBY,
    MEDICAL,
    INVALID;

    public static MainCategoryType getTypeFromString(String mainCategory)
    {
        switch(mainCategory.toLowerCase())
        {
            case "entertainment":
                return MainCategoryType.ENTERTAINMENT;
            case "fashion":
                return MainCategoryType.FASHION;
            case "food":
                return MainCategoryType.FOOD;
            case "hobby":
                return MainCategoryType.HOBBY;
            case "medical":
                return MainCategoryType.MEDICAL;
            default:
                return MainCategoryType.INVALID;
        }
    }
}
