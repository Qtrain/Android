package com.macbook.sample3redux;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddressMessage extends AppCompatActivity implements View.OnClickListener {

    /**** Step Bonus create Instance variable for back button ****/
    Button cmdBack;

    /**** bonus on click method calls finish() to go back to previous activity ****/
    @Override
    public void onClick(View v) {
        if(cmdBack.getId() == v.getId()){
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_message);
        /** from bonus round **/
        cmdBack = (Button) findViewById(R.id.cmdBack);
        cmdBack.setOnClickListener(this);

        /*************** automatic toolbar code *********************/
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
        /**************************************************************/

        /**** step 1 (coming from strong type intent) ****/
        AddressStrongTypeIntent i = new AddressStrongTypeIntent(getIntent());
        TextView textAddressMessage = (TextView)findViewById(R.id.textAddressMessage);
        textAddressMessage.setText("Alright " + i.name + " " + i.address);
    }



}
