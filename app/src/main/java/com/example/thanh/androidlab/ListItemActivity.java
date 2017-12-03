package com.example.thanh.androidlab;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemActivity extends Activity {

    protected static final String ACTIVITY_NAME = "ListItemActivity";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageButton btnImage;
    CheckBox CBox;
    Switch switch1;
    boolean isSwitchOn;
    CharSequence text;
    int duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);
        Toast.makeText(this, "onCreate", Toast.LENGTH_LONG ).show();
        Log.i(ACTIVITY_NAME, "In onCreate()");
        btnImage=(ImageButton)findViewById(R.id.btnImage);
        switch1=(Switch)findViewById(R.id.switch1);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                isSwitchOn = isChecked;
                CharSequence text;
                int duration;

                if (isSwitchOn) {
                    text = "Switch is On";
                    duration = Toast.LENGTH_SHORT;
                } else {
                    text = "Switch is Off";
                    duration = Toast.LENGTH_LONG;
                }
                Toast.makeText(ListItemActivity.this , text, duration).show();

                }

            });


        CBox=(CheckBox)findViewById(R.id.CBox);
        CBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListItemActivity.this);
                builder.setMessage(R.string.dialog_message) //Add a dialog message to strings.xml

                        .setTitle(R.string.dialog_title)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent resultIntent = new Intent(  );
                                resultIntent.putExtra("Response", "Here is my response");
                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //finish();
                                /*Intent cancelIntent=new Intent(ListItemActivity.this, ListItemActivity.class);
                                startActivity(cancelIntent);*/
                                // User cancelled the dialog
                            }
                        })
                        .show();
            }
        });
    }
    public void onClick(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            btnImage.setImageBitmap(imageBitmap);
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        //Toast.makeText(this, "onStart", Toast.LENGTH_SHORT  ).show();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }
    @Override
    protected void onResume(){
        super.onResume();
        //Toast.makeText(this, "onResume", Toast.LENGTH_SHORT ).show();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }
    @Override
    protected void onPause(){
        super.onPause();
        //Toast.makeText(this, "onPause", Toast.LENGTH_SHORT ).show();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }
    @Override
    protected void onStop(){
        super.onStop();
        //Toast.makeText(this, "onStop", Toast.LENGTH_SHORT  ).show();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        //Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT ).show();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }


}
