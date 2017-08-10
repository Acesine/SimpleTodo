package com.acesine.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public final static String ITEM_POSITION = "item_position";
    public final static String ITEM_VALUE = "item_value";

    private final static int EDIT_ITEM_REQUEST_CODE = 1;

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    protected void onAddItem(View view) {
        EditText et = (EditText) findViewById(R.id.etNewItem);
        itemsAdapter.add(et.getText().toString());
        et.setText("");
        writeItems();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_ITEM_REQUEST_CODE && resultCode == EditItemActivity.EDIT_ITEM_RESULT_CODE) {
            int pos = data.getExtras().getInt(ITEM_POSITION);
            String newValue = data.getExtras().getString(ITEM_VALUE);
            items.set(pos, newValue);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = ((ListView) parent).getItemAtPosition(position).toString();
                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                intent.putExtra(ITEM_POSITION, position);
                intent.putExtra(ITEM_VALUE, selected);
                startActivityForResult(intent, EDIT_ITEM_REQUEST_CODE);
            }
        });
    }

    private void readItems() {
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "todo.txt");
        try {
            items = new ArrayList<>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<>();
        }
    }

    private void writeItems() {
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
