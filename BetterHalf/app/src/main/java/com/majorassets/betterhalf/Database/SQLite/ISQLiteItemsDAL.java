package com.majorassets.betterhalf.Database.SQLite;

import com.majorassets.betterhalf.Model.BaseDataItem;

/**
 * Created by Marissa on 4/20/2016.
 */
public interface ISQLiteItemsDAL {
    void addItem(BaseDataItem item, String tableName, String colUUID, String colLabel, String colValue);
    void updateItem(BaseDataItem item, String tableName, String colUUID, String colLabel, String colValue);
    BaseDataItem deleteItem(BaseDataItem item, String tableName, String colUUID);
}
