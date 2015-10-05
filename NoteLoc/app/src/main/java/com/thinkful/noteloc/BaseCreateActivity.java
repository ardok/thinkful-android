package com.thinkful.noteloc;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.thinkful.noteloc.adapter.NoteListItemAdapter;
import com.thinkful.noteloc.model.note.NoteDAO;
import com.thinkful.noteloc.model.note.NoteDBHelper;
import com.thinkful.noteloc.model.note.NoteListItem;

public class BaseCreateActivity extends BaseToolbarActivity {

    private Button mButton;
    private EditText mEditText;

    private NoteDAO mNoteDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNoteDAO = new NoteDAO(this);

        View child = getLayoutInflater().inflate(R.layout.base_create, null);
        getContentFrame().addView(child);

        mEditText = (EditText) findViewById(R.id.note_text_edit);
        mButton = (Button) findViewById(R.id.note_save_button);

        // Make sure database is created
        NoteDBHelper.getInstance(this).getReadableDatabase();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text in the EditText
                // Create a new NoteListItem with the text
                // Add the item to the adapter
                // Set the EditText to an empty string
                NoteListItem note = new NoteListItem(mEditText.getText().toString());
                mNoteDAO.save(note);
                finish();
            }
        });
    }
}
