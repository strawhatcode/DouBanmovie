package com.example.douban.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.douban.Activity.MovieDetail;
import com.example.douban.Activity.SearchActivity;
import com.example.douban.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

public class Adapter_hot extends BaseAdapter {
    private Context context;
    private List<Map<String,Object>> arrayList;
    public Adapter_hot(Context context,List<Map<String,Object>> arrayList){
        this.context = context;
        this.arrayList = arrayList;

    }
//    public ListAdapter showadapter(Context context, ArrayList arrayList){
//        SimpleAdapter SA = new SimpleAdapter(context,arrayList, R.layout.search_movieview,
//                new String[]{"images","title","type","rating","directors","casts"},
//                new int[]{R.id.movie_image,R.id.movie_title,R.id.movie_type,R.id.movie_rating,R.id.movie_director,R.id.movie_cast});
//        return SA;
//    }
//
//    public ListAdapter showadapter2(Context context,ArrayList arrayList){
//        SimpleAdapter SA2 = new SimpleAdapter(context,arrayList,R.layout.boxoffice_view,
//                new String[]{"rank","box","images","title","genres","rating","directors","casts"},
//                new int[]{R.id.number,R.id.piaofang,R.id.movie_image,R.id.movie_title,R.id.movie_type,
//                R.id.movie_rating,R.id.movie_director,R.id.movie_cast});
//        return SA2;
//    }
//
//    public ListAdapter showadapter3(Context context,ArrayList arrayList){
//        SimpleAdapter SA2 = new SimpleAdapter(context,arrayList,R.layout.boxoffice_view,
//                new String[]{"rank","images","title","genres","rating","directors","casts"},
//                new int[]{R.id.number,R.id.movie_image,R.id.movie_title,R.id.movie_type,
//                        R.id.movie_rating,R.id.movie_director,R.id.movie_cast});
//        return SA2;
//    }

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
        ViewHolder viewHolder = null;
        if (view==null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.search_movieview,null);
            viewHolder.image = view.findViewById(R.id.movie_image);
            viewHolder.title = view.findViewById(R.id.movie_title);
            viewHolder.genres = view.findViewById(R.id.movie_type);
            viewHolder.rating = view.findViewById(R.id.movie_rating);
            viewHolder.director = view.findViewById(R.id.movie_director);
            viewHolder.cast = view.findViewById(R.id.movie_cast);
            view.setTag(viewHolder);
        }else
            viewHolder = (ViewHolder) view.getTag();
        Map<String,Object> map =arrayList.get(i);
        Glide.with(context).load(map.get("images")).into(viewHolder.image);
        viewHolder.title.setText((String) map.get("title"));
        viewHolder.genres.setText((String) map.get("genres"));
        viewHolder.rating.setText((String)map.get("rating"));
        viewHolder.director.setText((String)map.get("directors"));
        viewHolder.cast.setText((String)map.get("casts"));

        return view;
    }

    class ViewHolder{
        public ImageView image;
        public TextView title;
        public TextView genres;
        public TextView rating;
        public TextView director;
        public TextView cast;
    }

}

