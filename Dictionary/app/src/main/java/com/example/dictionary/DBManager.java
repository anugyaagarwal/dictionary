package com.example.dictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Reconnect on 14-07-2016.
 */
public class DBManager {
    DBOpenHelper dbOpenHelper;
    SQLiteDatabase sqLiteDatabase;

    public DBManager(Context context) {
        dbOpenHelper = new DBOpenHelper(context, Constant.DATABASE_NAME, null, Constant.DB_VERSION);
    }

    private void openDatabase() {
        sqLiteDatabase = dbOpenHelper.getWritableDatabase();
    }

    private void closeDatabase() {
        dbOpenHelper.close();
    }


    public ArrayList<WordBean> getBookmark() {
        openDatabase();
        ArrayList<WordBean> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + Constant.TABLE_BOOKMARK, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String part = cursor.getString(cursor.getColumnIndex(Constant.PART_OF_SPEECH));
                String word = cursor.getString(cursor.getColumnIndex(Constant.HEADWORD));
                String pronun = cursor.getString(cursor.getColumnIndex(Constant.AUDIO));
                String definition = cursor.getString(cursor.getColumnIndex(Constant.DEFINITION));
                WordBean wordBean = new WordBean();

                wordBean.setWord(word);
                wordBean.setPart_of_speech(part);
                wordBean.setAudio_url(pronun);
                wordBean.setDefinition(definition);
                arrayList.add(wordBean);
            } while (cursor.moveToNext());

        }
        return arrayList;
    }

    public ArrayList<WordBean> getHistory() {
        openDatabase();
        ArrayList<WordBean> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + Constant.TABLE_HISTORY, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String part = cursor.getString(cursor.getColumnIndex(Constant.PART_OF_SPEECH));
                String word = cursor.getString(cursor.getColumnIndex(Constant.HEADWORD));
                String pronun = cursor.getString(cursor.getColumnIndex(Constant.AUDIO));
                String definition = cursor.getString(cursor.getColumnIndex(Constant.DEFINITION));

                WordBean wordBean = new WordBean();

                wordBean.setWord(word);
                wordBean.setPart_of_speech(part);
                wordBean.setAudio_url(pronun);
                wordBean.setDefinition(definition);
                arrayList.add(wordBean);
            } while (cursor.moveToNext());

        }
        return arrayList;
    }


    public long setBookmark(WordBean wordBean) {
        openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.HEADWORD, wordBean.getWord());
        contentValues.put(Constant.PART_OF_SPEECH, wordBean.getPart_of_speech());
        contentValues.put(Constant.AUDIO, wordBean.getAudio_url());
        contentValues.put(Constant.DEFINITION, wordBean.getDefinition());
        long id = sqLiteDatabase.insert(Constant.TABLE_BOOKMARK, null, contentValues);
        closeDatabase();
        System.out.print(" Bookmark---------------------------------->>>>>>>");
        return id;

    }

    public long setHistory(WordBean wordBean) throws Exception {
        openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.HEADWORD, wordBean.getWord());
        contentValues.put(Constant.PART_OF_SPEECH, wordBean.getPart_of_speech());
        contentValues.put(Constant.AUDIO, wordBean.getAudio_url());
        contentValues.put(Constant.DEFINITION, wordBean.getDefinition());
        long id = sqLiteDatabase.insert(Constant.TABLE_HISTORY, null, contentValues);
        closeDatabase();
        return id;

    }

    public ArrayList<String> getBookmarkListSorted(String order) {
        openDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("Select " + Constant.HEADWORD + " from " + Constant.TABLE_BOOKMARK + " order by " + Constant.HEADWORD + " " + order, null);
        if (cursor != null && cursor.moveToFirst()) {

            do {
                arrayList.add(cursor.getString(cursor.getColumnIndex(Constant.HEADWORD)));
            } while (cursor.moveToNext());

        }
        return arrayList;
    }

    public ArrayList<String> getBookmarkList() {
        openDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("Select " + Constant.HEADWORD + " from " + Constant.TABLE_BOOKMARK, null);
        if (cursor != null && cursor.moveToFirst()) {

            do {
                arrayList.add(cursor.getString(cursor.getColumnIndex(Constant.HEADWORD)));
            } while (cursor.moveToNext());

        }
        return arrayList;
    }


    public ArrayList<String> getHistoryList() {
        openDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("Select " + Constant.HEADWORD + " from " + Constant.TABLE_HISTORY, null);
        if (cursor != null && cursor.moveToFirst()) {

            do {
                arrayList.add(cursor.getString(cursor.getColumnIndex(Constant.HEADWORD)));
            } while (cursor.moveToNext());

        }
        return arrayList;
    }

    public ArrayList<String> getHistoryListSorted(String order) {
        openDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("Select " + Constant.HEADWORD + " from " + Constant.TABLE_HISTORY + " order by " + Constant.HEADWORD + " " + order, null);
        if (cursor != null && cursor.moveToFirst()) {

            do {
                arrayList.add(cursor.getString(cursor.getColumnIndex(Constant.HEADWORD)));
            } while (cursor.moveToNext());

        }
        return arrayList;
    }

    public WordBean searchBookmark(String headword) {

        openDatabase();
        WordBean bean = new WordBean();
        Cursor cursor = sqLiteDatabase.rawQuery("Select *  from " + Constant.TABLE_BOOKMARK + " where " +
                Constant.HEADWORD + "='" + headword + "'", null);
        if (cursor != null && cursor.moveToFirst()) {

            String part = cursor.getString(cursor.getColumnIndex(Constant.PART_OF_SPEECH));
            String pronun = cursor.getString(cursor.getColumnIndex(Constant.AUDIO));
            String definition = cursor.getString(cursor.getColumnIndex(Constant.DEFINITION));
            bean.setDefinition(definition);
            bean.setAudio_url(pronun);
            bean.setPart_of_speech(part);
            bean.setWord(headword);
        }
        closeDatabase();
        return bean;
    }

    public WordBean searchHistory(String headword) {

        openDatabase();
        WordBean bean = new WordBean();
        Cursor cursor = sqLiteDatabase.rawQuery("Select *  from " + Constant.TABLE_HISTORY + " where " +
                Constant.HEADWORD + " ='" + headword + "'", null);
        if (cursor != null && cursor.moveToFirst()) {

            String part = cursor.getString(cursor.getColumnIndex(Constant.PART_OF_SPEECH));
            String pronun = cursor.getString(cursor.getColumnIndex(Constant.AUDIO));
            String definition = cursor.getString(cursor.getColumnIndex(Constant.DEFINITION));
            bean.setDefinition(definition);
            bean.setAudio_url(pronun);
            bean.setPart_of_speech(part);
            bean.setWord(headword);
        }
        closeDatabase();
        return bean;
    }


    public void removeBoomark(String word) {
        openDatabase();
        sqLiteDatabase.rawQuery("delete from " + Constant.TABLE_BOOKMARK + " where " + Constant.HEADWORD + " ='" + word + "'", null).moveToFirst();
        closeDatabase();
    }

    public void removeHistory(String word) {
        openDatabase();
        sqLiteDatabase.rawQuery("delete from " + Constant.TABLE_HISTORY + " where " + Constant.HEADWORD + " ='" + word + "'", null).moveToFirst();
        closeDatabase();
    }


    public void deleteHistory() {
        openDatabase();
        sqLiteDatabase.delete(Constant.TABLE_HISTORY, null, null);
        closeDatabase();
    }

    public void deleteBookmark(){
        openDatabase();
        sqLiteDatabase.delete(Constant.TABLE_BOOKMARK, null, null);
        closeDatabase();
    }


}
