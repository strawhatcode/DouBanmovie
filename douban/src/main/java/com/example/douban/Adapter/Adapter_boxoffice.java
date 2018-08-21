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

public class Adapter_boxoffice extends BaseAdapter{
    private Context context;
    private ArrayList arrayList;
    public Adapter_boxoffice(Context context,ArrayList arrayList){
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
        ViewHolder2 viewHolder2 = null;
        if (view == null){
            viewHolder2 = new ViewHolder2();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.boxoffice_view,null);
            viewHolder2.rank = view.findViewById(R.id.rank);
            viewHolder2.box = view.findViewById(R.id.piaofang);
            viewHolder2.image = view.findViewById(R.id.movie_image);
            viewHolder2.title = view.findViewById(R.id.movie_title);
            viewHolder2.genres = view.findViewById(R.id.movie_type);
            viewHolder2.rating = view.findViewById(R.id.movie_rating);
            viewHolder2.director = view.findViewById(R.id.movie_director);
            viewHolder2.cast = view.findViewById(R.id.movie_cast);
            view.setTag(viewHolder2);
        }else
            viewHolder2 = (ViewHolder2) view.getTag();
        Map<String,Object> map = (Map<String, Object>) arrayList.get(i);
        viewHolder2.rank.setText((String )map.get("rank"));
        viewHolder2.box.setText((String )map.get("box"));
        Glide.with(context).load(map.get("images")).into(viewHolder2.image);
        viewHolder2.title.setText((String) map.get("title"));
        viewHolder2.genres.setText((String) map.get("genres"));
        viewHolder2.rating.setText((String)map.get("rating"));
        viewHolder2.director.setText((String)map.get("directors"));
        viewHolder2.cast.setText((String)map.get("casts"));
        return view;
    }
    class ViewHolder2{
        public TextView rank;
        public TextView box;
        public ImageView image;
        public TextView title;
        public TextView genres;
        public TextView rating;
        public TextView director;
        public TextView cast;
    }
}
