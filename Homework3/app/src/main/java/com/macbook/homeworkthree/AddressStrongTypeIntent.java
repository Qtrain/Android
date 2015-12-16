package com.macbook.homeworkthree;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
public class AddressStrongTypeIntent {

    //private static final String TAG = "IntentActivity";



    public String first;
    public String last;
    public String address;
    public String town;
    public String state;
    public String zip;

    public enum ActionType{
        ADD,
        EDIT,
        DELETE
    }

    ActionType action;
    int addressIndex = 0;

    Intent intent;

    //constructor 1
    public AddressStrongTypeIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        try {
            first = bundle.getString("first");
            last = bundle.getString("last");
            address = bundle.getString("address");
            town = bundle.getString("town");
            state = bundle.getString("state");
            zip = bundle.getString("zip");

            action = ActionType.values()[bundle.getInt("action", 0)];
            addressIndex = bundle.getInt("addressIndex");

        } catch (Exception ex) {
            //Log.d(TAG, "intent", new Exception());
            ex.printStackTrace();

        }
    }

    //constructor 2
    public AddressStrongTypeIntent() {
        first = "";
        last = "";
        address = "";
        town = "";
        state = "";
        zip = "";
        address = "";
    }

    //constructor 3
    public AddressStrongTypeIntent(AddressAttributeObjects addressAttributes, ActionType action, int addressIndex){
        first = addressAttributes.first;
        last = addressAttributes.last;
        address = addressAttributes.address;
        town = addressAttributes.town;
        state = addressAttributes.state;
        zip = addressAttributes.zip;
        this.action = action;
        this.addressIndex = addressIndex;

    }

    public void clearIntent() {
        intent = null;

    }

    void putExtras() {
        intent.putExtra("first", first);
        intent.putExtra("last", last);
        intent.putExtra("address", address);
        intent.putExtra("town", town);
        intent.putExtra("state", state);
        intent.putExtra("zip", zip);
        intent.putExtra("action", action.ordinal());
        intent.putExtra("addressIndex", addressIndex);
    }

    public Intent getIntent() {
        if (intent == null) {
            intent = new Intent();
            putExtras();
        }
        return intent;
    }

    public Intent getIntent(Activity addressEntry,
                            Class<AddressEntry> class1) {
        if (intent == null) {
            intent = new Intent(addressEntry, class1);
            putExtras();
        }
        return intent;
    }

}
