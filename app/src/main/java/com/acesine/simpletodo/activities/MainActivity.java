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

import com.acesine.simpletodo.Constants;
import com.acesine.simpletodo.R;
import com.acesine.simpletodo.adapters.TodoItemAdapter;
import com.acesine.simpletodo.persistent.TodoItem;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public final static String ITEM_POSITION = "item_position";
    public final static String ITEM_DATA = "item_data";

    private final static int EDIT_ITEM_REQUEST_CODE = 1;
    private final static int ADD_ITEM_REQUEST_CODE = 2;

    List<TodoItem> items;
    ArrayAdapter<TodoItem> itemsAdapter;
    ListView lvItems;
    private Timer timer;

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
    protected void onResume() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        itemsAdapter.notifyDataSetChanged();
                    }
                });
            }
        }, 0, 60000);
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        super.onPause();
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
            switch (resultCode) {
                case EditItemActivity.EDIT_ITEM_COMPLETE_CODE:
                    int pos = data.getExtras().getInt(ITEM_POSITION);
                    TodoItem newItem = data.getExtras().getParcelable(ITEM_DATA);
                    items.set(pos, newItem);
                    writeItems();
                    itemsAdapter.notifyDataSetChanged();
                    break;
                case EditItemActivity.EDIT_ITEM_CANCEL_CODE:
                    break;
                case EditItemActivity.EDIT_ITEM_DELETE_CODE:
                    pos = data.getExtras().getInt(ITEM_POSITION);
                    if (!items.get(pos).delete()) {
                        return;
                    }
                    items.remove(pos);
                    writeItems();
                    itemsAdapter.notifyDataSetChanged();
                    break;
            }
        } else if (requestCode == ADD_ITEM_REQUEST_CODE) {
            switch (resultCode) {
                case AddItemActivity.ADD_ITEM_COMPLETE_CODE:
                    TodoItem newItem = data.getExtras().getParcelable(ITEM_DATA);
                    items.add(newItem);
                    writeItems();
                    itemsAdapter.notifyDataSetChanged();
                    break;
                case AddItemActivity.ADD_ITEM_CANCEL_CODE:
                    break;
            }
        }
    }

    private void setupListViewListener() {
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
        sortTodoItems();
    }

    private void writeItems() {
        sortTodoItems();
        for (TodoItem item : items) {
            item.save();
        }
    }

    private void sortTodoItems() {
        Collections.sort(items, new Comparator<TodoItem>() {
            @Override
            public int compare(TodoItem o1, TodoItem o2) {
                return Constants.PRIORITY.indexOf(o1.getItemPriority()) - Constants.PRIORITY.indexOf(o2.getItemPriority());
            }
        });
    }
}
