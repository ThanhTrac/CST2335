package com.example.thanh.androidlab;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MessageDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        Bundle bundle = new Bundle();
        bundle.putString("Message", getIntent().getStringExtra("Message"));
        bundle.putString("MessageID", getIntent().getStringExtra("MessageID"));

        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(bundle);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.phone_frameLayout, fragment);
        ft.commit();
    }
}
