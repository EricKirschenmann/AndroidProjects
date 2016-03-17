package com.majorassets.betterhalf.DataItemController;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

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
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return "Fragment # " + (position + 1);
    }
}
