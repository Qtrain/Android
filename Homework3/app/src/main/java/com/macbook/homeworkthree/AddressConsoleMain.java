package com.macbook.homeworkthree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddressConsoleMain extends AppCompatActivity implements View.OnClickListener {

    //added with toasts - don't really understand
    TextView textAddressMessage;
    //added as part of step 4 in onclick/ don't understand
    final int ADDRESS_ENTRY = 1001;
    //Step 1 wire up some xml elements
    EditText editRecordNumber;
    Button cmdAdd;
    Button cmdView;
    Button cmdEdit;
    Button cmdDelete;

    //step 2 declare array
    AddressCollection mAddresses = new AddressCollection();

    //step 5 add toasts
    void showError(Exception message) {
        Toast.makeText(this, message.getMessage(), Toast.LENGTH_LONG).show();
    }

    void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    void showInfo(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    void showAddressMessage(AddressAttributeObjects address) {
        textAddressMessage.setText("Address: " + " " + address.first + address.last + address.address + address.town + address.state + address.zip );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_console_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Step 1 wire up, set listeners, add on click method
        editRecordNumber = (EditText) findViewById(R.id.editRecordNumber);
        cmdAdd = (Button)findViewById(R.id.cmdAdd);
        cmdAdd.setOnClickListener(this);
        cmdView = (Button)findViewById(R.id.cmdView);
        cmdView.setOnClickListener(this);
        cmdEdit = (Button)findViewById(R.id.cmdEdit);
        cmdEdit.setOnClickListener(this);
        cmdDelete = (Button)findViewById(R.id.cmdDelete);
        cmdDelete.setOnClickListener(this);

        textAddressMessage = (TextView) findViewById(R.id.textAddressMessage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_address_console_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //step 4 add handling for onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        AddressStrongTypeIntent addressIntent = new AddressStrongTypeIntent(data);
        if (requestCode == ADDRESS_ENTRY) {
            try {
                switch (resultCode) {
                    case RESULT_OK:
                        AddressAttributeObjects address = new AddressAttributeObjects(addressIntent.first, addressIntent.address, addressIntent.last, addressIntent.town, addressIntent.state, addressIntent.zip);
                        switch (addressIntent.action) {
                            case ADD:
                                mAddresses.addAddress(address);
                                showAddressMessage(address);
                                showInfo("Added");
                                break;
                            case DELETE:
                                mAddresses.removeAddress(addressIntent.addressIndex);
                                showInfo("Deleted");
                                break;
                            case EDIT:
                                mAddresses.setAddress(addressIntent.addressIndex, address);
                                showAddressMessage(address);
                                showInfo("Updated");
                                break;
                        }
                        break;
                    case RESULT_CANCELED:
                        showInfo("Cancelled");
                        break;
                }
            } catch (Exception ex) {
                showError("here" + ex);

            }
        }
    }
    //added in step 1
    public void onClick(View v) {
        AddressStrongTypeIntent i;
        if (cmdAdd.getId() == v.getId()) {
            if (!mAddresses.isAddressLimitReached()) {
                i = new AddressStrongTypeIntent();
                i.action = AddressStrongTypeIntent.ActionType.ADD;
                startActivityForResult(i.getIntent(this, AddressEntry.class), ADDRESS_ENTRY);
            } else
                showError("Max mAddresses reached");
        } else {
            try {
                int addressIndex = Integer.parseInt(editRecordNumber.getText().toString());

                AddressAttributeObjects address = mAddresses.getAddress(addressIndex);
                if (cmdEdit.getId() == v.getId()) {
                    i = new AddressStrongTypeIntent(address, AddressStrongTypeIntent.ActionType.EDIT, addressIndex);
                    startActivityForResult(i.getIntent(this, AddressEntry.class), ADDRESS_ENTRY);
                }
                if (cmdDelete.getId() == v.getId()) {
                    i = new AddressStrongTypeIntent(address, AddressStrongTypeIntent.ActionType.DELETE, addressIndex);
                    startActivityForResult(i.getIntent(this, AddressEntry.class), ADDRESS_ENTRY);
                }
                if (cmdView.getId() == v.getId()) {
                    showAddressMessage(address);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                showError(ex);
            }
        }
    }
}
