package com.macbook.criminalintent;

import android.widget.Button;
import android.widget.CheckBox;

import java.util.Date;
import java.util.UUID;

/**
 * model layer (p 132)
 */
public class Crime {

    //(p133) declaring and generating g/s
    private UUID mID;
    private String mTitle;
    //(p149 adding more fields)
    private Date mDate; //the date the time occured
    private boolean mSolved; //whether the crime has been solved

    //constructor
    public Crime() {
        //Generate unique identifier
        mID = UUID.randomUUID();
        mDate = new Date();
    }


    //p149 getter/setter mSolved
    public boolean ismSolved() {
        return mSolved;
    }

    public void setmSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    //p 149 g/s Date
    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    //p133 getters and setters
    public UUID getmID() {
        return mID;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }


}
