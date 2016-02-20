package com.majorassets.betterhalf.Model;

/**
 * Created by dgbla on 12/13/2015.
 */
public class LikeableItem extends BaseDataItem
{
	protected boolean mIsFavorite;
	protected boolean mIsLeastFavorite;

	public LikeableItem(String label, String value) {
		super(label, value);
		mIsFavorite = false;
		mIsLeastFavorite = false;
	}

	protected boolean isFavorite() {
		return mIsFavorite;
	}

	protected void setIsFavorite(boolean isFavorite) {
		mIsLeastFavorite = !isFavorite;
		mIsFavorite = isFavorite;
	}

	protected boolean isLeastFavorite() {
		return mIsLeastFavorite;
	}

	protected void setIsLeastFavorite(boolean isLeastFavorite) {
		mIsFavorite = !isLeastFavorite;
		mIsLeastFavorite = isLeastFavorite;
	}
}
