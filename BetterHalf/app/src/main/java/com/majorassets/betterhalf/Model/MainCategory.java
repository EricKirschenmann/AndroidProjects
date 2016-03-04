package com.majorassets.betterhalf.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dgbla on 3/4/2016.
 */
public class MainCategory
{
    private String mName;
    private List<SubcategoryType> mSubcategories;

    public MainCategory(String name)
    {
        mName = name;
        mSubcategories = new ArrayList<>();
    }

    public String getName()
    {
        return mName;
    }

    public void setName(String name)
    {
        mName = name;
    }

    public List<SubcategoryType> getSubcategories()
    {
        return mSubcategories;
    }

    public void setSubcategories(List<SubcategoryType> subcategories)
    {
        mSubcategories = subcategories;
    }
}
