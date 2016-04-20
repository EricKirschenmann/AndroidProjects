package com.majorassets.betterhalf.Database.SQLite;

import com.majorassets.betterhalf.Model.BaseDataItem;

/**
 * Created by Marissa on 4/20/2016.
 */
public interface ISQLiteItemsDAL {
    void addItem(BaseDataItem item);
    void updateItem(BaseDataItem item);
    BaseDataItem deleteItem(BaseDataItem item);
}
