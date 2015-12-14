package com.macbook.homework4;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddressEntry extends AppCompatActivity implements View.OnClickListener {

    //XML elements
    EditText mEditFirst;
    EditText mEditLast;
    EditText mEditAddress;
    EditText mEditTown;
    EditText mEditState;
    EditText mEditZip;
    Button cmdSave;
    Button cmdClear;
    Button cmdCancel;

    //AddressSTIntent object
    AddressStrongTypeIntent stIntent;


    //need to add int result
    //added int result first usage in finish method
    int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /************ auto generated *******************************/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        /************************************************************/
        //wiring up
        mEditFirst = (EditText)findViewById(R.id.editFirst);
        mEditLast = (EditText)findViewById(R.id.editLast);
        mEditAddress = (EditText)findViewById(R.id.editAddress);
        mEditTown = (EditText)findViewById(R.id.editTown);
        mEditState = (EditText)findViewById(R.id.editState);
        mEditZip = (EditText)findViewById(R.id.editZip);
        cmdSave = (Button)findViewById(R.id.cmdSave);
        cmdSave.setOnClickListener(this);
        cmdClear = (Button)findViewById(R.id.cmdClear);
        cmdClear.setOnClickListener(this);
        cmdCancel = (Button)findViewById(R.id.cmdCancel);
        cmdCancel.setOnClickListener(this);

        //intent
        stIntent = new AddressStrongTypeIntent(getIntent());
        mEditFirst.setText(stIntent.mFirst);
        mEditLast.setText(stIntent.mLast);
        mEditLast.setText(stIntent.mAddress);
        mEditLast.setText(stIntent.mTown);
        mEditLast.setText(stIntent.mState);
        mEditLast.setText(stIntent.zip);

        if(stIntent.action == AddressStrongTypeIntent.ActionType.DELETE){
            cmdSave.setText("Delete");
            mEditFirst.setEnabled(stIntent.action != AddressStrongTypeIntent.ActionType.DELETE);
            mEditLast.setEnabled(stIntent.action != AddressStrongTypeIntent.ActionType.DELETE);
            mEditAddress.setEnabled(stIntent.action != AddressStrongTypeIntent.ActionType.DELETE);
            mEditTown.setEnabled(stIntent.action != AddressStrongTypeIntent.ActionType.DELETE);
            mEditState.setEnabled(stIntent.action != AddressStrongTypeIntent.ActionType.DELETE);
            mEditZip.setEnabled(stIntent.action != AddressStrongTypeIntent.ActionType.DELETE);
            cmdClear.setEnabled(stIntent.action != AddressStrongTypeIntent.ActionType.DELETE);
        }
    }
    /*********************************************************/

    @Override //need clarification on this method
    public void finish(){
        stIntent.clearIntent();
        stIntent.mFirst = mEditFirst.getText().toString();
        stIntent.mLast = mEditLast.getText().toString();
        stIntent.mAddress = mEditAddress.getText().toString();
        stIntent.mTown = mEditTown.getText().toString();
        stIntent.mState = mEditState.getText().toString();
        stIntent.zip = mEditZip.getText().toString();
        setResult(result, stIntent.getmIntent());
        super.finish();
    }

    @Override
    public void onClick(View v) {
        //handle on click
        if(cmdSave.getId() == v.getId()){
            result = RESULT_OK; //constant built into android
            finish();
        }
        if(cmdClear.getId() == v.getId()){
            mEditFirst.setText("");
            mEditLast.setText("");
            mEditAddress.setText("");
            mEditTown.setText("");
            mEditState.setText("");
            mEditZip.setText("");
        }
        if(cmdCancel.getId() == v.getId()){
            result = RESULT_CANCELED; //android constant
            finish();
        }
    }
}
