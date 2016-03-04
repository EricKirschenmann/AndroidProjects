package com.majorassets.betterhalf.Model;

/**
 * Created by dgbla on 1/29/2016.
 */
public abstract class BaseLikeableItem extends BaseDataItem
{
	protected boolean mIsFavorite;
	protected boolean mIsLeastFavorite;

	public BaseLikeableItem(String label, String value) {
		super(label, value);
		mIsFavorite = false;
		mIsLeastFavorite = false;
	}

	public boolean isFavorite() {
		return mIsFavorite;
	}

	public void setIsFavorite(boolean isFavorite) {
		mIsLeastFavorite = !isFavorite;
		mIsFavorite = isFavorite;
	}

	public boolean isLeastFavorite() {
		return mIsLeastFavorite;
	}

	public void setIsLeastFavorite(boolean isLeastFavorite) {
		mIsFavorite = !isLeastFavorite;
		mIsLeastFavorite = isLeastFavorite;
	}
}
