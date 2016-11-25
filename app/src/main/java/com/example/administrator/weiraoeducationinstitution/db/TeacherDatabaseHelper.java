package com.example.administrator.weiraoeducationinstitution.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/6/6.
 */
public class TeacherDatabaseHelper extends SQLiteOpenHelper {
    public TeacherDatabaseHelper(Context context) {
        super(context, "teacher_tag.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TeacherTagsTable.TABLE_NAME
                + "("
                + TeacherTagsTable._ID + " INTEGER PRIMARY KEY,"
                + TeacherTagsTable.TAG + " TEXT"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
