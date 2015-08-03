package com.thinkful.notes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thinkful.notes.R;
import com.thinkful.notes.model.note.NoteDAO;
import com.thinkful.notes.model.note.NoteListItem;

import java.util.ArrayList;

/**
 * Created by ardokusuma on 7/5/15.
 */
public class NoteListItemAdapter extends RecyclerView.Adapter<NoteListItemAdapter.ViewHolder> {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private ArrayList<NoteListItem> mNoteListItems = new ArrayList<NoteListItem>();

    public NoteListItemAdapter(Context context, RecyclerView recyclerView) {
        this.mContext = context;
        this.mRecyclerView = recyclerView;

        NoteDAO noteDAO = new NoteDAO(context);
        mNoteListItems = (ArrayList<NoteListItem>) noteDAO.list();
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
                removeItem(viewHolder.getAdapterPosition());
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
    }

    public void removeItem(int position) {
        mNoteListItems.remove(position);
        notifyItemRemoved(position);
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