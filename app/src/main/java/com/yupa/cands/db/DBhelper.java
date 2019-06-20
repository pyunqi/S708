package com.yupa.cands.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

    //Table Name
    public static final String TABLE_NAME = "tbStuff";
    //Column Names
    public static final String COL_STUFF_ID = "_id";
    public static final String COL_STUFF_NAME = "_name";
    public static final String COL_STUFF_PICTURE = "_picture";
    public static final String COL_STUFF_QUANTITY = "_quantity";
    public static final String COL_STUFF_PRICE = "_price";
    public static final String COL_STUFF_DESCRIPTION = "_description";
    public static final String COL_STUFF_LOCATION = "_location";
    public static final String COL_STUFF_TAG = "_tag";


    static final String[] columns = new String[]{DBhelper.COL_STUFF_ID,
            DBhelper.COL_STUFF_NAME, DBhelper.COL_STUFF_PICTURE,DBhelper.COL_STUFF_QUANTITY,COL_STUFF_PRICE,
            DBhelper.COL_STUFF_DESCRIPTION,DBhelper.COL_STUFF_LOCATION,DBhelper.COL_STUFF_TAG};
    //Database Information
    private static final String DATABASE_NAME = "cas.db";
    private static final int DATABASE_VERSION = 1;

    // creation SQLite statement
    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_NAME
            + "(" + COL_STUFF_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_STUFF_NAME + " TEXT NOT NULL, " + COL_STUFF_PICTURE + " TEXT NOT NUll," +
            COL_STUFF_QUANTITY + " TEXT NOT NULL, " + COL_STUFF_PRICE + " TEXT NOT NULL, " + COL_STUFF_DESCRIPTION +
            " TEXT NOT NULL, " + COL_STUFF_LOCATION + " TEXT NOT NULL, " + COL_STUFF_TAG + " TEXT NOT NULL);";

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        System.out.println("DB Created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        System.out.println("Table Created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        System.out.println("DB Updated");
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}