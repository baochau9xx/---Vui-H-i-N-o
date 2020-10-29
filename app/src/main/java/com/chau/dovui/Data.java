package com.chau.dovui;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Data extends SQLiteOpenHelper {

    public Data(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void AddData(String query)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
    }

    public Cursor GetData(String query)
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
}
