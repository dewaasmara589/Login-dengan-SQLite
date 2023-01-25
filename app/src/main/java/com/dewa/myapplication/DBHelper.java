package com.dewa.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "LoginDBSQLite.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE session(id integer PRIMARY KEY, login text)");
        db.execSQL("CREATE TABLE users(id integer PRIMARY KEY AUTOINCREMENT, nik text, password text)");
        // kondisi cek sudah login atau belum
        db.execSQL("INSERT INTO session(id, login) VALUES(1, 'kosong')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS session");
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    // Method cek session
    public Boolean checkSession(String sessionValues) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM session WHERE login = ?", new String[]{sessionValues});
        if (cursor.getCount() > 0){
            return true;
        }else {
            return false;
        }
    }

    // Method update session
    public Boolean upgradeSession(String sessionValues, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("login", sessionValues);

        long update = db.update("session", contentValues, "id="+id, null);
        if (update == -1){
            return false;
        }else {
            return true;
        }
    }

    // Method insert user
    public Boolean insertUser(String nik, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nik", nik);
        contentValues.put("password", password);

        long insert = db.insert("users", null, contentValues);
        if (insert == -1){
            return false;
        }else {
            return true;
        }
    }

    // Method cek Login
    public Boolean checkLogin(String nik, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE nik = ? AND password = ?", new String[]{nik, password});
        if (cursor.getCount() > 0){
            return true;
        }else {
            return false;
        }
    }
}
