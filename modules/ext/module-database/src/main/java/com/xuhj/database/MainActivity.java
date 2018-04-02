package com.xuhj.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.xuhj.database.db.MyDBHelper;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private MyDBHelper mDBHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void initData() {
        mDBHelper = new MyDBHelper(this);
    }

    private void dbInsert(String name) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put("id", 0);
        values.put("name", name);
        db.insert("user_info", "id", values);
        db.close();
    }

    private void dbUpdate(String name) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        db.update("user_info", values, "id=?", new String[]{"1"});
        db.close();
    }

    private void dbQuery() {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        Cursor cursor = db.query("user_info", null, null, null, null, null, "id desc");
        while (cursor.moveToNext()) {
            Log.d(TAG, "dbQuery: " + cursor.getString(cursor.getColumnIndex("name")));
        }
        cursor.close();
        db.close();
    }
}
