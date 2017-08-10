package com.acesine.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    public final static int EDIT_ITEM_RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        String currentText = getIntent().getExtras().getString(MainActivity.ITEM_VALUE);
        if (currentText != null) {
            EditText et = (EditText) findViewById(R.id.editText);
            et.setText(currentText);
            et.setSelection(currentText.length());
        }
    }

    public void onSave(View view) {
        Intent result = new Intent();
        result.putExtra(MainActivity.ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.ITEM_POSITION));
        result.putExtra(MainActivity.ITEM_VALUE, ((EditText)findViewById(R.id.editText)).getText().toString());
        setResult(EDIT_ITEM_RESULT_CODE, result);
        finish();
    }
}
