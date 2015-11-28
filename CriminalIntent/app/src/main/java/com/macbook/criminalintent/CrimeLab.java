package com.macbook.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * p168
 * Crime lab class is part of the model layer
 * centralized data stash for crime objects
 * singleton class
 */
public class CrimeLab {
    //private instances
    private static CrimeLab sCrimeLab;
    private final ArrayList<Crime> mCrimes;
    private Context mAppContext;

    //private constructor
    private CrimeLab(Context applicationContext) {
        mAppContext = applicationContext;
        mCrimes = new ArrayList<>();
        for(int i=0; i<100; i++){
            Crime c = new Crime();
            c.setmTitle("Crime number " + i);
            c.setmSolved( i%2 ==0 );
            mCrimes.add(c);
        }
    }

    //wrapper class providing access to singleton
    public static CrimeLab get(Context c){
        if(sCrimeLab == null){
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }

    public ArrayList<Crime> getmCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id){
        //for each through arraylist
        for(Crime c : mCrimes){
            if(c.getmID().equals(id))
                return c;
        }
        return null;
    }


}
