package com.yupa.cands.stuff;

import android.content.Context;

import com.yupa.cands.db.DBController;
import com.yupa.cands.db.Stuff;

import java.util.ArrayList;
import java.util.List;

public class StuffManagement {

    private  static  DBController dbController;
    public static List<Stuff> getStuffs(Context context) {

        List<Stuff> stuffs = new ArrayList<>();
        dbController = new DBController(context);
        if (dbController.getAllStuff().isEmpty()) {

        } else {
            for (Stuff stuff : dbController.getAllStuff()) {
                stuffs.add(stuff);
            }
        }
        return stuffs;
    }

}
