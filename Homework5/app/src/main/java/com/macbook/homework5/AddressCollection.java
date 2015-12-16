package com.macbook.homework5;

import java.util.ArrayList;

/**
 * Created by macbook on 12/15/15.
 */
public class AddressCollection {

    final int MAX_ADDRESS_COUNT = 15;
    ArrayList<AddressAttributeGroup> mAddressList = new ArrayList<>();

    public boolean isAddressLimitReached() {
        return mAddressList.size() > MAX_ADDRESS_COUNT;
    }

    public int addAddress(AddressAttributeGroup address) throws Exception {
        if (isAddressLimitReached()) {
            throw (new Exception("Max Address Exception from addAddress() in addressCollection"));
        }
        mAddressList.add(address);
        return mAddressList.indexOf(address);
    }

    public void setAddress(int addressIndex, AddressAttributeGroup address) {
        mAddressList.set(addressIndex, address);
    }

    public void removeAddress(int addressIndex) {
        mAddressList.remove(addressIndex);
    }

    public AddressAttributeGroup getAddress(int addressIndex) {
        return mAddressList.get(addressIndex);
    }
}

class AddressAttributeGroup {
    public String mFirst;
    public String mLast;
    public String mAddress;
    public String mTown;
    public String mState;
    public String mZip;
    public long mId;

    public AddressAttributeGroup(String mFirst, String mLast, String mAddress, String mTown, String mState, String mZip) {
        this.mFirst = mFirst;
        this.mLast = mLast;
        this.mAddress = mAddress;
        this.mTown = mTown;
        this.mState = mState;
        this.mZip = mZip;
    }

    public AddressAttributeGroup(long mId, String mFirst, String mLast, String mAddress, String mTown, String mState, String mZip)  {
        this.mFirst = mFirst;
        this.mLast = mLast;
        this.mAddress = mAddress;
        this.mTown = mTown;
        this.mState = mState;
        this.mZip = mZip;
        this.mId = mId;
    }
}