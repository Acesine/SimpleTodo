package com.acesine.simpletodo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.acesine.simpletodo.R;
import com.acesine.simpletodo.persistent.TodoItem;

import java.util.UUID;

public class EditItemActivity extends AppCompatActivity {
    public final static int EDIT_ITEM_COMPLETE_CODE = 1;
    public final static int EDIT_ITEM_CANCEL_CODE = 2;

    private TodoItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        item = getIntent().getExtras().getParcelable(MainActivity.ITEM_DATA);
        if (item.getItemName() != null) {
            EditText et = (EditText) findViewById(R.id.editText);
            et.setText(item.getItemName());
            et.setSelection(item.getItemName().length());
        }
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
            String newName = ((EditText)findViewById(R.id.editText)).getText().toString();
            item.setItemName(newName);
            Intent result = new Intent();
            result.putExtra(MainActivity.ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.ITEM_POSITION));
            result.putExtra(MainActivity.ITEM_DATA, item);
            setResult(EDIT_ITEM_COMPLETE_CODE, result);
            finish();
        } else if (id == R.id.cancel_item) {
            setResult(EDIT_ITEM_CANCEL_CODE, new Intent());
            finish();
        }
        return true;
    }
}
