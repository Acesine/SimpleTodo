package com.acesine.simpletodo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import com.acesine.simpletodo.R;
import com.acesine.simpletodo.persistent.TodoItem;
import com.acesine.simpletodo.utils.SpinnerUtils;

import java.util.UUID;

public class AddItemActivity extends AppCompatActivity {
    public final static int ADD_ITEM_COMPLETE_CODE = 1;
    public final static int ADD_ITEM_CANCEL_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        SpinnerUtils.setupPrioritySpinner(this, null);
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
            TodoItem item = new TodoItem(UUID.randomUUID().toString(), itemName, priority);
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
