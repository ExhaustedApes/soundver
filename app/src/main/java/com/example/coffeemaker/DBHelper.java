package com.example.coffeemaker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "usersExp.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "UserExp";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EXP = "Exp";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " TEXT PRIMARY KEY, " +
                COLUMN_EXP + " INTEGER);");

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, "susie0275@naver.com");
        values.put(COLUMN_EXP, 0);
        db.insert(TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean updateUserExp(String userId, int newExp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Exp", newExp);
        int rowsAffected = db.update("UserExp", values, "id = ?", new String[]{userId});
        db.close();
        return rowsAffected > 0;
    }

    public int getUserExp(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("UserExp", new String[]{"Exp"}, "id = ?", new String[]{userId}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int exp = cursor.getInt(cursor.getColumnIndexOrThrow("Exp"));
            cursor.close();
            db.close();
            return exp;
        }
        db.close();
        return -1;
    }


}