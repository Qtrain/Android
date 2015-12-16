package com.quatrani.homework6;


import com.quatrani.homework6.AddressStrongTypeIntent.ActionType;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AddressConsole extends Activity implements View.OnClickListener, ListView.OnItemClickListener, ListView.OnItemSelectedListener {

    final int ADDRESS_ENTRY = 1001;
    TextView textAddressMessage;
    EditText editRecordNumber;
    Button cmdAdd;
    Button cmdEdit;
    Button cmdDelete;
    Button cmdView;
    ListView listAddresses;
    AddressCollection addresses;
    AddressDataSource addressData;
    AddressArrayAdapter addressAdapter;
    int recordNumber = -1;

    void showError(Exception message) {
        Toast.makeText(this, message.getMessage(), Toast.LENGTH_LONG).show();
    }

    void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    void showInfo(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    void showAddressMessage(AddressAttributeGroup address) {
        textAddressMessage.setText("Address: " + address.name + " " + address.address);
    }

    void clearAddressMessage() {
        textAddressMessage.setText("");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_console);
        textAddressMessage = (TextView) findViewById(R.id.textAddressMessage);
        editRecordNumber = (EditText) findViewById(R.id.editRecordNumber);
        ;
        cmdAdd = (Button) findViewById(R.id.cmdAdd);
        cmdAdd.setOnClickListener(this);
        cmdEdit = (Button) findViewById(R.id.cmdEdit);
        cmdEdit.setOnClickListener(this);
        cmdDelete = (Button) findViewById(R.id.cmdDelete);
        cmdDelete.setOnClickListener(this);
        cmdView = (Button) findViewById(R.id.cmdView);
        cmdView.setOnClickListener(this);

        listAddresses = (ListView) findViewById(R.id.listAddresses);
        listAddresses.setOnItemClickListener(this);
        listAddresses.setOnItemSelectedListener(this);

        addressData = new AddressDataSource(this);

        try {
            addressData.open();
            addresses = addressData.getAllAddresses();
            addressData.close();
        } catch (Exception e) {
            showError("Error loading address data.");
            addresses = new AddressCollection();
        }

        addressAdapter = new AddressArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                addresses);
        listAddresses.setAdapter(addressAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_address_message, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        AddressStrongTypeIntent addressIntent = new AddressStrongTypeIntent(data);
        if (requestCode == ADDRESS_ENTRY) {
            try {
                switch (resultCode) {
                    case RESULT_OK:
                        AddressAttributeGroup address = new AddressAttributeGroup(addressIntent.name, addressIntent.address, addressIntent.image);
                        switch (addressIntent.action) {
                            case ADD:
                                addressData.open();
                                address.id = addressData.createAddress(address);
                                addressData.close();
                                addresses.addAddress(address);
                                showAddressMessage(address);
                                showInfo("Added");
                                break;
                            case DELETE:
                                addressData.open();
                                addressData.deleteAddress(addresses.getAddress(addressIntent.addressIndex));
                                addressData.close();
                                addresses.removeAddress(addressIntent.addressIndex);
                                showInfo("Deleted");
                                clearAddressMessage();
                                editRecordNumber.setText("");
                                recordNumber = -1;
                                break;
                            case EDIT:
                                addressData.open();
                                addressData.deleteAddress(addresses.getAddress(addressIntent.addressIndex));
                                address.id = addressData.createAddress(address);
                                addressData.close();
                                addresses.setAddress(addressIntent.addressIndex, address);
                                showAddressMessage(address);
                                showInfo("Updated");
                                break;
                        }
                        addressAdapter.notifyDataSetChanged();


                        break;
                    case RESULT_CANCELED:
                        showInfo("Cancelled");
                        break;
                }
            } catch (Exception ex) {
                showError(ex);
                addressData.close();
            }
        }
    }

    public void onClick(View v) {
        AddressStrongTypeIntent i;
        if (cmdAdd.getId() == v.getId()) {
            if (!addresses.isAddressLimitReached()) {
                i = new AddressStrongTypeIntent();
                i.action = ActionType.ADD;
                startActivityForResult(i.getIntent(this, AddressEntry.class), ADDRESS_ENTRY);
            } else
                showError("Max addresses reached");
        } else {
            try {
                AddressAttributeGroup address = addresses.getAddress(recordNumber);
                if (cmdEdit.getId() == v.getId()) {
                    i = new AddressStrongTypeIntent(address, ActionType.EDIT, recordNumber);
                    startActivityForResult(i.getIntent(this, AddressEntry.class), ADDRESS_ENTRY);
                }
                if (cmdDelete.getId() == v.getId()) {
                    i = new AddressStrongTypeIntent(address, ActionType.DELETE, recordNumber);
                    startActivityForResult(i.getIntent(this, AddressEntry.class), ADDRESS_ENTRY);
                }
                if (cmdView.getId() == v.getId()) {
                    showAddressMessage(address);
                }
            } catch (Exception ex) {
                showError(ex);
            }
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        onItemSelected(parent, view, position, id);
        AddressAttributeGroup address = addresses.getAddress(recordNumber);
        showAddressMessage(address);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        recordNumber = position;
        editRecordNumber.setText(String.valueOf(recordNumber));

    }

    public void onNothingSelected(AdapterView<?> parent) {
    }

}

class AddressArrayAdapter extends ArrayAdapter<AddressAttributeGroup> {
    private final Context context;
    private final AddressCollection addresses;

    public AddressArrayAdapter(Context context, int resource,
                               int textViewResourceId, AddressCollection addresses) {
        super(context, resource, textViewResourceId, addresses.addressList);
        this.context = context;
        this.addresses = addresses;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AddressAttributeGroup address = addresses.getAddress(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.addressrowlayout, parent, false);
        TextView nameTextView = (TextView) rowView.findViewById(R.id.name);
        TextView addressTextView = (TextView) rowView.findViewById(R.id.address);
        nameTextView.setText(address.name);
        addressTextView.setText(address.address);

        return rowView;
    }


}

