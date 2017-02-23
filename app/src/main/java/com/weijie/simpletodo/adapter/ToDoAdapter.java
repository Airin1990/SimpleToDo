package com.weijie.simpletodo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.weijie.simpletodo.R;
import com.weijie.simpletodo.model.ToDo;

import java.util.List;

/**
 * Created by weiji_000 on 2/23/2017.
 */

public class ToDoAdapter extends ArrayAdapter<ToDo> {

    private static class ViewHolder {
        TextView todo;
        TextView priority;
    }

    public ToDoAdapter(Context context, List<ToDo> objects) {
        super(context, R.layout.todo_row, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ToDo todoitem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.todo_row, parent, false);
            viewHolder.todo = (TextView) convertView.findViewById(R.id.todo_tv);
            viewHolder.priority = (TextView) convertView.findViewById(R.id.priority_tv);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.todo.setText(todoitem.getTitle());
        switch (todoitem.getPriority()) {
            case "High": {
                viewHolder.priority.setText("!"+todoitem.getPriority());
                viewHolder.priority.setTextColor(Color.RED);
                break;
            }
            case "Medium": {
                viewHolder.priority.setText(todoitem.getPriority());
                viewHolder.priority.setTextColor(Color.BLUE);
                break;
            }
            case "Low": default: {
                viewHolder.priority.setText(todoitem.getPriority());
                viewHolder.priority.setTextColor(Color.GREEN);
                break;
            }
        }

        // Return the completed view to render on screen
        return convertView;

    }
}
