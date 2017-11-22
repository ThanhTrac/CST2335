package com.example.thanh.androidlab;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import static com.example.thanh.androidlab.StartActivity.ACTIVITY_NAME;

/**
 * Created by thanh on 2017-10-10.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Messages.db" ;
    public static final String TABLE_NAME = "MESSAGE_TABLE" ;
    private static final int VERSION_NUM = 5 ;

    public static final  String KEY_ID = "_id";
    public static final  String KEY_MESSAGE ="Message" ;

    public static final String [] messageColumns = new String[]{KEY_ID, KEY_MESSAGE};

    private static final String CREATE_TABLE_MESSAGE = "CREATE TABLE "  + TABLE_NAME  +
            " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_MESSAGE + " TEXT); ";


    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    public void onCreate (SQLiteDatabase db) {
        // The table MESSAGE_TABLE is created,
        // and the column _id is defined as an integer primary key that will autoincrement.
        db.execSQL(CREATE_TABLE_MESSAGE);
        Log.i("ChatDatabaseHelper", "Calling onCreate");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVer + " newVersion=" + newVer);
    }


}
