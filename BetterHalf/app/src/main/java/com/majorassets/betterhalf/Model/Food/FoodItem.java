package com.majorassets.betterhalf.Model.Food;

import com.majorassets.betterhalf.Model.BaseLikeableItem;

/**
 * Created by dgbla on 1/29/2016.
 */
public class FoodItem extends BaseLikeableItem
{
	public FoodItem(String id) { super(id);}
	public FoodItem(String label, String value){
		super(label, value);
	}
}
