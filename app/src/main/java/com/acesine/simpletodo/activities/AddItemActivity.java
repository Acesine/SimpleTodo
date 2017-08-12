package com.acesine.simpletodo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.acesine.simpletodo.Constants;
import com.acesine.simpletodo.R;
import com.acesine.simpletodo.persistent.TodoItem;
import com.acesine.simpletodo.utils.SpinnerUtils;

import java.util.Date;
import java.util.UUID;

public class AddItemActivity extends AppCompatActivity {
    public final static int ADD_ITEM_COMPLETE_CODE = 1;
    public final static int ADD_ITEM_CANCEL_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        SpinnerUtils.setupPrioritySpinner(this, null);
        TimePicker tp = (TimePicker) findViewById(R.id.timePicker);
        tp.setIs24HourView(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_item_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem it) {
        int id = it.getItemId();

        if (id == R.id.save_item) {
            String itemName = ((EditText) findViewById(R.id.etAddItem)).getText().toString();
            String priority = ((Spinner) findViewById(R.id.prioritySpinner)).getSelectedItem().toString();
            DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
            TimePicker tp = (TimePicker) findViewById(R.id.timePicker);
            int day = dp.getDayOfMonth();
            int month = dp.getMonth();
            int year = dp.getYear();
            int hour = tp.getHour();
            int minute = tp.getMinute();
            String dueDate = Constants.DATE_FORMAT.format(new Date(year-1900, month, day, hour, minute));
            String creationDate = Constants.DATE_FORMAT.format(new Date());
            TodoItem item = new TodoItem(UUID.randomUUID().toString(), itemName, Constants.Priority.valueOf(priority), dueDate, creationDate);
            Intent result = new Intent();
            result.putExtra(MainActivity.ITEM_DATA, item);
            setResult(ADD_ITEM_COMPLETE_CODE, result);
            finish();
        } else if (id == R.id.cancel_item) {
            setResult(ADD_ITEM_CANCEL_CODE, new Intent());
            finish();
        }
        return true;
    }
}
