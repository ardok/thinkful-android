package com.thinkful.noteloc.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thinkful.noteloc.CreateActivity;
import com.thinkful.noteloc.R;
import com.thinkful.noteloc.model.note.NoteDAO;
import com.thinkful.noteloc.model.note.NoteListItem;

import java.util.ArrayList;

public class NoteListItemAdapter extends RecyclerView.Adapter<NoteListItemAdapter.ViewHolder> {

    private NoteDAO mNoteDAO;

    private Context mContext;
    private RecyclerView mRecyclerView;
    private ArrayList<NoteListItem> mNoteListItems = new ArrayList<NoteListItem>();

    public NoteListItemAdapter(Context context, RecyclerView recyclerView) {
        this.mContext = context;
        this.mRecyclerView = recyclerView;

        mNoteDAO = new NoteDAO(context);
        mNoteListItems = (ArrayList<NoteListItem>) mNoteDAO.list();
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(this.mContext).inflate(R.layout.note_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        NoteListItem noteListItem = mNoteListItems.get(i);
        viewHolder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the position of v
                // Call the removeItem method with the position
//                removeItem(viewHolder.getAdapterPosition());
                Intent intent = new Intent(mContext, CreateActivity.class);
                mContext.startActivity(intent);
            }
        });
        viewHolder.setText(noteListItem.getText());
    }

    @Override
    public int getItemCount() {
        return mNoteListItems.size();
    }

    public void addItem(NoteListItem item) {
        mNoteListItems.add(0, item);
        notifyItemInserted(0);
        mNoteDAO.save(item);
    }

    public void removeItem(int position) {
        NoteListItem removedNote = mNoteListItems.remove(position);
        notifyItemRemoved(position);
        mNoteDAO.delete(removedNote);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            this.text = (TextView) itemView.findViewById(R.id.text);
        }

        public void setText(String text) {
            this.text.setText(text);
        }
    }
}
