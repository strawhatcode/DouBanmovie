package com.example.douban.Thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.douban.HTTP.HttpUrlconnection;


public class Asyn_Thread extends Thread {
    private String url;
    private Handler handler;
    private String sort;
    public Asyn_Thread(String url, Handler handler, String sort){
        this.url = url;
        this.handler = handler;
        this.sort = sort;
    }

    @Override
    public void run() {
        super.run();

        HttpUrlconnection connection = new HttpUrlconnection();//实例化自建的GET请求类的对象
        String str_url = connection.GETstring(this.url);//调用对象的GETstring方法得到url赋给str_url

        Message message = new Message();//实例化Message对象来传输信息
        if(sort.equals("search"))//如果传过来的参数sort = search，则把message.what设置为1
            message.what = 1;
        else if(sort.equals("hot"))
            message.what = 2;
        else if(sort.equals("coming"))
            message.what = 3;
        else if(sort.equals("boxoffice"))
            message.what = 4;
        else if(sort.equals("top250"))
            message.what = 5;
        else if(sort.equals("moviedetail"))
            message.what = 6;
        else if(sort.equals("castdetail"))
            message.what = 7;
        Bundle b = new Bundle();//声明Bundle对象存放数据
        b.putString("text",str_url);
        message.setData(b);

        this.handler.sendMessage(message);
    }
}
