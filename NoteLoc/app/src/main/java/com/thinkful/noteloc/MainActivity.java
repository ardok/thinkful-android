package com.thinkful.noteloc;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.thinkful.noteloc.adapter.NoteListItemAdapter;
import com.thinkful.noteloc.model.note.NoteDAO;
import com.thinkful.noteloc.model.note.NoteDBHelper;
import com.thinkful.noteloc.model.note.NoteListItem;

public class MainActivity extends DrawerActivity {

    private RecyclerView mRecyclerView;
    private NoteListItemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button mButton;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View child = getLayoutInflater().inflate(R.layout.activity_main, null);
        getContentFrame().addView(child);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mEditText = (EditText) findViewById(R.id.edit_text);
        mButton = (Button) findViewById(R.id.button);

        // Make sure database is created
        NoteDBHelper.getInstance(this).getReadableDatabase();

        // Set up our layout
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new NoteListItemAdapter(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text in the EditText
                // Create a new NoteListItem with the text
                // Add the item to the adapter
                // Set the EditText to an empty string
                NoteListItem note = new NoteListItem(mEditText.getText().toString());
                mAdapter.addItem(note);
                mEditText.setText("");
                mLayoutManager.scrollToPosition(0);
            }
        });
    }
}
