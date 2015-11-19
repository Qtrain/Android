package com.macbook.homework2;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainAddressForm extends AppCompatActivity implements View.OnClickListener, Runnable {

    //Button objects from xml
    private Button cmdOK;
    private Button cmdClear;
    private Button cmdRead;

    //EditText Objects
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

    //booleans used in threads;
    private boolean isSave = false;


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
        cmdRead = (Button) findViewById(R.id.cmdRead);

        //actions
        cmdOK.setOnClickListener(this);
        cmdClear.setOnClickListener(this);
        cmdRead.setOnClickListener(this);
    }

    /*****************************************************************/
    public void onClick(View view) {
        //save statement
        if (cmdOK.getId() == view.getId()) {
            first = editFirst.getText().toString();
            last = editLast.getText().toString();
            address = editAddress.getText().toString();
            town = editTown.getText().toString();
            state = editState.getText().toString();
            zip = editZip.getText().toString();
            save();
            Toast.makeText(getApplicationContext(), "Hi " + first + " " + last + "" + address + " " + town + " " + state + " " + zip + ".", Toast.LENGTH_SHORT).show();
        }

        //read statement
        if (cmdRead.getId() == view.getId()) {
            read();
            editFirst.setText(first);
            editLast.setText(last);
            editAddress.setText(address);
            editTown.setText(town);
            editZip.setText(zip);
            Toast.makeText(getApplicationContext(), "Got Address...", Toast.LENGTH_SHORT).show();
        }

        //clear statement
        if (cmdClear.getId() == view.getId()) {
            editFirst.getText().clear();
            editLast.getText().clear();
            editAddress.getText().clear();
            editTown.getText().clear();
            editState.getText().clear();
            editZip.getText().clear();
            Toast.makeText(getApplicationContext(), "Cleared...", Toast.LENGTH_SHORT).show();
        }
    }

    /**********************************************************************/
    public void saveAddress() {

        try {
            OutputStreamWriter writer = new OutputStreamWriter(openFileOutput("addressfile", Context.MODE_PRIVATE));

            writer.append(first);
            writer.append("\r\n");
            writer.append(last);
            writer.append("\r\n");
            writer.append(address);
            writer.append("\r\n");
            writer.append(town);
            writer.append("\r\n");
            writer.append(zip);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**********************************************************************************/
    void readAddress() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("addressfile")));
            first = br.readLine();
            last = br.readLine();
            address = br.readLine();
            town = br.readLine();
            zip = br.readLine();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //thread
    public void save() {
        isSave = true;
        Thread thread = new Thread(this);
        thread.start();
    }

    //thread
    public void read() {
        isSave = false;
        Thread thread = new Thread(this);
        thread.start();

    }


    @Override
    public void run() {
        if (isSave) {
            saveAddress();
        } else readAddress();
    }
}



