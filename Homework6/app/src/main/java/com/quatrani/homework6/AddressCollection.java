package com.quatrani.homework6;

import java.util.ArrayList;

public class AddressCollection {
    final int MAX_ADDRESS_COUNT = 15;
    ArrayList<AddressAttributeGroup> addressList = new ArrayList<AddressAttributeGroup>();

    public boolean isAddressLimitReached() {
        return (addressList.size() >= MAX_ADDRESS_COUNT);
    }

    public int addAddress(AddressAttributeGroup address) throws Exception {
        if (isAddressLimitReached())
            throw (new Exception("Max Address Reached."));
        addressList.add(address);
        return addressList.indexOf(address);
    }

    public void setAddress(int addressIndex, AddressAttributeGroup address) {
        addressList.set(addressIndex, address);
    }

    public void removeAddress(int addressIndex) {
        addressList.remove(addressIndex);
    }

    public AddressAttributeGroup getAddress(int addressIndex) {
        return addressList.get(addressIndex);
    }
}

class AddressAttributeGroup {
    public String name;
    public String address;
    public String image;
    public long id;

    public AddressAttributeGroup(String name, String address, String image) {
        this.name = name;
        this.address = address;
        this.image = image;
    }

    public AddressAttributeGroup(int id, String name, String address, String image) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.image = image;
    }
}
