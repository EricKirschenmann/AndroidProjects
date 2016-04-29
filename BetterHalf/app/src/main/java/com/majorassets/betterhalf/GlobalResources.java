package com.majorassets.betterhalf;

import com.majorassets.betterhalf.Model.MainCategoryType;
import com.majorassets.betterhalf.Model.Subcategory;
import com.majorassets.betterhalf.Model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dgbla on 2/28/2016.
 */
public class GlobalResources {
    public static MainCategoryType mainTypePressed = MainCategoryType.INVALID;
    public static Map<MainCategoryType, List<Subcategory>> Subcategories = new HashMap<>();
    public static List<User> Users = new ArrayList<>();
    public static User AppUser;
    public static void addToGlobalSubcategories(MainCategoryType main, Subcategory sub) {
        List<Subcategory> lst;

        if(Subcategories.get(main) == null) {
            lst = new ArrayList<>();
            lst.add(sub);
            Subcategories.put(main, lst);
        } else {
            lst = Subcategories.get(main);
            lst.add(sub);
        }
    }
}
