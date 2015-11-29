package com.macbook.homeworkthree;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class AddressEntry extends AppCompatActivity implements View.OnClickListener {

    EditText editFirst, editLast, editAddress, editTown, editState, editZip;
    Button cmdOk, cmdClear, cmdCancel;
    int result;
    AddressStrongTypeIntent stIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_entry);


        //Step 2 wire up
        editFirst = (EditText)findViewById(R.id.editFirst);
        editLast = (EditText)findViewById(R.id.editLast);
        editAddress = (EditText)findViewById(R.id.editAddress);
        editTown = (EditText)findViewById(R.id.editTown);
        editState =(EditText)findViewById(R.id.editState);
        editZip = (EditText)findViewById(R.id.editZip);
        cmdOk = (Button)findViewById(R.id.cmdSave);
        cmdOk.setOnClickListener(this);
        cmdClear = (Button)findViewById(R.id.cmdClear);
        cmdClear.setOnClickListener(this);
        cmdCancel = (Button)findViewById(R.id.cmdCancel);
        cmdCancel.setOnClickListener(this);
        stIntent = new AddressStrongTypeIntent(getIntent());
        editFirst.setText(stIntent.first);
        editLast.setText(stIntent.last);
        editAddress.setText(stIntent.address);
        editTown.setText(stIntent.town);
        editState.setText(stIntent.state);
        editZip.setText(stIntent.zip);

        //step 4 need clarification
        if(stIntent.action == AddressStrongTypeIntent.ActionType.DELETE){
            cmdOk.setText(R.string.delete);
        }
        editFirst.setEnabled(stIntent.action != AddressStrongTypeIntent.ActionType.DELETE);
        editLast.setEnabled(stIntent.action != AddressStrongTypeIntent.ActionType.DELETE);
        editAddress.setEnabled(stIntent.action != AddressStrongTypeIntent.ActionType.DELETE);
        editTown.setEnabled(stIntent.action != AddressStrongTypeIntent.ActionType.DELETE);
        editState.setEnabled(stIntent.action != AddressStrongTypeIntent.ActionType.DELETE);
        editZip.setEnabled(stIntent.action != AddressStrongTypeIntent.ActionType.DELETE);
        cmdClear.setEnabled(stIntent.action != AddressStrongTypeIntent.ActionType.DELETE);
    }

    //step 5 add finish method
    public void finish(){
        stIntent.clearIntent();
        stIntent.first = editFirst.getText().toString();
        stIntent.last = editLast.getText().toString();
        stIntent.address = editAddress.getText().toString();
        stIntent.town = editTown.getText().toString();
        stIntent.state = editState.getText().toString();
        stIntent.zip = editZip.getText().toString();
        setResult(result, stIntent.getIntent());
        super.finish();
    }

    //step 6 on click
    @Override
    public void onClick(View v) {
        if(cmdOk.getId() == v.getId()){
            result = RESULT_OK;
            finish();
        }

        if(cmdClear.getId() == v.getId()){
            editFirst.setText("");
            editLast.setText("");
            editAddress.setText("");
            editTown.setText("");
            editState.setText("");
            editZip.setText("");
        }

        if(cmdCancel.getId() == v.getId()){
            result = RESULT_CANCELED;
            finish();
        }

    }
}
