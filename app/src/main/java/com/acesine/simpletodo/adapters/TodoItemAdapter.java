package com.acesine.simpletodo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.acesine.simpletodo.R;
import com.acesine.simpletodo.persistent.TodoItem;

import java.util.List;

public class TodoItemAdapter extends ArrayAdapter<TodoItem> {
    public TodoItemAdapter(Context context, List<TodoItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TodoItem item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item, parent, false);
        }
        // Lookup view for data population
        TextView itemView = (TextView) convertView.findViewById(R.id.item_name);
        // Populate the data into the template view using the data object
        itemView.setText(item.getItemName());
        // Return the completed view to render on screen
        return convertView;
    }
}