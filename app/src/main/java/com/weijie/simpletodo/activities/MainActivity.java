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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
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
                writeArray();
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
        toDoItems = read(this);
        if (toDoItems == null) {
            toDoItems = new ArrayList<>();
            toDoItems.add(new ToDo("Wash clothes", new Date(), "Low", "", "To-Do"));
            toDoItems.add(new ToDo("Clean Houses", new Date(), "Medium", "", "Done"));
            toDoItems.add(new ToDo("Buy Television", new Date(), "High", "", "To-Do"));
            writeArray();
        }
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
                writeArray();
            }
        }
    }

    public void addItem(View view) {
        String additem = editText.getText().toString();
        if (additem!= null && additem != "") {
            ToDo newitem = new ToDo(additem, new Date(),"Low","","To-Do");
            aToDoAdapter.add(newitem);
            aToDoAdapter.notifyDataSetChanged();
            writeArray();
            editText.setText("");
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void writeArray() {
        File f = new File(getFilesDir(),"MyToDoItems.srl");
        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream objectwrite = new ObjectOutputStream(fos);
            objectwrite.writeObject(toDoItems);
            fos.close();

            if (!f.exists()) {
                f.mkdirs();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ToDo> read(Context context) {

        ObjectInputStream input = null;
        ArrayList<ToDo> result = null;
        File f = new File(this.getFilesDir(),"MyToDoItems.srl");
        try {

            input = new ObjectInputStream(new FileInputStream(f));
            result = (ArrayList<ToDo>) input.readObject();
            input.close();

        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}
