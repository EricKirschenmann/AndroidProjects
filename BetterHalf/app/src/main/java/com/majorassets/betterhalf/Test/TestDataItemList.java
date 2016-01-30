package com.majorassets.betterhalf.Test;

import android.content.Context;

import com.majorassets.betterhalf.Model.BaseDataItem;
import com.majorassets.betterhalf.Model.Entertainment.MovieItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by dgbla on 12/13/2015.
 * Temporary class to create a dummy list of data items
 * Used for debugging before database was connected
 */
public class TestDataItemList
{
	private List<BaseDataItem> mItemList;
	private static TestDataItemList mTestList;


	private TestDataItemList(){
		mItemList = new ArrayList<BaseDataItem>();
		populateList();
	}

	//use of a singleton
	public static TestDataItemList get(Context context)
	{
		if(mTestList == null)
			mTestList = new TestDataItemList();

		return mTestList;
	}

	private void populateList(){
		MovieItem favMovie = new MovieItem("Movie", "Inception");
		favMovie.setIsFavorite(true);
		MovieItem favDirector = new MovieItem("Director", "Christopher Nolan");
		favDirector.setIsFavorite(true);
		MovieItem lstFavMovie = new MovieItem("Movie", "Twilight");
		lstFavMovie.setIsLeastFavorite(true);

		mItemList.add(favMovie);
		mItemList.add(favDirector);
		mItemList.add(lstFavMovie);
	}

	/** Get a item by referencing its unique ID
	 *
	 * @param id - the ID we are looking for
	 * @return The item with the associated id
	 */
	public BaseDataItem getItem(UUID id)
	{
		for(BaseDataItem item : mItemList)
			if(item.getID().equals(id))
				return item;
		return null;
	}

	public List<BaseDataItem> getItemList() {
		return mItemList;
	}

	public void setItemList(List<BaseDataItem> itemList) {
		mItemList = itemList;
	}
}