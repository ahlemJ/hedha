package com.example.ahlem.myapplication1;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomAdapter extends BaseAdapter {
    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");

        return null;
    }
}
