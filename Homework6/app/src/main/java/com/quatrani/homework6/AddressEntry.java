package com.quatrani.homework6;

import java.io.File;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddressEntry extends Activity implements View.OnClickListener {
    int CAMERA_PIC_REQUEST = 2;

    Button cmdSave;
    Button cmdPhoto;
    Button cmdCancel;
    ImageView imagePhoto;
    EditText editName;
    EditText editAddress;
    int result;
    AddressStrongTypeIntent stIntent;

    String imagePath = "";

    void showInfo(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_entry);
        editName = (EditText) findViewById(R.id.editName);
        editAddress = (EditText) findViewById(R.id.editAddress);
        imagePhoto = (ImageView) findViewById(R.id.imagePhoto);
        cmdSave = (Button) findViewById(R.id.cmdSave);
        cmdSave.setOnClickListener(this);
        cmdPhoto = (Button) findViewById(R.id.cmdPhoto);
        cmdPhoto.setOnClickListener(this);
        cmdCancel = (Button) findViewById(R.id.cmdCancel);
        cmdCancel.setOnClickListener(this);
        stIntent = new AddressStrongTypeIntent(getIntent());
        editName.setText(stIntent.name);
        editAddress.setText(stIntent.address);
        imagePath = stIntent.image;
        if (stIntent.action == AddressStrongTypeIntent.ActionType.DELETE)
            cmdSave.setText(R.string.delete);
        editName.setEnabled(stIntent.action != AddressStrongTypeIntent.ActionType.DELETE);
        editAddress.setEnabled(stIntent.action != AddressStrongTypeIntent.ActionType.DELETE);
        loadImage();

    }

    void loadImage() {
        if (imagePath == null || imagePath.length() == 0)
            return;

        Bitmap imageBitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        imageBitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/DCIM/" + imagePath, options);
        imagePhoto.setImageBitmap(imageBitmap);
    }

    @Override
    public void finish() {
        stIntent.clearIntent();
        stIntent.name = editName.getText().toString();
        stIntent.address = editAddress.getText().toString();
        stIntent.image = imagePath;
        setResult(result, stIntent.getIntent());
        super.finish();
    }

    public void onClick(View v) {
        if (cmdSave.getId() == v.getId()) {
            result = RESULT_OK;
            finish();

        }
        if (cmdPhoto.getId() == v.getId()) {

            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            imagePath = "Address" + System.currentTimeMillis() + ".png";
            Uri uriSavedImage = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/DCIM", imagePath));
            cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uriSavedImage);
            startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
        }
        if (cmdCancel.getId() == v.getId()) {
            result = RESULT_CANCELED;
            finish();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_PIC_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                try { // imageBitmap= (Bitmap) data.getExtras().get("data");
                    loadImage();
                } catch (Exception e) {
                    showInfo("Picture not taken");
                    e.printStackTrace();
                    imagePath = "";
                }
            }
        } else {
            showInfo("Picture not taken");
        }

    }


}



