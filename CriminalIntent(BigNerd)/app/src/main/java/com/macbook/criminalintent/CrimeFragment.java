package com.macbook.criminalintent;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

/**
 * crime fragment is a controller that interacts with model and view objects.  Its job is to present the details of a specific crime and update those details as the user changes them
 *
 * in geoQuz activites did most of their controller work in activitey lifecycle methods.
 * In CI this will be done by framents using fragment lifecycle methods
 */
public class CrimeFragment extends Fragment {
    //(p 140 add member variable)
    private Crime mCrime;
    //(p141 add EditText member variable)
    private EditText mTitleField;
    //(p 153 adding some button objects)
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;


    //(p 140 add on create)
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
    }

    //(p140 inflating the view)
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, parent, false);

        //(p141 wiring up EditText to respond to user input)
        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //(p141)
                mCrime.setmTitle(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //(p152 wiring widgets - date button)
        mDateButton= (Button) v.findViewById(R.id.crime_date);
        mDateButton.setText(mCrime.getmDate().toString());
        mDateButton.setEnabled(false);
        //(wiring up a widget member variable/cast/object get view
        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            //anonymous inner class
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                //  set the crimes solved property
                mCrime.setmSolved(isChecked);
            }
        });

        return v;
    }

}
