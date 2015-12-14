package com.macbook.homework4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by macbook on 12/5/15.
 */
public class AddressStrongTypeIntent {
    String mFirst;
    String mLast;
    String mAddress;
    String mTown;
    String mState;
    String zip;

    ActionType action; //adding enum need clarification on what adding an action type enum is doing

    int addressIndex = 0; //not sure?

    Intent mIntent;//not real sure?

    /******************************************************/
    //Create 3 Constructors
    //with bundle and mIntent
    public AddressStrongTypeIntent(Intent intent){
        Bundle bundle = intent.getExtras();  //create a bundle object to use with mIntent extras
        try {
            mFirst = bundle.getString("mFirst");
            mLast = bundle.getString("mLast");
            mAddress = bundle.getString("mEditAddress");
            mTown = bundle.getString("mEditTown");
            mState = bundle.getString("mEditState");
            zip = bundle.getString("zip");
            action = ActionType.values()[bundle.getInt("action", 0)];
            addressIndex = bundle.getInt("addressIndex");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    //constructor 2 without bundle
    public AddressStrongTypeIntent(AddressAttributeGroup addressAttributes, ActionType action,
                                   int addressIndex ){
        mFirst = addressAttributes.first;
        mLast = addressAttributes.last;
        mAddress = addressAttributes.address;
        mTown = addressAttributes.town;
        mState = addressAttributes.state;
        zip = addressAttributes.zip;

    }
    //empty constructor 1
    public AddressStrongTypeIntent() {
        mFirst = "";
        mLast = "";
        mAddress = "";
        mTown = "";
        mState = "";
        zip = "";
    }
    /***********************************************************/

    /*********** Intent Methods ******************************/
    public void clearIntent(){
        mIntent = null;
    }

    void putExtras(){
        mIntent.putExtra("mFirst", mFirst);
        mIntent.putExtra("mLast", mLast);
        mIntent.putExtra("mEditAddress", mAddress);
        mIntent.putExtra("mEditTown", mTown);
        mIntent.putExtra("mEditState", mState);
        mIntent.putExtra("zip", zip);
    }

    public Intent getmIntent(){
        if(mIntent ==null){
            mIntent = new Intent();
            putExtras();
        }
        return mIntent;
    }

    //don't understand this generics reflection stuff
    public Intent getmIntent(Activity addressEntry, Class<AddressEntry> class1){
        if (mIntent == null){
            mIntent = new Intent(addressEntry, class1);
            putExtras();
        }
        return mIntent;
    }
    //can i seperate out this class?
    public enum ActionType{
        ADD,
        EDIT,
       DELETE

    }
}
