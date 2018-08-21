package com.example.douban.Adapter;

import android.content.Context;
import android.util.Log;
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

public class Adapter_castimg extends BaseAdapter {
    private Context context;
    private ArrayList arrayList;
    public Adapter_castimg(Context context,ArrayList arrayList){
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
        ViewHolder6 viewHolder6= null;
        if (view == null){
            viewHolder6 = new ViewHolder6();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.casts_images,null);
            viewHolder6.img = view.findViewById(R.id.casts);
            viewHolder6.name = view.findViewById(R.id.name_chn);
            view.setTag(viewHolder6);
        }else
            viewHolder6 = (ViewHolder6) view.getTag();
        Map<String,Object> map = (Map<String, Object>) arrayList.get(i);
        Glide.with(context).load(map.get("cast"+(i+1))).into(viewHolder6.img);
        viewHolder6.name.setText((String) map.get("castname"+(i+1)));
        return view;
    }

    class ViewHolder6{
        public ImageView img;
        public TextView name;
    }
}
