package com.macbook.program1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainAddressForm extends AppCompatActivity implements View.OnClickListener {

    //declare objects from XML
    private Button cmdOK;
    private Button cmdClear;
    private EditText editFirst;
    private EditText editLast;
    private EditText editAddress;
    private EditText editTown;
    private EditText editState;
    private EditText editZip;


    //corresponding primitive for each input field/assigned object
    private String first;
    private String last;
    private String address;
    private String town;
    private String state;
    private String zip;


    //onCreate is like main
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_address_form);
        editFirst = (EditText) findViewById(R.id.editFirst);
        editLast = (EditText) findViewById(R.id.editLast);
        editAddress = (EditText) findViewById(R.id.editAddress);
        editTown = (EditText) findViewById(R.id.editTown);
        editState = (EditText) findViewById(R.id.editState);
        editZip = (EditText) findViewById(R.id.editZip);

        //buttons
        cmdOK = (Button) findViewById(R.id.cmdOK);
        cmdClear = (Button) findViewById(R.id.cmdClear);

        //actions
        cmdOK.setOnClickListener(this);
        cmdClear.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (cmdOK.getId() == view.getId()) {
            first = editFirst.getText().toString();
            last = editLast.getText().toString();
            address = editAddress.getText().toString();
            town = editTown.getText().toString();
            state = editState.getText().toString();
            zip = editZip.getText().toString();
            Toast.makeText(getApplicationContext(), "Hi " + first + " " + last + "" + address + " " + town + " " + state + " " + zip + ".", Toast.LENGTH_SHORT).show();
        }
        if (cmdClear.getId() == view.getId()) {
            editFirst.getText().clear();
            editLast.getText().clear();
            editAddress.getText().clear();
            editTown.getText().clear();
            editState.getText().clear();
            editZip.getText().clear();
        }

    }




}



