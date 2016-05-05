package com.majorassets.betterhalf.Helpers;

import android.util.Log;

import com.majorassets.betterhalf.GlobalResources;
import com.majorassets.betterhalf.Model.BaseLikeableItem;
import com.majorassets.betterhalf.Model.Subcategory;
import com.majorassets.betterhalf.Model.SubcategoryType;
import com.majorassets.betterhalf.Model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dgbla on 5/3/2016.
 */
public class UserMapHelper
{

    public static void addItem(User appUser, SubcategoryType type, BaseLikeableItem item)
    {
        Map<SubcategoryType, List<BaseLikeableItem>> userDataMap = appUser.getDataItems();

        List<BaseLikeableItem> list;

        if(userDataMap.get(type) == null)
        {
            list = new ArrayList<>();
            list.add(item);
            userDataMap.put(type, list);
        }
        else
        {
            list = userDataMap.get(type);
            list.add(item);
        }
    }

    /**
     * Remove an item from the User's data store (A map)
     * @param appUser - the current user
     * @param type - pass through the type for easy access in the hashmap
     * @param item - the item to be deleted
     * @return
     */
    public static BaseLikeableItem deleteItem(User appUser, SubcategoryType type, BaseLikeableItem item)
    {
        Map<SubcategoryType, List<BaseLikeableItem>> userDataMap = appUser.getDataItems();

        try
        {
            List<BaseLikeableItem> subCatList = userDataMap.get(type);

            if(!subCatList.isEmpty())
            {
                for (BaseLikeableItem likeableItem : subCatList)
                {
                    if(likeableItem.getID().equals(item.getID()))
                        subCatList.remove(likeableItem);
                }

                userDataMap.put(type, subCatList); //update the list in the map
            }
        }
        catch(Exception e)
        {
            Log.e("ERROR", e.getMessage());
        }

        return item; //return the deleted item
    }

    public static void updateItem(User appUser, SubcategoryType type, BaseLikeableItem item)
    {
        Map<SubcategoryType, List<BaseLikeableItem>> userDataMap = appUser.getDataItems();

        try
        {
            List<BaseLikeableItem> subCatList = userDataMap.get(type);

            if(!subCatList.isEmpty())
            {
                for (BaseLikeableItem likeableItem : subCatList)
                {
                    if(likeableItem.getID().equals(item.getID()))
                    {
                        subCatList.remove(likeableItem);
                        subCatList.add(item); //replace the item
                    }
                }
            }
            else
            {
                subCatList = new ArrayList<>();
                subCatList.add(item);
            }

            userDataMap.put(type, subCatList); //update the list in the map
        }
        catch(Exception e)
        {
            Log.e("ERROR", e.getMessage());
        }
    }
}
