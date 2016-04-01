package com.majorassets.betterhalf.Model;

/**
 * Created by dgbla on 3/7/2016.
 */
public class Subcategory
{
    private SubcategoryType type;
    private MainCategoryType mainType;

    public Subcategory()
    {

    }

    public Subcategory(SubcategoryType type)
    {
        this.type = type;
    }

    public SubcategoryType getType()
    {
        return type;
    }

    public void setType(SubcategoryType type)
    {
        this.type = type;
    }

    public MainCategoryType getMainType()
    {
        return mainType;
    }

    public void setMainType(MainCategoryType mainType)
    {
        this.mainType = mainType;
    }
}
