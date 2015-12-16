package com.quatrani.homework6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.*;


public class AddressDatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_ADDRESS = "address";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_IMAGE = "image";

    private static final String DATABASE_NAME = "address.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation SQL statement
    private static final String DATABASE_CREATE_SQL = "create table "
            + TABLE_ADDRESS + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_ADDRESS + " text not null, "
            + COLUMN_IMAGE + " text not null);";

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

class AddressDataSource {      // Database fields
    private SQLiteDatabase database;
    private AddressDatabaseHelper dbHelper;
    private String[] allColumns = {AddressDatabaseHelper.COLUMN_ID,
            AddressDatabaseHelper.COLUMN_ADDRESS, AddressDatabaseHelper.COLUMN_NAME};

    AddressDataSource(Context context) {
        dbHelper = new AddressDatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long createAddress(AddressAttributeGroup address) {
        ContentValues values = new ContentValues();

        values.put(AddressDatabaseHelper.COLUMN_NAME, address.name);
        values.put(AddressDatabaseHelper.COLUMN_ADDRESS, address.address);
        values.put(AddressDatabaseHelper.COLUMN_IMAGE, address.image);
        long insertId = database.insert(AddressDatabaseHelper.TABLE_ADDRESS,
                null,
                values);
        return insertId;
    }

    public void deleteAddress(AddressAttributeGroup address) {
        long id = address.id;
        database.delete(AddressDatabaseHelper.TABLE_ADDRESS,
                AddressDatabaseHelper.COLUMN_ID + " = " + id, null);
    }

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

    private AddressAttributeGroup cursorToAddressAttributeGroup(Cursor cursor) {
        AddressAttributeGroup address = new AddressAttributeGroup(cursor.getInt(cursor.getColumnIndex(AddressDatabaseHelper.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(AddressDatabaseHelper.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(AddressDatabaseHelper.COLUMN_ADDRESS)),
                cursor.getString(cursor.getColumnIndex(AddressDatabaseHelper.COLUMN_IMAGE)));
        return address;
    }

}
