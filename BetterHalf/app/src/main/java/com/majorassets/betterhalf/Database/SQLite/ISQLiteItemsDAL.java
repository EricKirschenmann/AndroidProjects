package com.majorassets.betterhalf.Database.SQLite;

import com.majorassets.betterhalf.Model.BaseDataItem;
import com.majorassets.betterhalf.Model.BaseLikeableItem;
import com.majorassets.betterhalf.Model.Subcategory;

import java.util.List;
import java.util.UUID;

/**
 * Created by Marissa on 4/20/2016.
 */
public interface ISQLiteItemsDAL {
    void addItem(BaseLikeableItem item, String tableName);
    void addItem(BaseDataItem item, String tableName);
    void updateItem(BaseLikeableItem item, String tableName, String colUUID);
    BaseDataItem deleteItem(BaseDataItem item, String tableName, String colUUID);

    List<BaseLikeableItem> getEntertainmentItems(String tableName, UUID userId);
    List<BaseLikeableItem> getFashionItems(String tableName, UUID userId);
    List<BaseLikeableItem> getFoodItems(String tableName, UUID userId);
    List<BaseLikeableItem> getHobbyItems(String tableName, UUID userId);
    List<BaseLikeableItem> getMedicalItems(String tableName, UUID userId);
}
