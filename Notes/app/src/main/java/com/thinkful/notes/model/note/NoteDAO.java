package com.thinkful.notes.model.note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by ardokusuma on 7/26/15.
 */
public class NoteDAO {
    private Context context;

    public NoteDAO(Context context) {
        this.context = context;
    }

    public long save(NoteListItem note) {
        NoteDBHelper helper = NoteDBHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NoteDBContract.Note.COLUMN_NAME_NOTE_TEXT, note.getText());
        values.put(NoteDBContract.Note.COLUMN_NAME_STATUS, note.getStatus());
        values.put(NoteDBContract.Note.COLUMN_NAME_NOTE_DATE, note.getDate().getTimeInMillis() / 1000);

        return db.insert(NoteDBContract.DATABASE_NAME, null, values);
    }

    public List<NoteListItem> list() {
        NoteDBHelper helper = NoteDBHelper.getInstance(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] projection = {
                NoteDBContract.Note.COLUMN_NAME_NOTE_TEXT,
                NoteDBContract.Note.COLUMN_NAME_STATUS,
                NoteDBContract.Note.COLUMN_NAME_NOTE_DATE
        };

        String sortOrder = NoteDBContract.Note.COLUMN_NAME_NOTE_DATE + " DESC";
        Cursor c = db.query(
                NoteDBContract.Note.TABLE_NAME,        // The table to query
                projection,                             // The columns to return
                null,                                   // The columns for the WHERE clause
                null,                                   // The values for the WHERE clause
                null,                                   // don't group the rows
                null,                                   // don't filter by row groups
                sortOrder                               // The sort order
        );

        List<NoteListItem> notes = new ArrayList<NoteListItem>();

        while (c.moveToNext()) {
            String text = c.getString(
                    c.getColumnIndex(NoteDBContract.Note.COLUMN_NAME_NOTE_TEXT));
            String status = c.getString(c.getColumnIndex(
                    NoteDBContract.Note.COLUMN_NAME_STATUS));
            Calendar date = new GregorianCalendar();
            date.setTimeInMillis(c.getLong(c.getColumnIndex(
                    NoteDBContract.Note.COLUMN_NAME_NOTE_DATE)) * 1000);
            notes.add(new NoteListItem(text, status, date));
        }

        c.close();

        return notes;
    }
}
