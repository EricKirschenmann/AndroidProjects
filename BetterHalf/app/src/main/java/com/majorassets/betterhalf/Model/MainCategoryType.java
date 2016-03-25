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
        switch(mainCategory)
        {
            case "entertainment":
                return MainCategoryType.ENTERTAINMENT;
            case "fashion":
                return MainCategoryType.FASHION;
            default:
                return MainCategoryType.INVALID;
        }
    }
}
