package com.macbook.homework5;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class AddressConsoleMain extends Activity implements View.OnClickListener, ListView.OnItemClickListener, ListView.OnItemSelectedListener {

    final int ADDRESS_ENTRY = 1001;
    TextView mTextAddressMessage;
    EditText mEditRecordNumber;
    Button cmdAdd;
    Button cmdEdit;
    Button cmdDelete;
    Button cmdView;
    ListView mListAddresses;

    //Address Collection and Address Data Source Classes
    AddressCollection mAddresses;
    AddressDataSource mAddressData;

    //Adapter
    AddressArrayAdapter mAddressAdapter;
    int mRecordNumber = -1;//why is this initialized to -1?

    //new
    boolean mServiceConnectionBound = false;
    /*****************************
     * Service Connection
     ***********************/
    //anonymous inner class
    private ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            AddressDataSource.ServiceBinder serviceBinder = (AddressDataSource.ServiceBinder) service;
            mAddressData = serviceBinder.getService();
            mAddressData.initDataSource(AddressConsoleMain.this);
            mServiceConnectionBound = true;
            try {
                mAddressData.open();
                mAddresses = mAddressData.getAllAddresses();
                mAddressData.close();

            } catch (Exception e) {
                showError("Error loading mAddress data.");
                mAddresses = new AddressCollection();
            }
            mAddressAdapter.mAddresses = mAddresses;
            mAddressAdapter.notifyDataSetChanged();
        }

        public void onServiceDisconnected(ComponentName arg0) {
            mServiceConnectionBound = false;
        }
    };

    /****************************
     * Toasts
     **********************************/
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
        mTextAddressMessage.setText("Address: " + address.mFirst + " " + address.mLast + " " + address.mAddress + " " + address.mTown + " " + address.mState + " " + address.mZip);
    }

    void clearAddressMessage() {
        mTextAddressMessage.setText("");
    }

    /*******************
     * on create method
     ****************************************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_console_main);

        mTextAddressMessage = (TextView) findViewById(R.id.textAddressMessage);
        mEditRecordNumber = (EditText) findViewById(R.id.editRecordNumber);
        cmdAdd = (Button) findViewById(R.id.cmdAdd);
        cmdAdd.setOnClickListener(this);
        cmdEdit = (Button) findViewById(R.id.cmdEdit);
        cmdEdit.setOnClickListener(this);
        cmdDelete = (Button) findViewById(R.id.cmdDelete);
        cmdDelete.setOnClickListener(this);
        cmdView = (Button) findViewById(R.id.cmdView);
        cmdView.setOnClickListener(this);

        mListAddresses = (ListView) findViewById(R.id.listAddresses);
        mListAddresses.setOnItemClickListener(this);
        mListAddresses.setOnItemSelectedListener(this);
    }

    /*****
     * methods implemented automatically from method signature
     ****************/
    @Override
    public void onClick(View v) {
        AddressStrongTypeIntent i;
        if (cmdAdd.getId() == v.getId()) {
            if (!mAddresses.isAddressLimitReached()) {
                i = new AddressStrongTypeIntent();
                i.mAction = AddressStrongTypeIntent.ActionType.ADD;
                startActivityForResult(i.getIntent(this, AddressEntry.class), ADDRESS_ENTRY);
            } else
                showError("Max addresses reached");
        } else {
            try {
                String recordNumberText = mEditRecordNumber.getText().toString();
                mRecordNumber = Integer.parseInt(recordNumberText);
                AddressAttributeGroup address = mAddresses.getAddress(mRecordNumber);
                if (cmdEdit.getId() == v.getId()) {
                    i = new AddressStrongTypeIntent(address, AddressStrongTypeIntent.ActionType.EDIT, mRecordNumber);
                    startActivityForResult(i.getIntent(this, AddressEntry.class), ADDRESS_ENTRY);
                }
                if (cmdDelete.getId() == v.getId()) {
                    i = new AddressStrongTypeIntent(address, AddressStrongTypeIntent.ActionType.DELETE, mRecordNumber);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        onItemSelected(parent, view, position, id);
        AddressAttributeGroup address = mAddresses.getAddress(mRecordNumber);
        showAddressMessage(address);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mRecordNumber = position;
        mEditRecordNumber.setText(String.valueOf(mRecordNumber));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
/*****************************
 * end auto implement
 ***************************/

/*************************** Adapter *****************************************/

class AddressArrayAdapter extends ArrayAdapter<AddressAttributeGroup> {
    private final Context mContext;
    public AddressCollection mAddresses;

    public AddressArrayAdapter(Context mContext, int resource,
                               int textViewResourceId, AddressCollection addresses) {
        super(mContext, resource, textViewResourceId, addresses.mAddressList);
        this.mContext = mContext;
        this.mAddresses = addresses;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AddressAttributeGroup address = mAddresses.getAddress(position);

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.addressrowlayout, parent, false);
        TextView nameTextView = (TextView) rowView.findViewById(R.id.first);
        TextView addressTextView = (TextView) rowView.findViewById(R.id.address);
        nameTextView.setText(address.mFirst);
        addressTextView.setText(address.mAddress);

        return rowView;
    }



}


