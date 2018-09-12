package com.example.douban.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.douban.Adapter.Adapter_castimg;
import com.example.douban.OverrideView.HorizontalListView;
import com.example.douban.R;
import com.example.douban.Thread.Asyn_Thread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MovieDetail extends AppCompatActivity{
    private HorizontalListView castimg;
    private ImageView back;
    public ImageView movieimg;
    public TextView title,year_country_genres,directors,rating,ratings_count,summary;
    String str;
    private ProgressBar loading;
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviedetail);

        init();
        back = findViewById(R.id.am_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        Intent getclickid = getIntent();//获取从其他activity传过来的数据
        String id = getclickid.getStringExtra("clickid");//把获取的数据转化为字符串
        String url = "http://api.douban.com/v2/movie/subject/"+id;

       Asyn_Thread asyn_thread = new Asyn_Thread(url,new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 6){
                    Bundle b = msg.getData();
                    str = b.getString("text");
                    movie_show();//调用show()方法显示内容
                    loading.setVisibility(View.GONE);
                }
            }
        },"moviedetail");
       new Thread(asyn_thread).start();

       //点击影人图片跳转到影人介绍界面
       castimg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(MovieDetail.this,CastsDetail.class);
                    Map<String,Object> m = (Map<String, Object>) getcastimage(str).get(i);
                    String castid = (String) m.get("castid"+(i+1));
                    intent.putExtra("castid",castid);
                    startActivity(intent);
                }
       });


    }

    public ArrayList movieinformation(String str){
        ArrayList<Map<String,Object>> arrayList = new ArrayList<Map<String, Object>>();
        SearchActivity searchActivity = new SearchActivity();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(str);
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("movieimg",jsonObject.getJSONObject("images").getString("medium"));
            map.put("title",jsonObject.getString("title"));

            map.put("year_country_genres",jsonObject.getString("year")+" / "+year_country_genres(jsonObject.getJSONArray("countries")
                    ,jsonObject.getJSONArray("genres")));
            JSONArray dir_arr = jsonObject.getJSONArray("directors");
            map.put("directors",searchActivity.inf(dir_arr,"name")+"  导演作品");
            map.put("rating",jsonObject.getJSONObject("rating").getString("average"));
            map.put("ratings_count",jsonObject.getString("ratings_count")+" 人");
            map.put("summary",jsonObject.getString("summary"));
            arrayList.add(map);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    //获取演员信息和图片的方法
    public ArrayList getcastimage(String string){
        ArrayList<Map<String,Object>> arrayList2 = new ArrayList<Map<String,Object>>();
        try {
            JSONObject jo = new JSONObject(string);
            JSONArray ja = jo.getJSONArray("casts");
            for(int i=0;i<ja.length();i++){
                JSONObject jo2 = (JSONObject) ja.get(i);
                Map<String,Object> m = new HashMap<String, Object>();
                m.put("cast"+(i+1),jo2.getJSONObject("avatars").getString("medium"));
                m.put("castid"+(i+1),jo2.getString("id"));
                m.put("castname"+(i+1),jo2.getString("name"));
                arrayList2.add(m);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList2;
    }
    //电影制作国家和类型合起来用“/”分隔开
    public String year_country_genres(JSONArray country,JSONArray genres){
        String str = "";
        for(int i=0;i<country.length();i++){
            try {
                if(i>0)
                    str += " / ";
                str += country.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
            str += " / ";
        for(int j=0;j<genres.length();j++){
            try {
                if (j>0)
                    str += " / ";
                str += genres.get(j);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    public void init(){
        movieimg = findViewById(R.id.movie_img);
        title = findViewById(R.id.movie_name);
        year_country_genres = findViewById(R.id.movie_inf);
        directors = findViewById(R.id.movie_dir);
        rating = findViewById(R.id.movie_rat);
        ratings_count = findViewById(R.id.movie_ratnum);
        summary = findViewById(R.id.movie_summary);
        castimg = findViewById(R.id.castimg);
        loading = findViewById(R.id.am_loading);
    }

    //把电影介绍内容显示在界面中
    public void movie_show(){
        init();
        Adapter_castimg adapter_castimg = new Adapter_castimg(MovieDetail.this,getcastimage(str));
        ArrayList arr = movieinformation(str);
        for(int i=0;i<arr.size();i++) {
            Map<String, Object> map = (Map<String, Object>)arr.get(i);
            Glide.with(this).load(map.get("movieimg")).into(movieimg);
            title.setText((String) map.get("title"));
            year_country_genres.setText((String) map.get("year_country_genres"));
            directors.setText((String) map.get("directors"));
            rating.setText((String) map.get("rating"));
            ratings_count.setText((String) map.get("ratings_count"));
            summary.setText((String) map.get("summary"));
            castimg.setAdapter(adapter_castimg);
        }
    }
}
