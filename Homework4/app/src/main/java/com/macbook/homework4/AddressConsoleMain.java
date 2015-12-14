package com.macbook.homework4;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class AddressConsoleMain extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final int ADDRESS_ENTRY = 1001;//created this with startActivityForResutl in
    // onClick

    //XML widgets
    Button cmdAdd;
    Button cmdEdit;
    Button cmdView;
    Button cmdDelete;
    ListView mListAddresses;
    EditText mEditRecordNumber;
    TextView mTextAddressMessage;

    //Decleration from AddressCollectionClass
    AddressCollection mAddresses;

    //need to make addressDatabaseHelper class with address data source subclass
    AddressDataSource mAddressData;
    AddressArrayAdapter addressAdapter;
    int mRecordNumber = -1;


    /*******************************
     * toasts
     *********************************/

    void showErrorToast(Exception message) {
        Toast.makeText(this, message.getMessage(), Toast.LENGTH_LONG).show();
    }

    void showErrorToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    void showInfoToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    void showAddressMessageToast(AddressAttributeGroup address) {
        mTextAddressMessage.setText("Address: " + address.first + " " + address.last + " " +
                address.address + address.town + " " + address.state + " " + address.zip + " ");
    }

    void clearAddressMessage() {
        mTextAddressMessage.setText("");
    }

    /*************************************************************************/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_console_main);

        //wiring up widgets
        cmdAdd = (Button) findViewById(R.id.cmdAdd);
        cmdAdd.setOnClickListener(this);
        cmdEdit = (Button) findViewById(R.id.cmdEdit);
        cmdEdit.setOnClickListener(this);
        cmdView = (Button) findViewById(R.id.cmdView);
        cmdView.setOnClickListener(this);
        cmdDelete = (Button) findViewById(R.id.cmdDelete);
        cmdDelete.setOnClickListener(this);
        mTextAddressMessage = (TextView) findViewById(R.id.textAddressMessage);
        mEditRecordNumber = (EditText) findViewById(R.id.editRecordNumber);

        mListAddresses = (ListView) findViewById(R.id.listAddresses);
        mListAddresses.setOnItemClickListener(this);
        mListAddresses.setOnItemClickListener(this);//implements adapter view methods

        mAddressData = new AddressDataSource(this);

        try {
            mAddressData.open();
            mAddresses = mAddressData.getAllAddresses();
            mAddressData.close();
        } catch (Exception e) {
            showErrorToast("error loading data");
            mAddresses = new AddressCollection();
        }

        addressAdapter = new AddressArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                mAddresses);
        mListAddresses.setAdapter(addressAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        AddressStrongTypeIntent addressIntent = new AddressStrongTypeIntent(data);
        if (requestCode == ADDRESS_ENTRY) {
            try {
                switch (resultCode) {
                    case RESULT_OK:
                        AddressAttributeGroup address = new AddressAttributeGroup(addressIntent.mFirst,
                                addressIntent.mLast, addressIntent.mAddress, addressIntent.mTown,
                                addressIntent.mState, addressIntent.zip);
                        switch (addressIntent.action) {
                            case ADD:
                                mAddressData.open();
                                address.id = mAddressData.createAddress(address);
                                mAddressData.close();
                                mAddresses.addAddress(address);
                                showAddressMessageToast(address);
                                showInfoToast("Added");
                                break;
                            case DELETE:
                                mAddressData.open();
                                mAddressData.deleteAddress(mAddresses.getAddress(addressIntent.addressIndex));
                                mAddressData.close();
                                mAddresses.removeAddress(addressIntent.addressIndex);
                                showInfoToast("Deleted");
                                clearAddressMessage();
                                mEditRecordNumber.setText("");
                                mRecordNumber = -1;
                                break;
                            case EDIT:
                                mAddressData.open();
                                mAddressData.deleteAddress(mAddresses.getAddress(addressIntent.addressIndex));
                                address.id = mAddressData.createAddress(address);
                                mAddressData.close();
                                mAddresses.setAddress(addressIntent.addressIndex, address);
                                showAddressMessageToast(address);
                                showInfoToast("Updated");
                                break;
                        }
                        addressAdapter.notifyDataSetChanged();


                        break;
                    case RESULT_CANCELED:
                        showInfoToast("Cancelled");
                        break;
                }
            } catch (Exception ex) {
                showErrorToast(ex);
                mAddressData.close();
            }
        }
    }

    /*******************************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_address_message, menu);
        return true;
    }

    /********************************************************************/
    @Override
    protected void onStart() {
        super.onStart();

    }

    /********************************************************************/


    @Override
    public void onClick(View v) {
        AddressStrongTypeIntent i;


        if (cmdAdd.getId() == v.getId()) {
            if (!mAddresses.isAddressLimitReached()) {
                i = new AddressStrongTypeIntent();
                i.action = AddressStrongTypeIntent.ActionType.ADD; //more explicit nice
                //added addresss entry constant - some more reflection tom-foolery
                startActivityForResult(i.getmIntent(this, AddressEntry.class), ADDRESS_ENTRY);
            } else
                showErrorToast("max addresses reached");
        } else {
            try {
                AddressAttributeGroup address = mAddresses.getAddress(mRecordNumber);
                if (cmdEdit.getId() == v.getId()) {
                    i = new AddressStrongTypeIntent(address, AddressStrongTypeIntent
                            .ActionType.EDIT, mRecordNumber);
                    startActivityForResult(i.getmIntent(this, AddressEntry.class), ADDRESS_ENTRY);
                }
                if (cmdDelete.getId() == v.getId()) {
                    i = new AddressStrongTypeIntent(address, AddressStrongTypeIntent
                            .ActionType.DELETE, mRecordNumber);
                    startActivityForResult(i.getmIntent(this, AddressEntry.class), ADDRESS_ENTRY);
                }
                if (cmdView.getId() == v.getId()) {
                    showAddressMessageToast(address);
                }
            } catch (Exception ex) {
                showErrorToast(ex);
            }
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        onItemSelected(parent, view, position, id);
        AddressAttributeGroup address = mAddresses.getAddress(mRecordNumber);
        showAddressMessageToast(address);

    }

    /*****************************************************************************/
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mRecordNumber = position;
        mEditRecordNumber.setText(String.valueOf(mRecordNumber));

    }

    /*********************************************************************************/
    public void onNothingSelected(AdapterView<?> parent) {
    }


    /**************************
     * Adapter Class
     ***************************************/
    class AddressArrayAdapter extends ArrayAdapter<AddressAttributeGroup> {
        private final Context context;
        private final AddressCollection addresses;

        //constructer
        public AddressArrayAdapter(Context context, int resource,
                                   int textViewResourceId, AddressCollection addresses) {
            super(context, resource, textViewResourceId, addresses.addressList);
            this.context = context;
            this.addresses = addresses;

        }

        /**************************************************************************/
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AddressAttributeGroup address = addresses.getAddress(position);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.addressrowlayout, parent, false);
            TextView nameTextView = (TextView) rowView.findViewById(R.id.editFirst);
//            TextView addressTextView = (TextView) rowView.findViewById(R.id.address);
//            nameTextView.setText(address.first);
//            nameTextView.setText(address.last);
//            nameTextView.setText(address.address);
//            nameTextView.setText(address.town);
//            nameTextView.setText(address.state);
//            nameTextView.setText(address.zip);

            return rowView;
        }


    }

}
