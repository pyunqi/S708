package com.yupa.cands.stuff;

import android.content.Context;

import com.yupa.cands.db.DBController;
import com.yupa.cands.db.Stuff;
import com.yupa.cands.utils.ShowMessage;

import java.util.ArrayList;
import java.util.List;

public class StuffManagement {


    public static List<Stuff> getStuffs(DBController dbController, Context context) {

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
