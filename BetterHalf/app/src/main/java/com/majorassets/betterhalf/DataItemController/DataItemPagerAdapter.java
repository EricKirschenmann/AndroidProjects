package com.majorassets.betterhalf.DataItemController;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.majorassets.betterhalf.GlobalResources;
import com.majorassets.betterhalf.Model.Subcategory;
import com.majorassets.betterhalf.Model.SubcategoryType;

/**
 * Created by dgbla on 3/4/2016.
 */
public class DataItemPagerAdapter extends FragmentPagerAdapter
{
    public DataItemPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        return DataItemActivityFragment.newInstance(position+1);
    }

    @Override
    public int getCount()
    {
        return GlobalResources.Subcategories.get(GlobalResources.mainTypePressed).size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        Subcategory sub = GlobalResources.Subcategories.get(GlobalResources.mainTypePressed).get(position);
        return SubcategoryType.getStringFromType(sub.getMainType(), sub.getType());
    }
}
