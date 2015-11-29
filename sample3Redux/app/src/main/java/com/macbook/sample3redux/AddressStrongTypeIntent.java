package com.macbook.sample3redux;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by macbook on 11/28/15.
 */
public class AddressStrongTypeIntent {

    /**** step 1 create instance variables ****/
    public String name;
    public String address;
    Intent intent;

    /**** step 3 - pause create AddressMessage class ****/
    public Intent getIntent(AddressEntry addressEntry, Class<AddressMessage> class1){

        /** step 3.1 unpause finish get method **/
        if(intent == null){
            intent = new Intent(addressEntry, class1);
            intent.putExtra("name", name);
            intent.putExtra("address", address);
        }
        return intent;
    }

    /**** step 2 create constructors ****/
    //constr. 2
    public AddressStrongTypeIntent(Intent intent){
        Bundle bundle = intent.getExtras();
        name = bundle.getString("name");
        address = bundle.getString("address");
    }

    //constr. 1
    public AddressStrongTypeIntent(){
        name = "";
        address = "";
    }
}
