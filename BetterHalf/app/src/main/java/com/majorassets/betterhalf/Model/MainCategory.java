package com.majorassets.betterhalf.Model;

import android.os.AsyncTask;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.majorassets.betterhalf.Database.DataProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dgbla on 3/4/2016.
 */
public class MainCategory
{
    private String mName;
    private List<SubcategoryType> mSubcategories;
    private Firebase ref;

    public MainCategory(String name)
    {
        mName = name;
        mSubcategories = new ArrayList<>();
    }

    /*
    GETTERS and SETTERS
     */
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
