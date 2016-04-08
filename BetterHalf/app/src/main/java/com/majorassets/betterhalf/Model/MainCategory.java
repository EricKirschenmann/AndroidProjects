package com.majorassets.betterhalf.Model;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dgbla on 3/4/2016.
 */
public class MainCategory
{
    private MainCategoryType type;
    private List<Subcategory> mSubcategories;
    private Firebase ref;

    public MainCategory(MainCategoryType type)
    {
        this.type = type;
        mSubcategories = new ArrayList<>();
    }

    /*
    GETTERS and SETTERS
     */
    public MainCategoryType getType()
    {
        return type;
    }

    public void setName(MainCategoryType type)
    {
        this.type = type;
    }

    public List<Subcategory> getSubcategories()
    {
        return mSubcategories;
    }

    public void setSubcategories(List<Subcategory> subcategories)
    {
        mSubcategories = subcategories;
    }
}
