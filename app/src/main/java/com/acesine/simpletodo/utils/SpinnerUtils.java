package com.acesine.simpletodo.utils;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.acesine.simpletodo.Constants;
import com.acesine.simpletodo.R;

import java.util.ArrayList;
import java.util.List;

public class SpinnerUtils {
    private SpinnerUtils() {}

    public static void setupPrioritySpinner(Activity activity, String defaultPriority) {
        if (defaultPriority == null) defaultPriority = Constants.PRIORITY.get(0);
        Spinner spinner = (Spinner) activity.findViewById(R.id.prioritySpinner);
        ArrayAdapter<String> aa = new ArrayAdapter<>(activity,android.R.layout.simple_spinner_item, Constants.PRIORITY);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        spinner.setSelection(Constants.PRIORITY.indexOf(defaultPriority), true);
        for (int pos=0; pos<spinner.getChildCount(); ++pos) {
            View v = spinner.getSelectedView();
            v.setBackgroundColor(Constants.PRIORITY_COLOR.get(((TextView) v).getText().toString()));
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                view.setBackgroundColor(Constants.PRIORITY_COLOR.get(((TextView) view).getText().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
