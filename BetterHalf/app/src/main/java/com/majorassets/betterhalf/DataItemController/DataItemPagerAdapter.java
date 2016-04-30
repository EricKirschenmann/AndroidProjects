package com.majorassets.betterhalf.DataItemController;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.majorassets.betterhalf.GlobalResources;
import com.majorassets.betterhalf.Model.Subcategory;
import com.majorassets.betterhalf.Model.SubcategoryType;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class DataItemPagerAdapter extends FragmentPagerAdapter {

    public DataItemPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return DataItemActivityFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return GlobalResources.Subcategories.get(GlobalResources.mainTypePressed).size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Subcategory sub = GlobalResources.Subcategories.get(GlobalResources.mainTypePressed).get(position);
        return SubcategoryType.getStringFromType(sub.getMainType(), sub.getType());
    }
}