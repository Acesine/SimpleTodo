package com.acesine.simpletodo.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.acesine.simpletodo.Constants;
import com.acesine.simpletodo.R;
import com.acesine.simpletodo.persistent.TodoItem;
import com.acesine.simpletodo.utils.DateTimeUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class TodoItemAdapter extends ArrayAdapter<TodoItem> {
    public TodoItemAdapter(Context context, List<TodoItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TodoItem item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item, parent, false);
        }
        TextView itemName = (TextView) convertView.findViewById(R.id.item_name);
        itemName.setText(item.getItemName());
        itemName.setBackgroundColor(Constants.PRIORITY_COLOR.get(item.getItemPriority()));
        itemName.setPaintFlags(0);
        TextView timeToDue = (TextView) convertView.findViewById(R.id.time_to_due);
        timeToDue.setBackgroundColor(Constants.PRIORITY_COLOR.get(item.getItemPriority()));
        if (item.getItemPriority() == Constants.Priority.Done) {
            timeToDue.setText(item.getItemPriority().name());
            itemName.setPaintFlags(itemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            try {
                timeToDue.setText(DateTimeUtils.getTimeToDue(Constants.DATE_FORMAT.parse(item.getItemDueDate())));
            } catch (ParseException e) {
                //
            }
        }
        try {
            setProgress(convertView, Constants.DATE_FORMAT.parse(item.getItemCreationDate()), Constants.DATE_FORMAT.parse(item.getItemDueDate()));
        } catch (ParseException e) {
            //
        }
        return convertView;
    }

    private void setProgress(View parent, Date start, Date due) {
        Date now = new Date();
        long diff = due.getTime() - now.getTime();
        int progress = 100;
        if (diff > 0) {
            progress = (int) (100.0 * (now.getTime() - start.getTime()) / (due.getTime() - start.getTime()));
        }
        ProgressBar pb = (ProgressBar) parent.findViewById(R.id.item_progress);
        pb.setProgress(progress, true);
    }
}