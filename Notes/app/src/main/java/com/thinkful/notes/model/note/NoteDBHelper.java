package com.thinkful.notes.model.note;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ardokusuma on 7/26/15.
 */
public class NoteDBHelper extends SQLiteOpenHelper {

    private static NoteDBHelper instance = null;

    public static NoteDBHelper getInstance(Context context){
        if (instance == null ) {
            instance = new NoteDBHelper(context);
        }

        return instance;
    }

    private NoteDBHelper(Context context) {
        super(context, NoteDBContract.DATABASE_NAME, null, NoteDBContract.DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NoteDBContract.SQL_CREATE_NOTE);
        db.execSQL(NoteDBContract.SQL_CREATE_TAG);
        db.execSQL(NoteDBContract.SQL_CREATE_NOTE_TAG);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(NoteDBContract.SQL_DELETE_NOTE_TAG);
        db.execSQL(NoteDBContract.SQL_DELETE_NOTE);
        db.execSQL(NoteDBContract.SQL_DELETE_TAG);
        onCreate(db);
    }
}
