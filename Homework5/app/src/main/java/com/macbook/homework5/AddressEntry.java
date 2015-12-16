package com.macbook.homework5;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AddressEntry extends Activity implements View.OnClickListener {

    Button cmdSave;
    Button cmdClear;
    Button cmdCancel;
    EditText mEditFirst;
    EditText mEditLast;
    EditText mEditAddress;
    EditText mEditTown;
    EditText mEditState;
    EditText mEditZip;
    int mResult;
    AddressStrongTypeIntent mSTIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_entry);

        mEditFirst = (EditText) findViewById(R.id.editFirst);
        mEditLast = (EditText) findViewById(R.id.editLast);
        mEditAddress = (EditText) findViewById(R.id.editAddress);
        mEditTown = (EditText) findViewById(R.id.editTown);
        mEditState = (EditText) findViewById(R.id.editState);
        mEditZip = (EditText) findViewById(R.id.editZip);
        cmdSave = (Button) findViewById(R.id.cmdSave);
        cmdSave.setOnClickListener(this);
        cmdClear = (Button) findViewById(R.id.cmdClear);
        cmdClear.setOnClickListener(this);
        cmdCancel = (Button) findViewById(R.id.cmdCancel);
        cmdCancel.setOnClickListener(this);
        mSTIntent = new AddressStrongTypeIntent(getIntent());
        mEditFirst.setText(mSTIntent.mFirst);
        mEditLast.setText(mSTIntent.mLast);
        mEditAddress.setText(mSTIntent.mAddress);
        mEditState.setText(mSTIntent.mState);
        mEditTown.setText(mSTIntent.mTown);
        mEditZip.setText(mSTIntent.mZip);

        if (mSTIntent.mAction == AddressStrongTypeIntent.ActionType.DELETE)
            cmdSave.setText(R.string.delete);
        mEditFirst.setEnabled(mSTIntent.mAction != AddressStrongTypeIntent.ActionType.DELETE);
        mEditLast.setEnabled(mSTIntent.mAction != AddressStrongTypeIntent.ActionType.DELETE);
        mEditAddress.setEnabled(mSTIntent.mAction != AddressStrongTypeIntent.ActionType.DELETE);
        mEditTown.setEnabled(mSTIntent.mAction != AddressStrongTypeIntent.ActionType.DELETE);
        mEditState.setEnabled(mSTIntent.mAction != AddressStrongTypeIntent.ActionType.DELETE);
        mEditZip.setEnabled(mSTIntent.mAction != AddressStrongTypeIntent.ActionType.DELETE);

        cmdClear.setEnabled(mSTIntent.mAction != AddressStrongTypeIntent.ActionType.DELETE);
    }


    @Override
    public void finish() {
        mSTIntent.clearIntent();
        mSTIntent.mFirst = mEditFirst.getText().toString();
        mSTIntent.mLast = mEditLast.getText().toString();
        mSTIntent.mAddress = mEditAddress.getText().toString();
        mSTIntent.mTown = mEditTown.getText().toString();
        mSTIntent.mState = mEditState.getText().toString();
        mSTIntent.mZip = mEditZip.getText().toString();

        setResult(mResult, mSTIntent.getmIntent());
        super.finish();
    }


    //implemented b/c of this parameter in onClick listener
    @Override
    public void onClick(View v) {
        if (cmdSave.getId() == v.getId()) {
            mResult = RESULT_OK;
            finish();

        }
        if (cmdClear.getId() == v.getId()) {
            mEditFirst.setText("");
            mEditLast.setText("");
            mEditAddress.setText("");
            mEditState.setText("");
            mEditTown.setText("");
            mEditZip.setText("");
        }

        if (cmdCancel.getId() == v.getId()) {
            mResult = RESULT_CANCELED;
            finish();
        }
    }
}

