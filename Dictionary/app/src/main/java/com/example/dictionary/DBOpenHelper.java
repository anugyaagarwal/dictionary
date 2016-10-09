package com.example.dictionary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBOpenHelper extends SQLiteOpenHelper {

    private final String CREATE_QUERY_HISTORY = "create table " + Constant.TABLE_HISTORY + "("
            + Constant.HEADWORD + " text," + Constant.PART_OF_SPEECH + " text," + Constant.AUDIO + " text,"
            + Constant.DEFINITION + " text, PRIMARY KEY("+Constant.HEADWORD+"));";
    private final String CREATE_QUERY_BOOKMARK = "create table " + Constant.TABLE_BOOKMARK + "("
            + Constant.HEADWORD + " text ," + Constant.PART_OF_SPEECH + " text," + Constant.AUDIO + " text,"
            + Constant.DEFINITION + " text, PRIMARY KEY("+Constant.HEADWORD+"));";


    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY_BOOKMARK);
        db.execSQL(CREATE_QUERY_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exist " + Constant.TABLE_HISTORY);
        db.execSQL("drop table if exist " + Constant.TABLE_BOOKMARK);
        db.execSQL(CREATE_QUERY_BOOKMARK);
        db.execSQL(CREATE_QUERY_HISTORY);
    }
}
