package com.example.douban.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.douban.Adapter.Adapter_movieimg;
import com.example.douban.OverrideView.HorizontalListView;
import com.example.douban.R;
import com.example.douban.Thread.Asyn_Thread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CastsDetail extends AppCompatActivity {
    private ImageView back;
    private ImageView castimg;
    private TextView cast_name_chn,cast_name_en,cast_summary;
    private HorizontalListView movieimg;
    String string;
    private ProgressBar loading;
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_castsdetail);
        back = findViewById(R.id.ac_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cast_init();

        Intent castclick = getIntent();
        String castid = castclick.getStringExtra("castid");
        String url = "http://api.douban.com/v2/movie/celebrity/"+castid;

        Asyn_Thread asyn_thread = new Asyn_Thread(url,new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 7){
                    Bundle b = msg.getData();
                    string = b.getString("text");
                    cast_show();
                    Log.i("sssssssssss","kaikaikai");
                    loading.setVisibility(View.GONE);
                }
            }
        },"castdetail");
    new Thread(asyn_thread).start();

    //点击影人的其他影片可以跳转到相应影片的介绍页面
    movieimg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(CastsDetail.this,MovieDetail.class);
            Map<String,Object> map = (Map<String, Object>) getmovieimage(string).get(i);
            String id = (String) map.get("id"+(i+1));
            intent.putExtra("clickid",id);
            startActivity(intent);
        }
    });
    }

    //存储影人的信息的内容
    public ArrayList castsinformation(String str){
        ArrayList<Map<String,Object>> arrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(str);
            Map<String,Object> map = new HashMap<>();
            map.put("castimg",jsonObject.getJSONObject("avatars").getString("medium"));
            map.put("name_chn",jsonObject.getString("name"));
            map.put("name_en",jsonObject.getString("name_en"));
            map.put("cast_summary",jsonObject.getString("name")+", "
                    +jsonObject.getString("gender")+", "+jsonObject.getString("born_place"));
            arrayList.add(map);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    //用来存储其他影片的方法
    public ArrayList getmovieimage(String str) {
        ArrayList<Map<String,Object>> arrayList2 = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = jsonObject.getJSONArray("works");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jo = (JSONObject) jsonArray.get(i);
                JSONObject jo2 = jo.getJSONObject("subject");

                Map<String,Object> map = new HashMap<>();
                map.put("movieimg",jo2.getJSONObject("images").getString("medium"));
                map.put("rating",jo2.getJSONObject("rating").getString("average")+"  分");
                map.put("title",jo2.getString("title"));
                map.put("id"+(i+1),jo2.getString("id"));
                arrayList2.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList2;
    }

    //初始化控件
    @SuppressLint("WrongViewCast")
    public void cast_init(){
        castimg = findViewById(R.id.castimg);
        cast_name_chn = findViewById(R.id.cast_name_chn);
        cast_name_en = findViewById(R.id.cast_name_en);
        cast_summary = findViewById(R.id.cast_summary);
        movieimg = findViewById(R.id.movieimg);
        loading = findViewById(R.id.ac_loading);
    }

    //把内容显示在界面上的方法
    public void cast_show(){
        cast_init();
        Adapter_movieimg adapter_movieimg = new Adapter_movieimg(CastsDetail.this,getmovieimage(string));
        ArrayList arr = castsinformation(string);
        for (int i=0;i<arr.size();i++) {
            Map<String,Object> m = (Map<String, Object>) arr.get(i);
            Glide.with(this).load(m.get("castimg")).into(castimg);
            cast_name_chn.setText((String) m.get("name_chn"));
            cast_name_en.setText((String) m.get("name_en"));
            cast_summary.setText((String) m.get("cast_summary"));
            movieimg.setAdapter(adapter_movieimg);
        }
    }
}
