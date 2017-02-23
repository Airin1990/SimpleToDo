package com.weijie.simpletodo.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.weijie.simpletodo.R;
import com.weijie.simpletodo.adapter.ToDoAdapter;
import com.weijie.simpletodo.model.ToDo;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ArrayList<ToDo> toDoItems;
    ToDoAdapter aToDoAdapter;
    ListView listview;
    EditText editText;
    int editPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateItems();
        aToDoAdapter = new ToDoAdapter(this, toDoItems);
        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(aToDoAdapter);
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                aToDoAdapter.remove(aToDoAdapter.getItem(i));
                aToDoAdapter.notifyDataSetChanged();
                return false;
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("item", aToDoAdapter.getItem(i));
                editPos = i;
                startActivityForResult(intent, 123);
            }
        });

        editText = (EditText) findViewById(R.id.editText);
    }

    private void populateItems() {
        //To add default items here
        toDoItems = new ArrayList<>();

        toDoItems.add(new ToDo("Wash clothes", new Date(),"Low", "","To-Do"));
        toDoItems.add(new ToDo("Clean Houses", new Date(),"Medium", "","Done"));
        toDoItems.add(new ToDo("Buy Television", new Date(),"High", "","To-Do"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            if (resultCode == RESULT_OK) {
                toDoItems.set(editPos,(ToDo) data.getSerializableExtra("dataBack"));
                aToDoAdapter.notifyDataSetChanged();
            }
        }
    }

    public void addItem(View view) {
        String additem = editText.getText().toString();
        if (additem!= null && additem != "") {
            ToDo newitem = new ToDo(additem, new Date(),"Low","","To-do");
            aToDoAdapter.add(newitem);
            aToDoAdapter.notifyDataSetChanged();
            editText.setText("");
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
