package com.example.finalexam.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.finalexam.adapter.LoginAdapter;



public class dbHelper extends SQLiteOpenHelper implements mHelper {
    public static final String TAG = dbHelper.class.getSimpleName();
    private SQLiteDatabase mDatabase;

    public dbHelper(Context context) {
        super(context, mHelper.DATABASE_NAME, null, mHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_USER = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT)" ,
                LoginAdapter.TABLE,
                LoginAdapter.Column.ID,
                LoginAdapter.Column.USERNAME,
                LoginAdapter.Column.PASSWORD
        );

        db.execSQL(CREATE_TABLE_USER);

        Log.i(TAG, CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_USER = "DROP TABLE IF EXISTS " + mHelper.DATABASE_VERSION;

        db.execSQL(DROP_USER);

        Log.i(TAG, DROP_USER);
        onCreate(mDatabase);
    }

    @Override
    public long registerUser(LoginAdapter user) {

        mDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LoginAdapter.Column.USERNAME, user.getUsername());
        values.put(LoginAdapter.Column.PASSWORD, user.getPassword());

        long result = mDatabase.insert(LoginAdapter.TABLE, null, values);
        mDatabase.close();

        return result;
    }

    @Override
    public LoginAdapter checkUserLogin(LoginAdapter user) {

        mDatabase = this.getReadableDatabase();

        Cursor cursor = mDatabase.query(LoginAdapter.TABLE,
                null,
                LoginAdapter.Column.USERNAME + " = ? AND " +
                        LoginAdapter.Column.PASSWORD + " = ?",
                new String[]{user.getUsername(), user.getPassword()},
                null,
                null,
                null);

        LoginAdapter currentUser = new LoginAdapter();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                currentUser.setId((int) cursor.getLong(0));
                currentUser.setUsername(cursor.getString(1));
                currentUser.setPassword(cursor.getString(2));
                mDatabase.close();
                return currentUser;
            }
        }

        return null;
    }


    }
