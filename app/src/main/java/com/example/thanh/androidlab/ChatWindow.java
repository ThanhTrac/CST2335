package com.example.thanh.androidlab;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {

    EditText editText;
    Button btnSend;
    ListView listView;
    ArrayList<String > arrayList;
    ChatAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        editText=(EditText)findViewById(R.id.editText);
        btnSend= (Button)findViewById(R.id.btnSend);
        listView = (ListView)findViewById(R.id.listView);
        arrayList= new ArrayList<>();
        messageAdapter =new ChatAdapter( this );
        listView.setAdapter (messageAdapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                arrayList.add(editText.getText().toString());
                messageAdapter.notifyDataSetChanged();
                editText.setText("");
            }
        });

        ChatAdapter messageAdapter =new ChatAdapter( this );
        listView.setAdapter (messageAdapter);


    }
    private class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }
        public int getCount(){
            return arrayList.size();
        }
        public String getItem(int position){
            return arrayList.get(position);
        }
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null ;
            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(   getItem(position)  ); // get the string at position
            return result;

        }
    }

}
