package com.test.myapplicationfinal.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Наследник SQLiteOpenHelper, помогающий создавать и обновлять БД
 */
public class NotesDbHelper extends SQLiteOpenHelper {

    /*конструктор*/
    public NotesDbHelper(Context context) {
        super(context, NotesContract.DB_NAME, null, NotesContract.DB_VERSION);
    }

    /*вызывается, когда БД создаётся*/
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        for (String query : NotesContract.CREATE_DATABASE_QUERIES) {
            sqLiteDatabase.execSQL(query);
        }
    }

    /*вызывается, когда система обнаруживает, что версия БД поменялась*/
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2) {
            sqLiteDatabase.execSQL(NotesContract.Images.CREATE_TABLE);
        }
    }
}