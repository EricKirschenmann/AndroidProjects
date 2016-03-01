package com.majorassets.betterhalf.Database;

import com.majorassets.betterhalf.Model.BaseDataItem;
import com.majorassets.betterhalf.Model.Subcategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by dgbla on 2/28/2016.
 */
//singleton for all of a user's data
public class DataItemRepository
{
    private static DataItemRepository sDataItemRepository;
    private Map<Subcategory, List<BaseDataItem>> mDataItems;

    private DataItemRepository()
    {}

    public static DataItemRepository getDataItemRepository()
    {
        if(sDataItemRepository == null)
            sDataItemRepository = new DataItemRepository();
        return sDataItemRepository;
    }

    public Map<Subcategory, List<BaseDataItem>> getDataItems()
    {
        return mDataItems;
    }
}
