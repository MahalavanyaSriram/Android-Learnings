package com.example.itunesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;


/**
* a. Assignment : #05.
* b. File Name : AppAdapter (com.example.itunesapp).
* c. Full name of the student : Mahalavanya Sriram, Chandan Mannem.
**/

public class AppAdapter extends ArrayAdapter<DataServices.App> {

    public AppAdapter(@NonNull Context context, int resource, @NonNull List<DataServices.App> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView ==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.app_list_row,parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.appName = convertView.findViewById(R.id.appName);
            viewHolder.artistName = convertView.findViewById(R.id.artistName);
            viewHolder.releaseDate = convertView.findViewById(R.id.releaseDate);
            convertView.setTag(viewHolder);
        }
        DataServices.App app = getItem(position);
        ViewHolder viewHolder = (ViewHolder)convertView.getTag();
        viewHolder.appName.setText(app.name);
        viewHolder.artistName.setText(app.artistName);
        viewHolder.releaseDate.setText(app.releaseDate);
        return convertView;
    }


    public static class ViewHolder{
        TextView appName;
        TextView artistName;
        TextView releaseDate;

    }
}
