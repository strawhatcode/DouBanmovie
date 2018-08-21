package com.example.douban.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.douban.R;

import java.util.ArrayList;
import java.util.Map;

public class Adapter_movieimg extends BaseAdapter {
    private Context context;
    private ArrayList arrayList;
    public Adapter_movieimg(Context context,ArrayList arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder7 viewHolder7;
        if (view ==null){
            viewHolder7 = new ViewHolder7();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.movie_images,null);
            viewHolder7.cast_movie_img = view.findViewById(R.id.cast_movie_img);
            viewHolder7.cast_movie_title = view.findViewById(R.id.cast_movie_title);
            viewHolder7.cast_movie_rating = view.findViewById(R.id.cast_movie_rating);
            view.setTag(viewHolder7);
        }
        viewHolder7 = (ViewHolder7) view.getTag();
        Map<String,Object> map = (Map<String, Object>) arrayList.get(i);
        Glide.with(context).load(map.get("movieimg")).into(viewHolder7.cast_movie_img);
        viewHolder7.cast_movie_title.setText((String) map.get("title"));
        viewHolder7.cast_movie_rating.setText((String) map.get("rating"));
        return view;
    }

    class ViewHolder7 {
        public ImageView cast_movie_img;
        public TextView cast_movie_title,cast_movie_rating;
    }
}
