package com.macbook.homework5;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class AddressDatabaseHelper extends SQLiteOpenHelper
{
    public static final String TABLE_ADDRESS = "mAddress";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FIRST = "first";
    public static final String COLUMN_LAST = "last";
    public static final String COLUMN_ADDRESS = "mAddress";
    public static final String COLUMN_TOWN = "town";
    public static final String COLUMN_STATE = "state";
    public static final String COLUMN_ZIP = "zip";

    private static final String DATABASE_NAME = "mAddress.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation SQL statement
    private static final String DATABASE_CREATE_SQL = "create table "
            + TABLE_ADDRESS  + "("
            + COLUMN_ID      + " integer primary key autoincrement, "
            + COLUMN_FIRST    + " text not null, "
            + COLUMN_LAST    + " text not null, "
            + COLUMN_ADDRESS    + " text not null, "
            + COLUMN_TOWN+ " text not null);"
            + COLUMN_STATE+ " text not null, "
            + COLUMN_ZIP+ " text not null, ";

    public AddressDatabaseHelper(Context context)
    { super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    { database.execSQL(DATABASE_CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    { // Check prior version to understand upgrade steps
        // Export data
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESS);
        onCreate(db);
        // Import Data
    }
}


