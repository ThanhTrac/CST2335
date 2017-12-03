package com.example.thanh.androidlab;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.icu.text.MessagePattern.ArgType.SELECT;
import static com.example.thanh.androidlab.ChatDatabaseHelper.KEY_ID;
import static com.example.thanh.androidlab.ChatDatabaseHelper.KEY_MESSAGE;
import static com.example.thanh.androidlab.StartActivity.ACTIVITY_NAME;

public class ChatWindow extends Activity {

    EditText editText;
    Button btnSend;
    ListView listView;
    ArrayList<String > arrayList;
    ChatAdapter messageAdapter;
    SQLiteDatabase sqlDB;
    ChatDatabaseHelper chatDbHelper;



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

        chatDbHelper=new ChatDatabaseHelper(this);
        sqlDB = chatDbHelper.getWritableDatabase();




        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                ContentValues newData=new ContentValues();
                //editText.getText().toString();
                newData.put(ChatDatabaseHelper.KEY_MESSAGE, (editText.getText().toString()));

                sqlDB.insert(ChatDatabaseHelper.TABLE_NAME, null, newData);


                Cursor cursor = sqlDB.query(ChatDatabaseHelper.TABLE_NAME, ChatDatabaseHelper.messageColumns,
                        ChatDatabaseHelper.KEY_MESSAGE, null , null, null, null);
                //   Cursor cursor=sqlDB.rawQuery("SELECT * FROM MESSAGE_TABLE ", ChatDatabaseHelper.messageColumns);
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        int c = cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE);
                        Log.i("column index  ", c + " \n");
                        Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
                        Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + cursor.getColumnCount());
                        cursor.moveToNext();
                    }
                    cursor.close();
                }
//                for (int i =0; i<cursor.getColumnCount(); i++ ){
//                    cursor.getColumnName(i+1);
//                }
                arrayList.add(editText.getText().toString());

                messageAdapter.notifyDataSetChanged();
                editText.setText("");
            }
        });


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
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT  ).show();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }



}
