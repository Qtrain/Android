package com.macbook.homeworkthree;

import java.util.ArrayList;

/**
 * Created by macbook on 11/26/15.
 */
public class AddressCollection {
    ArrayList<AddressAttributeObjects> addressList = new ArrayList<AddressAttributeObjects>();
    final int MAX_ADDRESS_COUNT = 5;


    public boolean isAddressLimitReached() {
        return addressList.size() >= MAX_ADDRESS_COUNT;
    }

    public int addAddresses(AddressAttributeObjects addressObject) throws Exception {
        if(!isAddressLimitReached()) {
            throw (new Exception("address limit reached"));
        }
        addressList.add(addressObject);

        return addressList.indexOf(addressObject);

    }

    public void setAddress(int addressIndex, AddressAttributeObjects addressObject) {
        addressList.set(addressIndex, addressObject);
    }

    public void removeAddress(int addressIndex) {
        addressList.remove(addressIndex);
    }

    public AddressAttributeObjects getAddress(int addressIndex) {
        return addressList.get(addressIndex);
    }
}

class AddressAttributeObjects {
    public String first;
    public String last;
    public String address;
    public String town;
    public String state;
    public String zip;

    public AddressAttributeObjects(String first, String last, String address, String town, String state, String zip) {
        this.first = first;
        this.last = last;
        this.address = address;
        this.town = town;
        this.state = state;
        this.zip = zip;
    }
}
