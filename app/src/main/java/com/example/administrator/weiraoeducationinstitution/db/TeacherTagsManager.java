package com.example.administrator.weiraoeducationinstitution.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/6.
 */
public class TeacherTagsManager {
    private static TeacherTagsManager INSTANCE;

    private TeacherDatabaseHelper mDbHelper;

    private TeacherTagsManager(Context context) {
        mDbHelper = new TeacherDatabaseHelper(context);
    }

    public static TeacherTagsManager getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new TeacherTagsManager(context);
        }
        return INSTANCE;
    }

    public String[] getTags() {
        List<String> tagList = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.query(TeacherTagsTable.TABLE_NAME,
                new String[]{TeacherTagsTable.TAG}, null, null, null, null, null);
        while (c.moveToNext()) {
            String tag = c.getString(c.getColumnIndex(TeacherTagsTable.TAG));
            tagList.add(tag);
        }
        c.close();
        db.close();
        return tagList.toArray(new String[]{});
    }

    public void updateTags(CharSequence... tags) {
        clearTags();
        for (CharSequence tag : tags) {
            addTag(tag);
        }
    }

    public void addTag(CharSequence tag) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        values.put(TeacherTagsTable.TAG, tag.toString());
        db.insert(TeacherTagsTable.TABLE_NAME, null, values);
        db.close();
    }

    public void clearTags() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(TeacherTagsTable.TABLE_NAME, null, null);
        db.close();
    }
}
