package com.majorassets.expandablelistview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Eric on 10/8/15.
 */
public class CreateList {

    private List<String> listDataHeader = new ArrayList<String>();
    private HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();

    public CreateList(){
        prepareListData();
    }

    public HashMap<String, List<String>> getListChildData() {
        return listDataChild;
    }

    public List<String> getListHeaderData() {
        return listDataHeader;
    }

    /*
    * Preparing the list data
    * TODO: Move into it's own class file to make it more modular and get it out of the main Activity
    */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Entertainment");
        listDataHeader.add("Fashion");
        listDataHeader.add("Food");
        listDataHeader.add("Hobbies");
        listDataHeader.add("Medical");
        listDataHeader.add("Other");

        // Adding child data
        List<String> food = new ArrayList<String>();
        food.add("Restaurants");
        food.add("Drinks");
        food.add("Dishes");
        food.add("Snacks");

        Collections.sort(food);

        List<String> entertainment = new ArrayList<String>();
        entertainment.add("Books");
        entertainment.add("Games");
        entertainment.add("Movies");
        entertainment.add("TV Shows");
        entertainment.add("Music");
        entertainment.add("Theater");

        Collections.sort(entertainment);

        List<String> fashion = new ArrayList<String>();
        fashion.add("Clothing");
        fashion.add("Shoes");
        fashion.add("Accessories");
        fashion.add("Jewelry");

        Collections.sort(fashion);

        List<String> hobbies = new ArrayList<String>();
        hobbies.add("Indoor");
        hobbies.add("Outdoor");
        hobbies.add("Sports");

        Collections.sort(hobbies);

        List<String> medical = new ArrayList<String>();
        medical.add("Allergies");
        medical.add("Illnesses");
        medical.add("Phobias");
        medical.add("Medication");

        Collections.sort(medical);

        List<String> other = new ArrayList<String>();
        other.add("Animals");
        other.add("Colors");
        other.add("Add Your Own");

        Collections.sort(other);

        listDataChild.put(listDataHeader.get(0), entertainment); // Header, Child data
        listDataChild.put(listDataHeader.get(1), fashion);
        listDataChild.put(listDataHeader.get(2), food);
        listDataChild.put(listDataHeader.get(3), hobbies);
        listDataChild.put(listDataHeader.get(4), medical);
        listDataChild.put(listDataHeader.get(5), other);
    }


}
