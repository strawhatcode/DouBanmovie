package com.example.douban.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.douban.R;


import com.example.douban.Fragment.Fragment_item;
import com.example.douban.Fragment.Fragment_list;
import com.example.douban.Fragment.Fragment_setting;

public class MainActivity extends AppCompatActivity {

    private FragmentTabHost fragmentTabHost;
    private LayoutInflater layoutInflater;
    private long firsttime = 0;


    private String[] item = new String[]{"热映","榜单","设置"};//声明且初始化item数组
    private Class[] fragment = new Class[]{Fragment_item.class, Fragment_list.class, Fragment_setting.class};//声明且初始化fragment数组
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inittabview();

    }

    public boolean onKeyDown(int keycode, KeyEvent keyEvent){
        if (keycode == keyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
            //判断两次点击的时间差，如果两次点击的时间差大于2秒，则不退出，否则退出程序
            if (System.currentTimeMillis() - firsttime >2000) {
                Toast.makeText(MainActivity.this, "再次点击返回键退出程序", Toast.LENGTH_SHORT).show();
                firsttime = System.currentTimeMillis(); //把第二点击的时间转化为毫秒后赋给firsttime，然后再点击时与上一次的时间进行计算再判断
                return true;
            }else
                finish();
        }
        return super.onKeyDown(keycode,keyEvent);
    }



    //初始化组件
    public void inittabview(){
        layoutInflater = layoutInflater.from(this);//加载当前布局管理器

        //实例化fragmenttabhost对象，得到fragmenttabhost
        fragmentTabHost = findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this,getSupportFragmentManager(),R.id.fragmenttabcontent);

        for(int i=0;i<item.length;i++){
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(item[i]).setIndicator(seticonandtext(i));
            fragmentTabHost.addTab(tabSpec,fragment[i],null);//把图标和文字和Fragment资源添加到tab选项卡中
           // fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.background);//给tab底栏设置背景
        }
        fragmentTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);//取消tab选项之间的分割线
    }

    //把图标的文字添加到tab按钮
    public View seticonandtext(int i){
        //创建view 且得到tabcontent_view(底栏图标和文字)布局文件
        View view = layoutInflater.inflate(R.layout.tabcontent_view,null);
        ImageView imageView = view.findViewById(R.id.icon);
        //把图标资源赋给imageView
        if(i == 0)
            imageView.setImageResource(R.drawable.selector_item);
        else if(i == 1)
            imageView.setImageResource(R.drawable.selector_list);
        else if(i == 2)
            imageView.setImageResource(R.drawable.selector_setting);
        TextView textView = view.findViewById(R.id.tagname);
        textView.setText(item[i]);//把tab名字资源赋给textView

        return view;//返回这个VIEW
    }
}
