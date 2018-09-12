package com.example.douban.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.douban.Activity.MainActivity;
import com.example.douban.Activity.MovieDetail;
import com.example.douban.Adapter.Adapter_hot;
import com.example.douban.R;
import com.example.douban.Activity.SearchActivity;
import com.example.douban.Thread.Asyn_Thread;

import java.util.ArrayList;
import java.util.Map;


public class Fragment_item extends Fragment{
    private EditText editText;
    private ListView lv_hot;
    SearchActivity searchActivity;
    Adapter_hot adapter_hot;
    ArrayList arrayList;
    private View view;
    private ProgressBar loading;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.activity_item, container, false);
        }
        editText = view.findViewById(R.id._search);
        lv_hot = view.findViewById(R.id.lv_hot);

        loading = view.findViewById(R.id.ai_loading);
        //设置文本框不可编辑但可以点击
        editText.setCursorVisible(false);
        editText.setFocusable(false);//失去焦点
        editText.setFocusableInTouchMode(false);//失去焦点

        //缓存这个view,点击另外的fragment后，再点回来则不再重新加载数据
        ViewGroup group = (ViewGroup) view.getParent();
        if (group != null){
            group.removeView(view);
        }


        //点击搜索框跳到搜索页面
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SearchActivity.class);
                startActivity(intent);
            }
        });

        String url = "https://api.douban.com/v2/movie/in_theaters";//热映电影api
        @SuppressLint("HandlerLeak")
        Asyn_Thread at = new Asyn_Thread(url,new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == 2){
                    searchActivity = new SearchActivity();
                    Bundle b =msg.getData();//获取子线程的信息
                    String str = b.getString("text");//把获取到的信息转化为字符串
                    arrayList = searchActivity.information(str);
                    adapter_hot = new Adapter_hot(getActivity(),arrayList);
                    lv_hot.setAdapter(adapter_hot);
                    loading.setVisibility(View.GONE);
                }
            }
        },"hot");//实例化异步线程
        new Thread(at).start();//开启线程

        lv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,int i,long l) {
                Intent intent = new Intent(getActivity(), MovieDetail.class);
                Map<String,Object> map = (Map<String, Object>) arrayList.get(i);
                String id = (String) map.get("id");
                intent.putExtra("clickid",id);
                startActivity(intent);
            }
        });
        Log.i("Fragment---2","onCreateView");

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Fragment---2","onDestroy");

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("Fragment---2","onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Fragment---2","onCreat");

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("Fragment---2","onActivityCreated");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("Fragment---2","onStart");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Fragment---2","onResume");

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Fragment---2","onPause");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("Fragment---2","onStop");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("Fragment---2","onDestroyView");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("Fragment---2","onDetach");

    }

}
