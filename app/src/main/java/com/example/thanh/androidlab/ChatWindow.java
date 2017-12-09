package com.example.thanh.androidlab;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;

import static com.example.thanh.androidlab.ChatDatabaseHelper.TABLE_NAME;
import static com.example.thanh.androidlab.ChatDatabaseHelper.messageColumns;


public class ChatWindow extends Activity {

    EditText editText;
    Button btnSend;
    ListView listView;
    ArrayList<String > arrayList;
    ChatAdapter messageAdapter;
    SQLiteDatabase sqlDB;
    ChatDatabaseHelper chatDbHelper;
    private String chatWindow = ChatWindow.class.getSimpleName();
    Cursor cursor;


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
        public long getItemId(int position){
            cursor.moveToPosition(position);
            return cursor.getLong(cursor.getColumnIndex(chatDbHelper.KEY_ID));
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        //setContentView(R.layout.activity_message_details);

        editText=(EditText)findViewById(R.id.editText);
        btnSend= (Button)findViewById(R.id.btnSend);
        listView = (ListView)findViewById(R.id.listView);
        arrayList= new ArrayList<String>();
        messageAdapter =new ChatAdapter( this );
        listView.setAdapter (messageAdapter);

        chatDbHelper=new ChatDatabaseHelper(this);
        sqlDB = chatDbHelper.getWritableDatabase();

        btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (editText.getText().toString().equals("") == false) {
                        arrayList.add(editText.getText().toString());

                        chatDbHelper.insertEntry(editText.getText().toString());
                        displaySQL();
                        editText.setText("");
                    }
                }
            });


        editText.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (keyCode) {
                            case KeyEvent.KEYCODE_DPAD_CENTER:
                            case KeyEvent.KEYCODE_ENTER:
                                arrayList.add(editText.getText().toString());

                                chatDbHelper.insertEntry(editText.getText().toString());
                                displaySQL();
                                editText.setText("");
                                return true;
                            default:
                                break;
                        }
                    }
                    return false;
                }
            });


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                    String item = adapterView.getItemAtPosition(position).toString();
                    Long messageId = adapterView.getItemIdAtPosition(position);

                        if(findViewById(R.id.frameLayout) != null) {
                        MessageFragment messageFragment = new MessageFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("Message", item);
                        bundle.putString("MessageID", messageId + "");
                        bundle.putBoolean("isTablet", true);
                        messageFragment.setArguments(bundle);
                        FragmentTransaction ft =  getFragmentManager().beginTransaction();
                        ft.replace(R.id.frameLayout, messageFragment);
                        //Call transaction.addToBackStack(String name) if you want to undo this transaction with the back button.
                        ft.addToBackStack("A string");
                        ft.commit();

                        Log.i(chatWindow, "Run on Tablet?");
                    } else {
                        Intent intent = new Intent(ChatWindow.this, MessageDetails.class);
                        intent.putExtra("Message", item);
                        intent.putExtra("MessageID", messageId + "");
                        startActivityForResult(intent, 1);

                        Log.i(chatWindow, "Run on Phone");
                    }

                }
            });
        }

    public void displaySQL() {
        arrayList.clear();
        cursor = chatDbHelper.getRecords();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Log.i(chatWindow, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));

                arrayList.add(cursor.getString(cursor.getColumnIndex(chatDbHelper.KEY_MESSAGE)));
                cursor.moveToNext();
            }
            messageAdapter.notifyDataSetChanged();//this restarts the process of getCount()/getView()
            Log.i(chatWindow, "Cursor's column count = " + cursor.getColumnCount());
        }

        for (int i = 0; i < cursor.getColumnCount(); i++) {
            Log.i(chatWindow, "Cursor's column name is " + (i + 1) + ". " + cursor.getColumnName(i));
        }
    }

    protected void onResume(){
        super.onResume();
        Log.i(chatWindow, "In onResume()");
    }

    protected void onStart(){
        super.onStart();
        chatDbHelper.openDatabase();
        displaySQL();

        Log.i(chatWindow,"In onStart()");
    }

    protected void onPause(){
        super.onPause();
        Log.i(chatWindow,"In onPause()");
    }

    protected void onStop(){
        super.onStop();
        Log.i(chatWindow,"In onStop()");
    }

    protected void onDestroy(){
        super.onDestroy();
        chatDbHelper.closeDatabase();
        Log.i(chatWindow,"In onDestroy()");
    }

    public void notifyChange() {
        listView.setAdapter(messageAdapter);
        sqlDB = chatDbHelper.getWritableDatabase();

        cursor = sqlDB.query(false, TABLE_NAME, messageColumns, null, null, null, null, null, null);
        int numColumns = cursor.getColumnCount();
        int numResults = cursor.getCount();

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Log.i(chatWindow, "SQL MESSAGE: " + cursor.getString(cursor.getColumnIndex(chatDbHelper.KEY_MESSAGE)));
            arrayList.add(cursor.getString(cursor.getColumnIndex(chatDbHelper.KEY_MESSAGE)));
            cursor.moveToNext();
        }
        Log.i(chatWindow, "Cursor's column count = " + numColumns);

        cursor.moveToFirst();
        for(int i = 0; i < numResults; i++) {
            Log.i(chatWindow, "The " + i + " row is " + cursor.getString(cursor.getColumnIndex(chatDbHelper.KEY_MESSAGE)));
            cursor.moveToNext();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            String id = data.getStringExtra("MessageID");
            chatDbHelper.deleteItem(id);
        }

    }
}
