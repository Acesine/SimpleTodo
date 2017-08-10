package com.acesine.simpletodo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.acesine.simpletodo.R;
import com.acesine.simpletodo.persistent.TodoItem;

public class EditItemActivity extends AppCompatActivity {
    public final static int EDIT_ITEM_RESULT_CODE = 1;

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

    public void onSave(View view) {
        String newName = ((EditText)findViewById(R.id.editText)).getText().toString();
        item.setItemName(newName);
        Intent result = new Intent();
        result.putExtra(MainActivity.ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.ITEM_POSITION));
        result.putExtra(MainActivity.ITEM_DATA, item);
        setResult(EDIT_ITEM_RESULT_CODE, result);
        finish();
    }
}
