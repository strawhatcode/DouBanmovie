package com.example.douban.HTTP;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUrlconnection {

    private static final int CONNECT_TIME_OUT = 1000*5;//声明连接超时时间为5秒
    String str ="";//声明一个接收得到的信息的字符串

    public String GETstring(String url){
        try {
            URL url1 = new URL(url); //获得被请求地址的URL
            HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();//得到网络访问对象
            httpURLConnection.setDoInput(true);//设置允许请求读入
            httpURLConnection.setDoOutput(false);//设置不允许请求输出
            httpURLConnection.setRequestMethod("GET");//设置请求的方法为GET请求
            httpURLConnection.setUseCaches(true);//设置允许使用缓存
            httpURLConnection.setConnectTimeout(CONNECT_TIME_OUT);//s设置连接时间最多5秒
            httpURLConnection.connect();//连接

            int REQUESTCODE = httpURLConnection.getResponseCode();//用来接收状态返回码
            //如果状态返回码为200则正常响应
            if(REQUESTCODE == 200){
                //从输入流中读取信息
                BufferedReader buffer = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line="" ;
                //循环读取流中的信息
                while((line = buffer.readLine()) != null){
                    str += line+"\n";
                }
                buffer.close();//关闭流
            }
            httpURLConnection.disconnect();//断开连接，释放资源

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}
