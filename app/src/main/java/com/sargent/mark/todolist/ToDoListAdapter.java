package com.sargent.mark.todolist;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.sargent.mark.todolist.data.Contract;
import com.sargent.mark.todolist.data.ToDoItem;

import java.util.ArrayList;

/**
 * Created by mark on 7/4/17.
 */

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ItemHolder> {

    private Cursor cursor;
    private ItemClickListener listener;
    private String TAG = "todolistadapter";

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item, parent, false);
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(holder, position);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public interface ItemClickListener {
        void onItemClick(int pos, String description, String duedate, long id, boolean status, String category);
    }

    public ToDoListAdapter(Cursor cursor, ItemClickListener listener) {
        this.cursor = cursor;
        this.listener = listener;
    }

    public void swapCursor(Cursor newCursor){
        if (cursor != null) cursor.close();
        cursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    class ItemHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        TextView due;
        CheckBox checkBox;
        TextView desc;

        //Declarying local variables for status and category
        String duedate;
        String description;
        boolean status;
        String category;
        long id;


        ItemHolder(View view) {
            super(view);
            due = (TextView) view.findViewById(R.id.dueDate);
            desc = (TextView) view.findViewById(R.id.description);
            checkBox = (CheckBox) view.findViewById(R.id.status);

            view.setOnClickListener(this);

            // Checkbox Listner  for onclick events, Notiifies on every changes
            checkBox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                MainActivity.updateTodoStatus(cursor.getLong(cursor.getColumnIndex(Contract.TABLE_TODO._ID)),
                           checkBox.isChecked());
                }
            });

        }

        public void bind(ItemHolder holder, int pos) {

            cursor.moveToPosition(pos);
            id = cursor.getLong(cursor.getColumnIndex(Contract.TABLE_TODO._ID));

            Log.d(TAG, "Binding id: " + id);
            duedate = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_DUE_DATE));
            description = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_DESCRIPTION));

            //Binding data for status and category on every change TODO
            status = Boolean.getBoolean(cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_STATUS)));
            category = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_CATEGORY));

            due.setText(duedate);
            desc.setText(description);

            Log.d("ToDoListAdapter", "Status, Id: " + id + "Status is: " +status);
            checkBox.setChecked(status);

            holder.itemView.setTag(id);
        }


        //Adding status and category for onClick Event
        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(pos, description, duedate, id, status, category);
        }
    }

}
