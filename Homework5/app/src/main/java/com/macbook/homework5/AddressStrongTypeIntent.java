package com.macbook.homework5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by macbook on 12/16/15.
 */
public class AddressStrongTypeIntent {

    public String mFirst;
    public String mLast;
    public String mAddress;
    ActionType mAction;
    int mAddressIndex = 0;
    Intent mIntent;
    public String mTown;
    public String mState;
    public String mZip;

    //constructor 1
    public AddressStrongTypeIntent(Intent mIntent) {
        Bundle bundle = mIntent.getExtras();
        try {
            mFirst = bundle.getString("mFirst");
            mLast = bundle.getString("mLast");
            mAddress = bundle.getString("mAddress");
            mTown = bundle.getString("mTown");
            mState = bundle.getString("mState");
            mZip = bundle.getString("mZip");
            mAction = ActionType.values()[bundle.getInt("mAction", 0)];
            mAddressIndex = bundle.getInt("addressindex");
        } catch (Exception ex) {
        }
    }

    //constructor 2
    public AddressStrongTypeIntent() {
        mFirst = "";
        mLast = "";
        mAddress = "";
        mZip = "";
        mState = "";
        mTown = "";
    }

    //third one
    public AddressStrongTypeIntent(AddressAttributeGroup addressAttributes, ActionType mAction, int mAddressIndex) {
        mFirst = addressAttributes.mFirst;
        mLast = addressAttributes.mLast;
        mAddress = addressAttributes.mAddress;
        mTown = addressAttributes.mTown;
        mState = addressAttributes.mState;
        mZip = addressAttributes.mZip;
        this.mAction = mAction;
        this.mAddressIndex = mAddressIndex;
    }

    /************************ methods ******************************************/
    public void clearIntent() {
        mIntent = null;

    }

    void putExtras() {
        mIntent.putExtra("mFirst", mFirst);
        mIntent.putExtra("mLast", mLast);
        mIntent.putExtra("mAddress", mAddress);
        mIntent.putExtra("mTown", mTown);
        mIntent.putExtra("mState", mState);
        mIntent.putExtra("mZip", mZip);
        mIntent.putExtra("mAction", mAction.ordinal());
        mIntent.putExtra("addressindex", mAddressIndex);
    }

    public Intent getmIntent() {
        if (mIntent == null) {
            mIntent = new Intent();
            putExtras();
        }
        return mIntent;
    }

    public Intent getIntent(Activity addressEntry,
                            Class<AddressEntry> class1) {
        if (mIntent == null) {
            mIntent = new Intent(addressEntry, class1);
            putExtras();
        }
        return mIntent;
    }

    public enum ActionType {
        ADD,
        EDIT,
        DELETE
    }

}

