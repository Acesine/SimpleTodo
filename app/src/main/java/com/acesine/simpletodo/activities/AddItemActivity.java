package com.acesine.simpletodo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.acesine.simpletodo.R;
import com.acesine.simpletodo.persistent.TodoItem;

import java.util.UUID;

import static com.acesine.simpletodo.activities.EditItemActivity.EDIT_ITEM_CANCEL_CODE;

public class AddItemActivity extends AppCompatActivity {
    public final static int ADD_ITEM_COMPLETE_CODE = 1;
    public final static int ADD_ITEM_CANCEL_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
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
            String itemName = ((EditText) findViewById(R.id.etNewItem)).getText().toString();
            TodoItem item = new TodoItem(UUID.randomUUID().toString(), itemName);
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
