package com.thinkful.noteloc.model.note;

import java.util.Calendar;

/**
 * Created by ardokusuma on 7/5/15.
 */
public class NoteListItem {
    private Long id;
    private String text;
    private String status;
    private Calendar date;

    public NoteListItem(String text) {
        this(null, text, "Open", Calendar.getInstance());
    }

    public NoteListItem(Long id, String text, String status, Calendar date) {
        this.id = id;
        this.text = text;
        this.status = status;
        this.date = date;
    }

    public String getText() {
        return this.text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
