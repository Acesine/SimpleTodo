package com.acesine.simpletodo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.acesine.simpletodo.R;
import com.acesine.simpletodo.adapters.TodoItemAdapter;
import com.acesine.simpletodo.persistent.TodoItem;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public final static String ITEM_POSITION = "item_position";
    public final static String ITEM_DATA = "item_data";

    private final static int EDIT_ITEM_REQUEST_CODE = 1;
    private final static int ADD_ITEM_REQUEST_CODE = 2;

    List<TodoItem> items;
    ArrayAdapter<TodoItem> itemsAdapter;
    ListView lvItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<>();
        readItems();
        itemsAdapter = new TodoItemAdapter(this, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_item) {
            Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
            startActivityForResult(intent, ADD_ITEM_REQUEST_CODE);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_ITEM_REQUEST_CODE) {
            if (resultCode == EditItemActivity.EDIT_ITEM_COMPLETE_CODE) {
                int pos = data.getExtras().getInt(ITEM_POSITION);
                TodoItem newItem = data.getExtras().getParcelable(ITEM_DATA);
                items.set(pos, newItem);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
            } else if (resultCode == EditItemActivity.EDIT_ITEM_CANCEL_CODE) {
                // Do nothing
            }
        } else if (requestCode == ADD_ITEM_REQUEST_CODE) {
            if (resultCode == AddItemActivity.ADD_ITEM_COMPLETE_CODE) {
                TodoItem newItem = data.getExtras().getParcelable(ITEM_DATA);
                items.add(newItem);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
            } else if (resultCode == AddItemActivity.ADD_ITEM_CANCEL_CODE) {
                // Do nothing
            }
        }
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (!items.get(position).delete()) {
                    return false;
                }
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TodoItem item = items.get(position);
                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                intent.putExtra(ITEM_POSITION, position);
                intent.putExtra(ITEM_DATA, item);
                startActivityForResult(intent, EDIT_ITEM_REQUEST_CODE);
            }
        });
    }

    private void readItems() {
        items = SQLite.select().
                from(TodoItem.class).
                queryList();
    }

    private void writeItems() {
        for (TodoItem item : items) {
            item.save();
        }
    }
}
