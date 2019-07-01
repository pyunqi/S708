package com.yupa.cands.stuff;

import android.content.Context;
import android.support.v4.content.FileProvider;

import com.yupa.cands.db.DBController;
import com.yupa.cands.db.Stuff;

import java.io.File;
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
    public static void deleteStuff(Context context,int _id,String path){
        dbController = new DBController(context);
        dbController.deleteStuff(_id);
        DelFile dF = new DelFile(path);
        dF.start();
    }

    static class DelFile extends Thread {

        private String fPath;
        DelFile(String path){
            fPath = path;
        }

        @Override
        public void run() {
            super.run();
            File delFile = new File(fPath);
            delFile.delete();
        }
    }

}
