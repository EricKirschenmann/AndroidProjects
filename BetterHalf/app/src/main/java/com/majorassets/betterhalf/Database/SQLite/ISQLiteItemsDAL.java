package com.majorassets.betterhalf.Database.SQLite;

import com.majorassets.betterhalf.Model.BaseDataItem;
import com.majorassets.betterhalf.Model.BaseLikeableItem;
import com.majorassets.betterhalf.Model.Subcategory;
import com.majorassets.betterhalf.Model.SubcategoryType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Marissa on 4/20/2016.
 */
public interface ISQLiteItemsDAL
{
    int getItemID(BaseLikeableItem item, String tableName);
    void addItem(BaseLikeableItem item, String tableName);
    void addItem(BaseDataItem item, String tableName);
    void updateItem(BaseLikeableItem item, String tableName, String colUUID);
    BaseDataItem deleteItem(BaseLikeableItem item, String tableName, String colUUID);

    List<BaseLikeableItem> getEntertainmentItems(String tableName, UUID userId);
    List<BaseLikeableItem> getFashionItems(String tableName, UUID userId);
    List<BaseLikeableItem> getFoodItems(String tableName, UUID userId);
    List<BaseLikeableItem> getHobbyItems(String tableName, UUID userId);
    List<BaseLikeableItem> getMedicalItems(String tableName, UUID userId);

    void getAllUserEntertainmentItems(UUID userId, Map<SubcategoryType, List<BaseLikeableItem>> items);
    void getAllUserFashionItems(UUID userId, Map<SubcategoryType, List<BaseLikeableItem>> items);
    void getAllUserFoodItems(UUID userId, Map<SubcategoryType, List<BaseLikeableItem>> items);
    void getAllUserHobbyItems(UUID userId, Map<SubcategoryType, List<BaseLikeableItem>> items);
    void getAllUserMedicalItems(UUID userId, Map<SubcategoryType, List<BaseLikeableItem>> items);
}
