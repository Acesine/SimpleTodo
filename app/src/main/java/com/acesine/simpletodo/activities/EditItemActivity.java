package com.acesine.simpletodo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.acesine.simpletodo.Constants;
import com.acesine.simpletodo.R;
import com.acesine.simpletodo.persistent.TodoItem;
import com.acesine.simpletodo.utils.DateTimeUtils;
import com.acesine.simpletodo.utils.SpinnerUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

public class EditItemActivity extends AppCompatActivity {
    public final static int EDIT_ITEM_COMPLETE_CODE = 1;
    public final static int EDIT_ITEM_CANCEL_CODE = 2;
    public final static int EDIT_ITEM_DELETE_CODE = 3;

    private TodoItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        item = getIntent().getExtras().getParcelable(MainActivity.ITEM_DATA);
        if (item.getItemName() != null) {
            EditText et = (EditText) findViewById(R.id.etEditItem);
            et.setText(item.getItemName());
            et.setSelection(item.getItemName().length());
        }

        SpinnerUtils.setupPrioritySpinner(this, item.getItemPriority());
        if (item.getItemDueDate() != null) {
            try {
                DateTimeUtils.setupDateTime(this, Constants.DATE_FORMAT.parse(item.getItemDueDate()));
            } catch (ParseException e) {
                //
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_item_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem it) {
        int id = it.getItemId();

        if (id == R.id.save_item) {
            String newName = ((EditText)findViewById(R.id.etEditItem)).getText().toString();
            String newPriority = ((Spinner) findViewById(R.id.prioritySpinner)).getSelectedItem().toString();
            DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
            TimePicker tp = (TimePicker) findViewById(R.id.timePicker);
            int day = dp.getDayOfMonth();
            int month = dp.getMonth();
            int year = dp.getYear();
            int hour = tp.getHour();
            int minute = tp.getMinute();
            String dueDate = Constants.DATE_FORMAT.format(new Date(year-1900, month, day, hour, minute));
            item.setItemName(newName);
            item.setItemPriority(newPriority);
            item.setItemDueDate(dueDate);
            Intent result = new Intent();
            result.putExtra(MainActivity.ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.ITEM_POSITION));
            result.putExtra(MainActivity.ITEM_DATA, item);
            setResult(EDIT_ITEM_COMPLETE_CODE, result);
            finish();
        } else if (id == R.id.cancel_item) {
            setResult(EDIT_ITEM_CANCEL_CODE);
            finish();
        } else if (id == R.id.delete_item) {
            Intent result = new Intent();
            result.putExtra(MainActivity.ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.ITEM_POSITION));
            setResult(EDIT_ITEM_COMPLETE_CODE, result);
            setResult(EDIT_ITEM_DELETE_CODE, result);
            finish();
        }
        return true;
    }
}
