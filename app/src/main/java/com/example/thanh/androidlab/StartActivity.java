package com.example.thanh.androidlab;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {
    protected static final String ACTIVITY_NAME = "StartActivity";

    Button btn1;
    Button btnChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toast.makeText(this, "onCreate", Toast.LENGTH_LONG ).show();
        Log.i(ACTIVITY_NAME, "In onCreate()");
        btn1=(Button)findViewById(R.id.btn1);
        btnChart=(Button)findViewById(R.id.btnChart);
        btnChart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
             Log.i(ACTIVITY_NAME, "User clicked Start Chat");
                Intent intent = new Intent(StartActivity.this, ChatWindow.class);
                startActivity(intent);
            }
        });

    }
    public void clickHandler(View view) {
        Intent intent = new Intent(this, ListItemActivity.class);
        startActivityForResult(intent, 10);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 10) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
                Toast.makeText(this, "ListItemsActivity passed: My information to share", Toast.LENGTH_LONG ).show();
                Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
                String messagePassed = data.getStringExtra("Response");
            }
        }
    }

            @Override
    protected void onStart(){
        super.onStart();
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT  ).show();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT  ).show();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT  ).show();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT  ).show();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT  ).show();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }


}
