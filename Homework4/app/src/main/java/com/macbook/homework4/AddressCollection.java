package com.macbook.homework4;

import java.util.ArrayList;

/**
 * Created by macbook on 12/5/15.
 */
public class AddressCollection {
    final int MAX_ADDRESS_COUNT = 15;
    ArrayList<AddressAttributeGroup> addressList = new ArrayList<>();


    public boolean isAddressLimitReached() {
        return (addressList.size() >= MAX_ADDRESS_COUNT);
    }

    public int addAddress(AddressAttributeGroup address) throws Exception {
        if (isAddressLimitReached()) {
            throw (new Exception("max"));
        }
        addressList.add(address);
        return addressList.indexOf(address);
    }

    //getter
    public AddressAttributeGroup getAddress(int addressIndex) {
        return addressList.get(addressIndex);
    }

    //setter
    public void setAddress(int addressIndex, AddressAttributeGroup address) {
        addressList.set(addressIndex, address);
    }

    public void removeAddress(int addressIndex) {
        addressList.remove(addressIndex);
    }
}

    //i forget why exactly we use this as like an object collector
    class AddressAttributeGroup {
        public String first;
        public String last;
        public String address;
        public String town;
        public String zip;
        public String state;
        public long id;

        //constructor 1
        public AddressAttributeGroup(String first, String last, String address, String town,
                                     String zip, String state){
            this.first = first;
            this.last = last;
            this.address = address;
            this.town = town;
            this.zip = zip;
            this.state = state;
        }

        //constructor 2
        public AddressAttributeGroup(int id, String first, String last, String address, String town,
                                     String zip, String state){

            this.id = id;
            this.first = first;
            this.last = last;
            this.address = address;
            this.town = town;
            this.zip = zip;
            this.state = state;
        }
    }

