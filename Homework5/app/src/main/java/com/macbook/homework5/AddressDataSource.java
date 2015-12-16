package com.macbook.homework5;

import android.app.Service;
import android.content.ContentValues;//create mAddress()
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;//added from Context context


/**
 * Created by macbook on 12/15/15.
 */
public class AddressDataSource extends Service {

    private final IBinder mServiceBinder = new ServiceBinder();
    private SQLiteDatabase mDatabase;
    private AddressDatabaseHelper mDbHelper;
    private String[] mAllColumns =

            {       AddressDatabaseHelper.COLUMN_ID,
                    AddressDatabaseHelper.COLUMN_ADDRESS,
                    AddressDatabaseHelper.COLUMN_FIRST,
                    AddressDatabaseHelper.COLUMN_LAST,
                    AddressDatabaseHelper.COLUMN_TOWN,
                    AddressDatabaseHelper.COLUMN_STATE,
                    AddressDatabaseHelper.COLUMN_ZIP      };


    //constructor
    public AddressDataSource() {
        super();
    }

    public void initDataSource(Context context) {
        mDbHelper = new AddressDatabaseHelper(context);
    }

    public void open() {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    /************** create an mAddress ******************************/
    public long createAddress(AddressAttributeGroup address) {
        ContentValues values = new ContentValues();

        values.put(AddressDatabaseHelper.COLUMN_FIRST, address.mFirst);
        values.put(AddressDatabaseHelper.COLUMN_LAST, address.mLast);
        values.put(AddressDatabaseHelper.COLUMN_ADDRESS, address.mAddress);
        values.put(AddressDatabaseHelper.COLUMN_TOWN, address.mTown);
        values.put(AddressDatabaseHelper.COLUMN_STATE, address.mState);
        values.put(AddressDatabaseHelper.COLUMN_ZIP, address.mZip);

        long insertId = mDatabase.insert(AddressDatabaseHelper.TABLE_ADDRESS,
                null,
                values);
        return insertId;
    }

    /******************** delete and mAddress************************/
    public void deleteAddrress(AddressAttributeGroup address) {
        long id = address.mId;
        mDatabase.delete(AddressDatabaseHelper.TABLE_ADDRESS, AddressDatabaseHelper.COLUMN_ID + " =" + id, null);
    }

    /********************** instance of AddressCollectionClass ***********/
    public AddressCollection getAllAddresses() throws Exception {
        AddressCollection addresses = new AddressCollection();

        Cursor cursor = mDatabase.query(AddressDatabaseHelper.TABLE_ADDRESS, mAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            addresses.addAddress(cursorToAddressAttributeGroup(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return addresses;
    }

    /*********** Binder given to clients ***************************/
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

    /********* auto implemented from method signature ************/
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mServiceBinder;
    }

    /********* service binder sublclass ***************/
    public class ServiceBinder extends Binder {
        AddressDataSource getService() {
            return AddressDataSource.this;
        }
    }
}

/********************************************************************************************/
//what is a cursor?
//values.put suggests hash
//gain higher level view of this file

