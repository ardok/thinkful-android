package com.thinkful.notes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thinkful.notes.adapter.NoteListItemAdapter;
import com.thinkful.notes.model.note.NoteDAO;
import com.thinkful.notes.model.note.NoteDBHelper;
import com.thinkful.notes.model.note.NoteListItem;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private NoteListItemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button mButton;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Make sure database is created
        NoteDBHelper.getInstance(this).getReadableDatabase();

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new NoteListItemAdapter(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        mEditText = (EditText) findViewById(R.id.edit_text);
        mButton = (Button) findViewById(R.id.button);
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
                NoteDAO dao = new NoteDAO(MainActivity.this);
                dao.save(note);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast toast = Toast.makeText(this, "Hello", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM|Gravity.LEFT, 10, 10);
            toast.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
