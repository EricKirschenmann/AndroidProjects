package com.majorassets.betterhalf.Model;

import java.util.UUID;

/**
 * Created by dgbla on 12/13/2015.
 * A DataItem in an abstraction of any possible information that a user could input
 * At the most fundamental level a DataItem is defined by its label and its value
 *
 * For example, a label may be "Star Wars" with a value of "Empire Strikes Back"
 * signifying a user favorite (or least favorite) Star Wars movie or soundtrack
 */
public abstract class DataItem implements IDataItem
{
	protected UUID mID;
	protected String mLabel;
	protected String mValue;

	public DataItem(String label, String value) {
		mLabel = label;
		mValue = value;
	}

	public UUID getID() {
		return mID;
	}

	public void setID(UUID ID) {
		mID = ID;
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
}