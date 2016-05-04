package com.majorassets.betterhalf.Model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by dgbla on 1/29/2016.
 * A BaseDataItem in an abstraction of any possible information that a user could input
 * At the most fundamental level a BaseDataItem is defined by its label and its value
 *
 * For example, a label may be "Star Wars" with a value of "Empire Strikes Back"
 * signifying a user favorite (or least favorite) Star Wars movie or soundtrack
 */
public abstract class BaseDataItem implements Serializable
{
	protected String mID;
	protected UUID mUserID;
	protected String mLabel;
	protected String mValue;

	//DEFAULT CONSTRUCTOR
	public BaseDataItem() {}

	//OVERLOADED CONSTRUCTOR
	public BaseDataItem(String label, String value) {
		mLabel = label;
		mValue = value;
	}

	public String getID() {
		return mID;
	}

	public void setID(String ID) {
		mID = ID;
	}

	public UUID getUserID()
	{
		return mUserID;
	}

	public void setUserID(UUID userID)
	{
		mUserID = userID;
	}

	public String getLabel() {
		return mLabel;
	}

	public void setLabel(String label) {
		mLabel = label;
	}

	public String getValue() {
		return mValue;
	}

	public void setValue(String value) {
		mValue = value;
	}

	@Override
	/** Override object equals method to compare on the item's labels and values
	 *
	 * @param o - the object you are comparing against
	 * @returns - true if the labels and values are the same; false otherwise
	 */
	public boolean equals(Object o)
	{
		if(!(o.getClass().isInstance(this)))
			return false;
		else{
			BaseDataItem item = (BaseDataItem)o;
			if(item.mID.equals(this.mID))
				return true;
			else
				return false;
		}
	}
}