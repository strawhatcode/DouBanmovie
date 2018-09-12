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

import com.example.douban.Adapter.Adapter_top250;
import com.example.douban.R;
import com.example.douban.Thread.Asyn_Thread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Top250Activity extends AppCompatActivity implements View.OnClickListener{
    private ImageView back;
    private TextView tv1,tv2,tv3,tv4,tv5;
    private ListView t1,t2,t3,t4,t5;
    private SearchActivity searchActivity;
    private ArrayList arr1,arr2,arr3,arr4,arr5;
    private ProgressBar loading;
    String str;
    boolean isloading = false;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top250);
        back = findViewById(R.id.at_back);
        loading = findViewById(R.id.at_loading);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        t1 = findViewById(R.id.top1_50);
        t2 = findViewById(R.id.top50_100);
        t3 = findViewById(R.id.top101_150);
        t4 = findViewById(R.id.top151_200);
        t5 = findViewById(R.id.top201_250);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        final Intent intent = new Intent(Top250Activity.this,MovieDetail.class);

        t1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                arr1 = top250_information(str,0);
                Map<String,Object> m = (Map<String, Object>) arr1.get(i);
                String id1 = (String) m.get("id");
                intent.putExtra("clickid",id1);
                startActivity(intent);
            }
        });
        t2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                arr2 = top250_information(str,50);
                Map<String,Object> m = (Map<String, Object>) arr2.get(i);
                String id2 = (String) m.get("id");
                intent.putExtra("clickid",id2);
                startActivity(intent);
                Log.i("lllllllllllllllllllll",""+id2);

            }
        });
        t3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                arr3 = top250_information(str,100);
                Map<String,Object> m = (Map<String, Object>) arr3.get(i);
                String id3 = (String) m.get("id");
                intent.putExtra("clickid",id3);
                startActivity(intent);
            }
        });
        t4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                arr4 = top250_information(str,150);
                Map<String,Object> m = (Map<String, Object>) arr4.get(i);
                String id4 = (String) m.get("id");
                intent.putExtra("clickid",id4);
                startActivity(intent);
            }
        });
        t5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                arr5 = top250_information(str,200);
                Map<String,Object> m = (Map<String, Object>) arr5.get(i);
                String id5 = (String) m.get("id");
                intent.putExtra("clickid",id5);
                startActivity(intent);
            }
        });
        final String url = "http://api.douban.com/v2/movie/top250?count=50";

        @SuppressLint("HandlerLeak")
        Asyn_Thread asyn_thread = new Asyn_Thread(url,new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == 5){
                    Bundle b = msg.getData();
                    str = b.getString("text");
                    t1.setAdapter(new Adapter_top250(Top250Activity.this,top250_information(str,0)));
                    if (isloading ==false)
                        loading.setVisibility(View.GONE);
                    else
                        loading.setVisibility(View.VISIBLE);

                }
            }
        },"top250");
        new Thread(asyn_thread).start();
        t1.setVisibility(View.VISIBLE);
        t2.setVisibility(View.GONE);
        t3.setVisibility(View.GONE);
        t4.setVisibility(View.GONE);
        t5.setVisibility(View.GONE);
        tv1.setSelected(true);
        tv2.setSelected(false);
        tv3.setSelected(false);
        tv4.setSelected(false);
        tv5.setSelected(false);
        isloading = true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                t1.setVisibility(View.VISIBLE);
                t2.setVisibility(View.GONE);
                t3.setVisibility(View.GONE);
                t4.setVisibility(View.GONE);
                t5.setVisibility(View.GONE);
                tv1.setSelected(true);
                tv2.setSelected(false);
                tv3.setSelected(false);
                tv4.setSelected(false);
                tv5.setSelected(false);
                break;
            case R.id.tv2:
                String url2 = "http://api.douban.com/v2/movie/top250?start=51&count=100";
                @SuppressLint("HandlerLeak")
                Asyn_Thread asyn_thread2 = new Asyn_Thread(url2,new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        if(msg.what == 5){
                            Bundle b = msg.getData();
                            str = b.getString("text");
                            t2.setAdapter(new Adapter_top250(Top250Activity.this,top250_information(str,50)));
                            if (isloading ==false)
                                loading.setVisibility(View.GONE);
                            else
                                loading.setVisibility(View.VISIBLE);
                        }
                    }
                },"top250");
                new Thread(asyn_thread2).start();
                t1.setVisibility(View.GONE);
                t2.setVisibility(View.VISIBLE);
                t3.setVisibility(View.GONE);
                t4.setVisibility(View.GONE);
                t5.setVisibility(View.GONE);
                tv1.setSelected(false);
                tv2.setSelected(true);
                tv3.setSelected(false);
                tv4.setSelected(false);
                tv5.setSelected(false);
                isloading = true;
                break;
            case R.id.tv3:
                String url3 = "http://api.douban.com/v2/movie/top250?start=101&count=150";
                @SuppressLint("HandlerLeak")
                Asyn_Thread asyn_thread3 = new Asyn_Thread(url3,new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        if(msg.what == 5){
                            Bundle b = msg.getData();
                            str = b.getString("text");
                            t3.setAdapter(new Adapter_top250(Top250Activity.this,top250_information(str,100)));
                            if (isloading ==false)
                                loading.setVisibility(View.GONE);
                            else
                                loading.setVisibility(View.VISIBLE);
                        }
                    }
                },"top250");
                new Thread(asyn_thread3).start();
                t1.setVisibility(View.GONE);
                t2.setVisibility(View.GONE);
                t3.setVisibility(View.VISIBLE);
                t4.setVisibility(View.GONE);
                t5.setVisibility(View.GONE);
                tv1.setSelected(false);
                tv2.setSelected(false);
                tv3.setSelected(true);
                tv4.setSelected(false);
                tv5.setSelected(false);
                isloading = true;
                break;
            case R.id.tv4:
                String url4 = "http://api.douban.com/v2/movie/top250?start=151&count=200";
                @SuppressLint("HandlerLeak")
                Asyn_Thread asyn_thread4 = new Asyn_Thread(url4,new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        if(msg.what == 5){
                            Bundle b = msg.getData();
                            str = b.getString("text");
                            t4.setAdapter(new Adapter_top250(Top250Activity.this,top250_information(str,150)));
                            if (isloading ==false)
                                loading.setVisibility(View.GONE);
                            else
                                loading.setVisibility(View.VISIBLE);
                        }
                    }
                },"top250");
                new Thread(asyn_thread4).start();
                t1.setVisibility(View.GONE);
                t2.setVisibility(View.GONE);
                t3.setVisibility(View.GONE);
                t4.setVisibility(View.VISIBLE);
                t5.setVisibility(View.GONE);
                tv1.setSelected(false);
                tv2.setSelected(false);
                tv3.setSelected(false);
                tv4.setSelected(true);
                tv5.setSelected(false);
                isloading = true;
                break;
            case R.id.tv5:
                String url5 = "http://api.douban.com/v2/movie/top250?start=201&count=250";
                @SuppressLint("HandlerLeak")
                Asyn_Thread asyn_thread5 = new Asyn_Thread(url5,new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        if(msg.what == 5){
                            Bundle b = msg.getData();
                            str = b.getString("text");
                            t5.setAdapter(new Adapter_top250(Top250Activity.this,top250_information(str,200)));
                            if (isloading ==false)
                                loading.setVisibility(View.GONE);
                            else
                                loading.setVisibility(View.VISIBLE);
                        }
                    }
                },"top250");
                new Thread(asyn_thread5).start();
                t1.setVisibility(View.GONE);
                t2.setVisibility(View.GONE);
                t3.setVisibility(View.GONE);
                t4.setVisibility(View.GONE);
                t5.setVisibility(View.VISIBLE);
                tv1.setSelected(false);
                tv2.setSelected(false);
                tv3.setSelected(false);
                tv4.setSelected(false);
                tv5.setSelected(true);
                isloading = true;
                break;
        }
    }

    public ArrayList top250_information(String str,int index){
        searchActivity = new SearchActivity();

        ArrayList<Map<String,Object>> arrayList = new ArrayList<Map<String, Object>>();
        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = jsonObject.getJSONArray("subjects");

            for(int i=0;i<50;i++){
                JSONObject temp = (JSONObject) jsonArray.get(i);
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("rank","__________   "+(i+1+index)+"   __________");
                map.put("images",temp.getJSONObject("images").getString("small"));
                map.put("title",temp.getString("title"));

                JSONArray arr_genres = temp.getJSONArray("genres");
                map.put("genres",searchActivity.inf2(arr_genres));
                map.put("rating",temp.getJSONObject("rating").getString("average"));

                JSONArray arr_director  = temp.getJSONArray("directors");
                map.put("directors","导演："+searchActivity.inf(arr_director,"name"));

                JSONArray arr_cast = temp.getJSONArray("casts");
                map.put("casts","演员："+searchActivity.inf(arr_cast,"name"));

                map.put("id",temp.getString("id"));
                arrayList.add(map);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
