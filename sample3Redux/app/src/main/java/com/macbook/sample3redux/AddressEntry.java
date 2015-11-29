package com.macbook.sample3redux;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This class is the main activity for the program.  The convention will be to create like a stack.  Methods that are created later will be placed above the last entry in the file.
 *
 */

public class AddressEntry extends AppCompatActivity implements View.OnClickListener {

    /**** step 1 create instance variables ****/
    EditText editName;
    EditText editAddress;
    Button cmdOk;
    Button cmdClear;

    /**** Step 3 implemented on click method/pause create Intent - unpause ****/

    @Override
    public void onClick(View v) {
        if(cmdOk.getId() == v.getId()){

            /**** Step 3.1 ****/
            AddressStrongTypeIntent i = new AddressStrongTypeIntent();
            i.name = editName.getText().toString();
            i.address = editAddress.getText().toString();
            startActivity(i.getIntent(this, AddressMessage.class));
        }
        /**** Step 3.2 ****/
        if(cmdClear.getId() == v.getId()){
            editName.setText("");
            editAddress.setText("");
            Toast.makeText(getApplicationContext(), "Cleared..", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        /**** step 2 ****/
        editName = (EditText)findViewById(R.id.editName);
        editAddress = (EditText)findViewById(R.id.editAddress);
        cmdOk = (Button)findViewById(R.id.cmdOK);
        cmdOk.setOnClickListener(this);
        cmdClear = (Button)findViewById(R.id.cmdClear);
        cmdClear.setOnClickListener(this);

    }

    /*******************************************************************/
    //not really using these methods

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_address_entry, menu);
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


}
