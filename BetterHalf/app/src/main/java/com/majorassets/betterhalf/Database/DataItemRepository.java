package com.majorassets.betterhalf.Database;

import com.majorassets.betterhalf.Model.BaseDataItem;
import com.majorassets.betterhalf.Model.SubcategoryType;

import java.util.List;
import java.util.Map;

/**
 * Created by dgbla on 2/28/2016.
 */
//singleton for all of a user's data
public class DataItemRepository
{
    private static DataItemRepository sDataItemRepository;
    private Map<SubcategoryType, List<BaseDataItem>> mDataItems;

    private DataItemRepository()
    {}

    public static DataItemRepository getDataItemRepository()
    {
        if(sDataItemRepository == null)
            sDataItemRepository = new DataItemRepository();
        return sDataItemRepository;
    }

    public Map<SubcategoryType, List<BaseDataItem>> getDataItems()
    {
        return mDataItems;
    }

    public void setDataItems(Map<SubcategoryType, List<BaseDataItem>> dataItems)
    {
        this.mDataItems = dataItems;
    }
}
