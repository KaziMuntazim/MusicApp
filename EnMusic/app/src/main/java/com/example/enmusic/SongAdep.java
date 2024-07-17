package com.example.enmusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SongAdep extends BaseAdapter{
    Context context;
    String[] item;

    public SongAdep(Context context , String[] item) {
        this.context = context;
        this.item = item;
        inflater = LayoutInflater.from(context);
    }

    LayoutInflater inflater;

    @Override
    public int getCount() {
        return item.length;
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
        View view = inflater.inflate(R.layout.songlist , null);
        TextView song_text = view.findViewById(R.id.song_text);
        song_text.setText(item[position]);
        return view;
    }
}
