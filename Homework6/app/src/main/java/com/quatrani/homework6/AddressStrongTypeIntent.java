package com.quatrani.homework6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class AddressStrongTypeIntent {
    public String name;
    public String address;
    public String image;
    ActionType action;
    int addressIndex = 0;
    Intent intent;
    public AddressStrongTypeIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        try {
            name = bundle.getString("name");
            address = bundle.getString("address");
            image = bundle.getString("image");
            action = ActionType.values()[bundle.getInt("action", 0)];
            addressIndex = bundle.getInt("addressindex");
        } catch (Exception ex) {
        }
    }

    public AddressStrongTypeIntent() {
        name = "";
        address = "";
    }

    public AddressStrongTypeIntent(AddressAttributeGroup addressAttributes, ActionType action, int addressIndex) {
        name = addressAttributes.name;
        address = addressAttributes.address;
        image = addressAttributes.image;
        this.action = action;
        this.addressIndex = addressIndex;
    }

    public void clearIntent() {
        intent = null;

    }

    void putExtras() {
        intent.putExtra("name", name);
        intent.putExtra("address", address);
        intent.putExtra("image", image);
        intent.putExtra("action", action.ordinal());
        intent.putExtra("addressindex", addressIndex);
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

    public enum ActionType {
        ADD,
        EDIT,
        DELETE
    }

}
