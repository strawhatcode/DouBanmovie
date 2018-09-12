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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.douban.Adapter.Adapter_boxoffice;
import com.example.douban.R;
import com.example.douban.Thread.Asyn_Thread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BoxofficeActivity extends AppCompatActivity {
    private ImageView back,image;
    private TextView rank,piaofang,title,type,rating,director,cast;
    private SearchActivity searchActivity;
    private ListView boxoffice;
    private ArrayList arr;
    private ProgressBar loading;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boxoffice);

        image = findViewById(R.id.movie_image);
        rank  = findViewById(R.id.rank);
        piaofang = findViewById(R.id.piaofang);
        title = findViewById(R.id.movie_title);
        type = findViewById(R.id.movie_type);
        rating = findViewById(R.id.movie_rating);
        director = findViewById(R.id.movie_director);
        cast = findViewById(R.id.movie_cast);
        boxoffice = findViewById(R.id.boxoffice);
        loading = findViewById(R.id.ab_loading);

        back = findViewById(R.id.ab_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String url = "http://api.douban.com/v2/movie/us_box";
        @SuppressLint("HandlerLeak") Asyn_Thread asyn_thread = new Asyn_Thread(url,new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 4){
                    Bundle b = msg.getData();
                    String str = b.getString("text");
                    arr = piaofang_information(str);
                    boxoffice.setAdapter(new Adapter_boxoffice(BoxofficeActivity.this,arr));
                    loading.setVisibility(View.GONE);

                }
            }
        },"boxoffice");
        new Thread(asyn_thread).start();

        boxoffice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent  = new Intent(BoxofficeActivity.this,MovieDetail.class);
                Map<String,Object> m = (Map<String, Object>) arr.get(i);
                String id = (String) m.get("id");
                intent.putExtra("clickid",id);
                startActivity(intent);
            }
        });
    }

    public ArrayList piaofang_information(String str){
        searchActivity = new SearchActivity();

        ArrayList<Map<String,Object>> arrayList = new ArrayList<Map<String, Object>>();
        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = jsonObject.getJSONArray("subjects");

            for(int i=0;i<jsonArray.length();i++){
                JSONObject temp = (JSONObject) jsonArray.get(i);
                JSONObject temp2 = temp.getJSONObject("subject");
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("rank","__________   "+temp.getString("rank")+"   __________");
                map.put("box","票房："+temp.getString("box")+" 美元");


                map.put("images",temp2.getJSONObject("images").getString("small"));
                map.put("title",temp2.getString("title"));

                JSONArray arr_genres = temp2.getJSONArray("genres");
                map.put("genres",searchActivity.inf2(arr_genres));
                map.put("rating",temp2.getJSONObject("rating").getString("average"));

                JSONArray arr_director  = temp2.getJSONArray("directors");
                map.put("directors","导演："+searchActivity.inf(arr_director,"name"));

                JSONArray arr_cast = temp2.getJSONArray("casts");
                map.put("casts","演员："+searchActivity.inf(arr_cast,"name"));

                map.put("id",temp2.getString("id"));
                arrayList.add(map);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
