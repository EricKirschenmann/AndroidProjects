package com.majorassets.betterhalf.Database;

import com.majorassets.betterhalf.Model.BaseDataItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by dgbla on 2/28/2016.
 */
//singleton for all of a user's data
public class DataItemRepository
{
    private static DataItemRepository sDataItemRepository;
    private List<BaseDataItem> mDataItems;

    private DataItemRepository()
    {
        mDataItems = new ArrayList<>();
    }

    public static DataItemRepository getDataItemRepository()
    {
        if(sDataItemRepository == null)
            sDataItemRepository = new DataItemRepository();
        return sDataItemRepository;
    }

    public List<BaseDataItem> getDataItems()
    {
        return mDataItems;
    }

    public BaseDataItem getSingleDataItem(UUID itemID)
    {
        for(BaseDataItem item : mDataItems)
            if(item.getID().equals(itemID))
                return item;

        return null;
    }
}
