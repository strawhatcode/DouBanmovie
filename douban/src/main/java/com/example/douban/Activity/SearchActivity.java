package com.example.douban.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.douban.Adapter.Adapter_hot;
import com.example.douban.DATABASE.History_database;
import com.example.douban.R;
import com.example.douban.Thread.Asyn_Thread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends Activity {
    private TextView cancel,clear;
    private ImageButton delete;
    private EditText editText;
    private History_database database;
    private ListView lv_history,searchresult;
    private RelativeLayout searchview;
    String str;
    private ProgressBar loading;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        cancel = findViewById(R.id.cancel);
        clear = findViewById(R.id.clear);
        delete = findViewById(R.id.delete);
        editText = findViewById(R.id.search);
        searchresult = findViewById(R.id.searchresult);
        searchview = findViewById(R.id.searchview);
        loading = findViewById(R.id.as_loading);

        //创建一个mingwei名为history_database的数据库
        database = new History_database(this,"history_database",null,1);

        //点击取消按钮退出搜索页面
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //点击搜索框右边的清空按钮则清空文本框的内容
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });
        //点击搜索记录右边的清除按钮则清空搜索历史
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.getReadableDatabase().execSQL("DELETE FROM history");
                setonview();//调用这个方法来把历史纪录显示在历史纪录中
            }
        });
        setonview();//调用这个方法来把历史纪录显示在历史纪录中

        //监听软件盘的搜索按钮
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction()==KeyEvent.ACTION_DOWN){

                    //判断在数据库中是否存在要搜索的内容，如果有返回true
                    boolean exits = querydatabase(editText.getText().toString().trim());
                    //如果在数据库中没有，如果没有的话执行if里的内容
                    if(!exits){
                        //实例化ContentValues对象用来存储输入的内容
                        ContentValues values = new ContentValues();
                        values.put("value",editText.getText().toString());
                        //把文本框里的内容添加到数据库中
                        database.getReadableDatabase().insert("history",null,values);
                    }
                    searchresult.setVisibility(View.VISIBLE);//搜索到的结果布局可见
                    searchview.setVisibility(View.GONE);//搜索记录布局隐藏
                    //豆瓣电影搜索api
                    String text = editText.getText().toString();
                    Log.i("texttexttext",""+text);
                    String url = "http://api.douban.com/v2/movie/search?q={"+text+"}";
                    Asyn_Thread at = new Asyn_Thread(url, handler,"search");//实例化异步线程
                    new Thread(at).start();//开启线程
                }
                return false;
            }
        });

        searchresult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchActivity.this,MovieDetail.class);
                ArrayList arr;
                arr = information(str);
                Map<String,Object> m = (Map<String, Object>) arr.get(i);
                String id = (String) m.get("id");
                intent.putExtra("clickid",id);
                startActivity(intent);
            }
        });
    }

    //创建handler接受子线程发来的信息进行处理
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what ==1){
                Bundle b =msg.getData();//获取子线程的信息
                str = b.getString("text");//把获取到的信息转化为字符串
                searchresult.setAdapter(new Adapter_hot(SearchActivity.this,information(str)));
                loading.setVisibility(View.GONE);
            }
        }
    };

    //这个方法用来获取API里的数据，以字符串的形式用适配器显示在UI界面中
    public ArrayList information(String str){
        JSONObject jsonObject;
        JSONArray jsonArray;
        //创建Map类型的泛型数组
        ArrayList<Map<String,Object>> arrayList = new ArrayList<Map<String,Object>>();
        try {
            jsonObject = new JSONObject(str);//实例化JSONObject对象，把字符串转化为JSON对象类型
            jsonArray = jsonObject.getJSONArray("subjects");//获取JSONObject对象中键值为“subjects”的信息，并且转化为JSONArray数组
            //遍历JSONArray
            for(int i=0;i<jsonArray.length();i++){
                JSONObject temp = (JSONObject) jsonArray.get(i);//获取“subjects”JSON数组中的第i个对象
                Map<String,Object> map = new HashMap<String, Object>();

                map.put("images", temp.getJSONObject("images").getString("small"));

                map.put("title",temp.getString("title"));

                JSONArray ja_genres = temp.getJSONArray("genres");//获取到temp对象中为“genres”的键值【key】，并转化为JSON数组
                map.put("genres",inf2(ja_genres));

                map.put("rating",temp.getJSONObject("rating").getString("average")+"分");

                JSONArray ja_directory = temp.getJSONArray("directors");//创建JSON列表读取JSON对象中的key【directors】
                map.put("directors","导演："+inf(ja_directory,"name"));

                JSONArray ja_casts = temp.getJSONArray("casts");//创建JSON列表读取JSON对象中的key【cast】
                map.put("casts","演员："+inf(ja_casts,"name"));

                map.put("id",temp.getString("id"));//电影的ID
                arrayList.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }




    //查询数据库，是否已存在搜索记录
    public boolean querydatabase(String value){
        Cursor cursor = database.getReadableDatabase().query("history",new String[]{"value"},
                "value=?",new String[]{value},null,null,null);
        return cursor.moveToNext();//返回游标是否存在下一个数据
    }

    //把数据库中的记录显示在历史记录框中
    public void setonview(){
        Cursor cursor = database.getReadableDatabase().query("history",new String[]{"value"},
                null,null,null,null,null);
        ArrayList<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        while(cursor.moveToNext()){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("history_value",cursor.getString(0));
            list.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this,list,R.layout.search_textview,new String[]{"history_value"},new int[]{R.id.tv_history});
        lv_history = findViewById(R.id.lv_history);
        lv_history.setAdapter(adapter);
    }

    //这个是解析有【嵌套】的JSON数组
    //将JSON格式的数据转换成字符串，且两个数据之间用“/”号隔开
    //第一个参数为JSON数组 第二个参数为【嵌套JSON数组】里的键值的名称
    public String inf(JSONArray jsonArray,String name){
        String str = "";
        for(int j=0;j<jsonArray.length();j++){
            if(j>0)
                str += " / ";
            try {
                str += ((JSONObject)jsonArray.get(j)).optString(name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return str;
    }

    //这个是解析普通的JSON数组（即【没有嵌套】）
    //将JSON格式的数据转换成字符串，且两个数据之间用“/”号隔开
    //参数为JSON数组
    public String inf2(JSONArray jsonArray){
        String string = "";//声明电影类型字符串
        for(int k=0;k<jsonArray.length();k++){ //for循环遍历“genres”数组中的值
            if(k>0)
                string += " / ";//如果值个数超过两个，则在每一个值后面添加“/”符号分割开
            try {
                string += jsonArray.get(k);//将genres里的数组每个值合成一个新的字符串
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return string;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //如果数据库不为空则关闭数据库
        if(database !=null)
            database.close();
    }
}
