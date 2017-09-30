package com.example.thanh.androidlab;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
    protected static final String ACTIVITY_NAME = "LoginActivity";

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String email = "emailKey";
    SharedPreferences sharedPref;


    Button btnLogin;
    EditText etEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toast.makeText(this, "onCreate", Toast.LENGTH_LONG ).show();
        Log.i(ACTIVITY_NAME, "In onCreate()");
        btnLogin=(Button)findViewById(R.id.btnLogin);
        etEmail=(EditText)findViewById(R.id.etEmail);

        sharedPref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        etEmail.setText(sharedPref.getString(email, "email@domain.com"));


    }

    public void Save(View view) {
        String e = etEmail.getText().toString();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(email, e);
        editor.commit();
        Intent intent = new Intent(LoginActivity.this, StartActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onStart(){
        super.onStart();
        Toast.makeText(this, "onStart", Toast.LENGTH_LONG ).show();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Toast.makeText(this, "onResume", Toast.LENGTH_LONG ).show();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Toast.makeText(this, "onPause", Toast.LENGTH_LONG ).show();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Toast.makeText(this, "onStop", Toast.LENGTH_LONG ).show();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Toast.makeText(this, "onDestroy", Toast.LENGTH_LONG ).show();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }


}
