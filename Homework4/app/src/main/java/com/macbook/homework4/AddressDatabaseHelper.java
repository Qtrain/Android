package com.macbook.homework4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by macbook on 12/14/15.
 */
public class AddressDatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_ADDRESS = "address";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FIRST = "first";
    public static final String COLUMN_LAST = "last";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_TOWN = "town";
    public static final String COLUMN_STATE = "state";
    public static final String COLUMN_ZIP = "zip";

    private static final String DATABASE_NAME = "address.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation SQL statement
    private static final String DATABASE_CREATE_SQL = "create table "
            + TABLE_ADDRESS + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_FIRST + " text not null, "
            + COLUMN_LAST + " text not null, "
            + COLUMN_ADDRESS + " text not null);"
            + COLUMN_TOWN + " text not null, "
            + COLUMN_STATE + " text not null, "
            + COLUMN_ZIP + " text not null, ";

    //Constructer
    public AddressDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_SQL);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { // Check prior version to understand upgrade steps
        // Export data
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESS);
        onCreate(db);
        // Import Data
    }
}

/*************************
 * nested class
 **************************/
///this class gives us the ability to query the database using the database helper described above

class AddressDataSource {      // Database fields
    private SQLiteDatabase database;
    private AddressDatabaseHelper dbHelper;
    private String[] allColumns = {AddressDatabaseHelper.COLUMN_ID,
            AddressDatabaseHelper.COLUMN_ADDRESS, AddressDatabaseHelper.COLUMN_FIRST,
            AddressDatabaseHelper.COLUMN_LAST, AddressDatabaseHelper.COLUMN_TOWN,
            AddressDatabaseHelper.COLUMN_STATE, AddressDatabaseHelper.COLUMN_ZIP};

    //constructor
    AddressDataSource(Context context) {
        dbHelper = new AddressDatabaseHelper(context);
    }

    /**************************************************************/
    //uses the database that was instantiated during the constructor above
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    /**************************************************************/
    public void close() {
        dbHelper.close();
    }

    /****************************************************************/
    public long createAddress(AddressAttributeGroup address) {
        //this insert is an alternative to using straight sql
        ContentValues values = new ContentValues();

        values.put(AddressDatabaseHelper.COLUMN_FIRST, address.first);
        values.put(AddressDatabaseHelper.COLUMN_LAST, address.last);
        values.put(AddressDatabaseHelper.COLUMN_ADDRESS, address.address);
        values.put(AddressDatabaseHelper.COLUMN_TOWN, address.town);
        values.put(AddressDatabaseHelper.COLUMN_STATE, address.state);
        values.put(AddressDatabaseHelper.COLUMN_ZIP, address.zip);
        long insertId = database.insert(AddressDatabaseHelper.TABLE_ADDRESS,
                null,
                values);
        return insertId;
    }

    /*****************************************************************/
    public void deleteAddress(AddressAttributeGroup address) {
        long id = address.id;
        database.delete(AddressDatabaseHelper.TABLE_ADDRESS,
                AddressDatabaseHelper.COLUMN_ID + " = " + id, null);
    }

    /*****************************************************************/
    public AddressCollection getAllAddresses() throws Exception {
        AddressCollection addresses = new AddressCollection();

        Cursor cursor = database.query(AddressDatabaseHelper.TABLE_ADDRESS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            addresses.addAddress(cursorToAddressAttributeGroup(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return addresses;
    }

    /*****************************************************************************/
    private AddressAttributeGroup cursorToAddressAttributeGroup(Cursor cursor) {
        AddressAttributeGroup address = new AddressAttributeGroup(cursor.getInt(cursor.getColumnIndex(AddressDatabaseHelper.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(AddressDatabaseHelper.COLUMN_FIRST)),
                cursor.getString(cursor.getColumnIndex(AddressDatabaseHelper.COLUMN_LAST)),
                cursor.getString(cursor.getColumnIndex(AddressDatabaseHelper.COLUMN_ADDRESS)),
                cursor.getString(cursor.getColumnIndex(AddressDatabaseHelper.COLUMN_TOWN)),
                cursor.getString(cursor.getColumnIndex(AddressDatabaseHelper.COLUMN_STATE)),
                cursor.getString(cursor.getColumnIndex(AddressDatabaseHelper.COLUMN_ZIP)));
        return address;
    }

}

