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

public class Adapter_top250 extends BaseAdapter {
    private Context context;
    private ArrayList arrayList;
    public Adapter_top250(Context context,ArrayList arrayList){
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
        ViewHolder3 viewHolder3 = null;
        if (view ==null){
            viewHolder3 = new ViewHolder3();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.boxoffice_view,null);
            viewHolder3.rank = view.findViewById(R.id.rank);
            viewHolder3.image = view.findViewById(R.id.movie_image);
            viewHolder3.title = view.findViewById(R.id.movie_title);
            viewHolder3.genres = view.findViewById(R.id.movie_type);
            viewHolder3.rating = view.findViewById(R.id.movie_rating);
            viewHolder3.director = view.findViewById(R.id.movie_director);
            viewHolder3.cast = view.findViewById(R.id.movie_cast);
            viewHolder3.piaofang = view.findViewById(R.id.piaofang);
            view.setTag(viewHolder3);
        }else
            viewHolder3 = (ViewHolder3) view.getTag();
        Map<String,Object> map = (Map<String, Object>) arrayList.get(i);
        viewHolder3.rank.setText((String )map.get("rank"));
        Glide.with(context).load(map.get("images")).into(viewHolder3.image);
        viewHolder3.title.setText((String) map.get("title"));
        viewHolder3.genres.setText((String) map.get("genres"));
        viewHolder3.rating.setText((String)map.get("rating"));
        viewHolder3.director.setText((String)map.get("directors"));
        viewHolder3.cast.setText((String)map.get("casts"));
        viewHolder3.piaofang.setVisibility(View.GONE);
        return view;
    }
    class ViewHolder3{
        public TextView rank,title,genres,rating,director,cast,piaofang;
        public ImageView image;
    }
}