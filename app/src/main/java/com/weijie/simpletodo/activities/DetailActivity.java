package com.weijie.simpletodo.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;

import com.weijie.simpletodo.R;
import com.weijie.simpletodo.model.ToDo;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DetailActivity extends AppCompatActivity {

    EditText name;
    EditText date;
    EditText notes;
    Spinner priority;
    Spinner status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        name = (EditText) findViewById(R.id.editText2);
        date = (EditText) findViewById(R.id.editText3);
        notes = (EditText) findViewById(R.id.editText4);
        priority = (Spinner) findViewById(R.id.spinner);
        status = (Spinner) findViewById(R.id.spinner2);

        ToDo td = (ToDo) intent.getSerializableExtra("item");
        name.setText(td.getTitle());
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        date.setText(df.format(td.getDueDate()));
        notes.setText(td.getNotes());
        switch (td.getPriority().toString()) {
            case "High": {
                priority.setSelection(0);
                break;
            }
            case "Medium": {
                priority.setSelection(1);
                break;
            }
            case "Low": default:{
                priority.setSelection(2);
                break;
            }

        }

        switch (td.getStatus().toString()) {
            case "To-Do": {
                status.setSelection(0);
                break;
            }
            case "Done": default:{
                status.setSelection(1);
                break;
            }

        }
    }

    public void saveItem(View view) {
        Intent intent = new Intent();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        ToDo backToDo = null;
        try {
            Log.d("toDolist", "sendDataback" + priority.getSelectedItem().toString() + df.parse(date.getText().toString()));
            backToDo = new ToDo(name.getText().toString(), df.parse(date.getText().toString()),
                    priority.getSelectedItem().toString(), notes.getText().toString(),
                    status.getSelectedItem().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        intent.putExtra("dataBack", backToDo);
        setResult(RESULT_OK, intent);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        finish();
    }

    public void discardChanges(View view) {
        onBackPressed();
    }
}
