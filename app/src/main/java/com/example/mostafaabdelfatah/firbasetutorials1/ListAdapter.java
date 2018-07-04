package com.example.mostafaabdelfatah.firbasetutorials1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mostafa Abd El Fatah on 2/1/2018.
 */

public class ListAdapter  extends ArrayAdapter<User> {

    public ListAdapter(Context context, ArrayList<User> users) {
        super(context, 0, users);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        User user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_layout, parent, false);
        }
        // Lookup view for data population
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView age = (TextView) convertView.findViewById(R.id.age);
        TextView address = (TextView) convertView.findViewById(R.id.address);
        // Populate the data into the template view using the data object
        name.setText(user.getName());
        age.setText(user.getAge());
        address.setText(user.getAddress());
        // Return the completed view to render on screen
        return convertView;
    }
}
